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

    private final CoffeeService coffeeService;

    @Autowired
    public CoffeeController(CoffeeService coffeeService) {
        this.coffeeService = coffeeService;
    }

    @GetMapping("/catalog")
    public String catalog(Model model, HttpSession session){
        UserID currentUser = (UserID) session.getAttribute("user");
        if (currentUser == null) {
            return "redirect:/login";
        }
        model.addAttribute("coffees", coffeeService.getCoffee());
        model.addAttribute("activeMenu", "catalog");
        return "coffeeCatalog";
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
        model.addAttribute("activeMenu", "home");
        return "index";
    }

    @GetMapping("/delete")
    public String deleteCoffee(@RequestParam int id) {
        coffeeService.deleteCoffee(id);
        return "redirect:/";
    }

    @GetMapping("/new") // or /add
    public String createCoffee(Model model, HttpSession session) {
        UserID currentUser = (UserID) session.getAttribute("user");
        if (currentUser == null) {
            return "redirect:/login";
        }

        model.addAttribute("coffee", new Coffee());
        model.addAttribute("activeMenu", "add");
        return "addCoffee";
    }

    @PostMapping("/save")
    public String saveCoffee(@ModelAttribute("coffee") @Valid Coffee coffee,
                             BindingResult bindingResult,
                             @RequestParam("imageFile") MultipartFile picture,
                             HttpSession session) {

        UserID currentUser = (UserID) session.getAttribute("user");
        if (currentUser == null) {
            return "redirect:/login";
        }

        if (bindingResult.hasErrors()) {
            return "addCoffee";
        }

        if (!picture.isEmpty()) {
            String path = "data/coffee_pics/";
            File uploadFolder = new File(path);
            if (!uploadFolder.exists()) {
                uploadFolder.mkdirs();
            }

            String originalFileName = picture.getOriginalFilename();
            String extension = originalFileName.substring(originalFileName.lastIndexOf('.'));
            String fileName = UUID.randomUUID() + extension;

            try {
                File destinationFile = new File(uploadFolder.getAbsolutePath() + File.separator + fileName);
                picture.transferTo(destinationFile);
                coffee.setPicture(fileName);
            } catch (IOException e) {
                System.out.println("File upload error: " + e.getMessage());
            }
        }

        coffeeService.addCoffee(coffee);
        return "redirect:/";
    }

    @GetMapping("/edit")
    public String editCoffee(@RequestParam int id, Model model, HttpSession session) {
        UserID currentUser = (UserID) session.getAttribute("user");
        if (currentUser == null) {
            return "redirect:/login";
        }

        Coffee c = coffeeService.getCoffee(id);
        if (c != null) {
            model.addAttribute("coffee", c);
            model.addAttribute("activeMenu", "edit");
            return "editCoffee";
        }
        return "redirect:/";
    }

    @PostMapping("/update")
    public String updateCoffee(@ModelAttribute("coffee") @Valid Coffee coffee,
                               BindingResult bindingResult,
                               @RequestParam("imageFile") MultipartFile picture,
                               Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("coffee", coffee);
            return "editCoffee";
        }

        Coffee existingCoffee = coffeeService.getCoffee(coffee.getId());
        if (existingCoffee != null) {

            if (!picture.isEmpty()) {
                String path = "data/coffee_pics/";
                File uploadFolder = new File(path);
                if (!uploadFolder.exists()) {
                    uploadFolder.mkdirs();
                }

                String originalFileName = picture.getOriginalFilename();
                String extension = originalFileName.substring(originalFileName.lastIndexOf('.'));
                String fileName = UUID.randomUUID() + extension;

                try {
                    File destinationFile = new File(uploadFolder.getAbsolutePath() + File.separator + fileName);
                    picture.transferTo(destinationFile);
                    coffee.setPicture(fileName);
                } catch (IOException e) {
                    System.out.println("File upload error: " + e.getMessage());
                }
            } else {
                coffee.setPicture(existingCoffee.getPicture());
            }

            coffeeService.updateCoffee(coffee.getId(), coffee);
        }

        return "redirect:/";
    }

    @GetMapping("/coffee/view")
    public String viewCoffee(@RequestParam int id, Model model, HttpSession session) {
        UserID currentUser = (UserID) session.getAttribute("user");
        if (currentUser == null) {
            return "redirect:/login";
        }

        Coffee coffee = coffeeService.getCoffee(id);
        if (coffee == null) {
            return "redirect:/";
        }

        model.addAttribute("coffee", coffee);
        return "coffee";
    }
}
