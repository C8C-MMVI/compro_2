package com.coffeemenu.coffeeSpring.controllerCodes;

import com.coffeemenu.coffeeSpring.modelCodes.Coffee;
import com.coffeemenu.coffeeSpring.serviceCodes.CoffeeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class CoffeeController {

    @Autowired
    CoffeeService coffeeService;

    @GetMapping("/")
    public String index(@RequestParam(defaultValue = "") String search, Model model) {
        model.addAttribute("coffees", coffeeService.searchCoffee(search));
        return "index";
    }

    @GetMapping("/delete")
    public String deleteCoffee(@RequestParam int id) {
        coffeeService.deleteCoffee(id);
        return "redirect:/";
    }

    @GetMapping("/new")
    public String createCoffee(Model model) {
        model.addAttribute("coffee", new Coffee()); // Corrected: add the model attribute
        return "addCoffee";
    }

    @PostMapping("/save")
    public String saveCoffee(@ModelAttribute("coffee") @Valid Coffee coffee,
                             BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "addCoffee"; // Return to form if validation fails
        }
        coffeeService.addCoffee(coffee);
        return "redirect:/";
    }

    @GetMapping("/edit")
    public String editCoffee(@RequestParam int id, Model model) {
        Coffee c = coffeeService.getCoffee(id);
        if (c != null) {
            model.addAttribute("coffee", c); // Corrected: provide coffee for editing
            return "editCoffee";
        }
        return "redirect:/"; // fallback
    }

    @PostMapping("/update")
    public String store(@ModelAttribute("coffee") @Valid Coffee coffee,
                        BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "editCoffee";
        }
        coffeeService.updateCoffee(coffee.getId(), coffee);
        return "redirect:/";
    }
}
