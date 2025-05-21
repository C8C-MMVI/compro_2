package com.coffeemenu.coffeeSpring.controllerCodes;

import com.coffeemenu.coffeeSpring.modelCodes.Coffee;
import com.coffeemenu.coffeeSpring.modelCodes.UserID;
import com.coffeemenu.coffeeSpring.serviceCodes.CoffeeService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Controller
public class CoffeeController {

    @Autowired
    CoffeeService coffeeService;

    @GetMapping("/catalog")
    public String catalog(Model model){
        model.addAttribute("coffeeLists", coffeeService.getCoffee());
        model.addAttribute("liveMenu", "catalog");
        return "catalog";
    }

    @GetMapping("/home")
    public String home(Model model) {
        return "layouts/master";
    }

    @GetMapping("/")
    public String index(@RequestParam(defaultValue = "") String search, Model model, HttpSession session) {
        UserID currentUser = (UserID) session.getAttribute("user");
        if (currentUser == null) {
            return "redirect:/login";
        }
        model.addAttribute("coffees", coffeeService.searchCoffee(search));
        return "index";
    }


    @GetMapping("/delete")
    public String deleteCoffee(@RequestParam int id) {
        coffeeService.deleteCoffee(id); // Lets the user delete any of the coffee options
        return "redirect:/";
    }

    @GetMapping("/add")
    public String addCoffee(Model model, HttpSession session){
        UserID currentUser = (UserID) session.getAttribute("user");
        if (currentUser == null) {
            return "redirect:/login";
        }

        model.addAttribute("coffee", new Coffee());
        model.addAttribute("liveMenu", "add");
        return "add";
    }


    @PostMapping("/save")
    public String saveCoffee(@ModelAttribute("coffee") @Valid Coffee coffee,
                                BindingResult bindingResult,
                                @RequestParam("imageFile") MultipartFile coffeeImage,
                                HttpSession session,
                                Model model) {

        UserID currentUser = (UserID) session.getAttribute("user");
        if (currentUser == null) {
            return "redirect:/login";
        }

        if (bindingResult.hasErrors()) {
            return "add";
        }

        if (!coffeeImage.isEmpty()) {
            String path = "data/coffeeImages/";
            File uploadFolder = new File(path);
            if (!uploadFolder.exists()) {
                uploadFolder.mkdirs();
            }

            String originalFileName = coffeeImage.getOriginalFilename();
            String extension = originalFileName.substring(originalFileName.lastIndexOf('.'));
            String fileName = UUID.randomUUID() + extension;

            try {
                File destinationFile = new File(uploadFolder.getAbsolutePath() + File.separator + fileName);
                coffeeImage.transferTo(destinationFile);
                coffee.setCoffeeImage(fileName);
            } catch (IOException e) {
                System.out.println("File upload error: " + e.getMessage());
            }
        }

        coffee.setId(coffeeService.getLastId() + 1);
        coffeeService.addCoffee(coffee);
        return "redirect:/";
    }

    @GetMapping("/edit")
    public String editCoffee(@RequestParam int id, Model model) {
        Coffee c = coffeeService.getCoffee(id);
        if(c != null){
            model.addAttribute("coffee", c);
            model.addAttribute("liveMenu", "edit");
            return "edit";
        }
        return "redirect:/";
    }

    @PostMapping("/update")
    public String store(@ModelAttribute("coffee") @Valid Coffee coffee,
                        BindingResult bindingResult,
                        @RequestParam("imageFile") MultipartFile coffeeImage,
                        HttpSession session,
                        Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("coffee", coffee);
            return "edit";
        }

        Coffee availableCoffee = coffeeService.getCoffee(coffee.getId());
        if (availableCoffee != null) {

            if (!coffeeImage.isEmpty()) {
                String path = "data/coffeeImages/";
                File uploadFolder = new File(path);
                if (!uploadFolder.exists()) {
                    uploadFolder.mkdirs();
                }

                String originalFileName = coffeeImage.getOriginalFilename();
                String extension = originalFileName.substring(originalFileName.lastIndexOf('.'));
                String fileName = UUID.randomUUID() + extension;

                try {
                    File destinationFile = new File(uploadFolder.getAbsolutePath() + File.separator + fileName);
                    coffeeImage.transferTo(destinationFile);
                    coffee.setCoffeeImage(fileName);
                } catch (IOException e) {
                    System.out.println("File upload error: " + e.getMessage());
                }
            } else {
                coffee.setCoffeeImage(availableCoffee.getCoffeeImage());
            }

            coffeeService.updateCoffee(coffee.getId(), coffee);
        }

        return "redirect:/";
    }

    @GetMapping("/coffee/{id}")
    public String viewCoffee(@PathVariable int id, Model model, HttpSession session) {
        //check if user is logged in
        UserID currentUser = (UserID) session.getAttribute("user");
        if(currentUser == null){
            return "redirect:/login";
        }

        Coffee c = coffeeService.getCoffee(id);
        if (c==null){
            return "redirect:/";
        }

        model.addAttribute("coffee", c);
        return "coffee";
    }
}