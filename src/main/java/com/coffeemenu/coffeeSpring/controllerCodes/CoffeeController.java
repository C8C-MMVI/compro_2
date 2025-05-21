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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
//import jakarta.servlet.http.HttpSession;
//import jakarta.validation.Valid;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;


import java.io.File;
import java.io.IOException;
import java.util.List;
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
    public String index(@RequestParam(defaultValue = "") String search, Model model) {
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
    public String addCoffee(Model model){
        UserID currentUser = (UserID) session.getAttribute("user");
        if (currentUser == null) {
            return "redirect:/login";
        }

        model.addAttribute("coffee", new Coffee());
        model.addAttribute("liveMenu", "add");
        return "add";
    }

    @PostMapping("/save")
    public String saveCoffee(ModelAttribute("coffee") @Valid Coffee coffee,
    BindingResult bindingResult, @RequestParam("imageFile")
    MultipartFile coffeePicture,
    HttpSession session,
    Model model) {

        AppUser currentUser = (AppUser) session.getAttribute("user");
        if (currentUser == null) {
            return "redirect:/login";
        }

        if (bindingResult.hasErrors()) {
            return "add";
        }

        if (!coffeePicture.isEmpty()) {
            String path = "data/coffee_pictures/";
            File uploadFolder = new File(path);
            if (!uploadFolder.exists()) {
                uploadFolder.mkdirs();
            }

            String originalFileName = coffeePicture.getOriginalFilename();
            String extension = originalFileName.substring(originalFileName.lastIndexOf('.'));
            String fileName = UUID.randomUUID() + extension;

            try {
                File destinationFile = new File(uploadFolder.getAbsolutePath() + File.separator + fileName);
                coffeePicture.transferTo(destinationFile);
                coffee.setCoffeePicture(fileName);
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
            int[] levels = {1,2,3,4};
            model.addAttribute("levels", levels);
            model.addAttribute("coffee", c);
            return "edit";
        }
        return "redirect:/";
    }

    @PostMapping("/update")
    public String store(@RequestParam int id,
                        @RequestParam (required = true) String name,
                        @RequestParam (required = false) String type,
                        @RequestParam (required = true) String size,
                        @RequestParam (required = true) double price,
                        @RequestParam String roastLevel,
                        @RequestParam String origin,
                        @RequestParam (required = true) int stock,
                        @RequestParam List<String> flavorNotes,
                        @RequestParam String brewMethod) {
        Coffee c = coffeeService.getCoffee(id);
        if (c != null){
            c.setName(name);
            c.setType(type);
            c.setSize(size);
            c.setPrice(price);
            c.setRoastLevel(roastLevel);
            c.setOrigin(origin);
            c.setStock(stock);
            c.setFlavorNotes(flavorNotes);
            c.setBrewMethod(brewMethod);

            coffeeService.updateCoffee(id, c);
        }
        return "redirect:/";
    }
}