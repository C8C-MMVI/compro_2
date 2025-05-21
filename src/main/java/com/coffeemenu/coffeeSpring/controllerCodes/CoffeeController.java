package com.coffeemenu.coffeeSpring.controllerCodes;

import com.coffeemenu.coffeeSpring.serviceCodes.CoffeeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
        coffeeService.deleteCoffee(id); // Lets the user delete any of the coffee options
        return "redirect:/";
    }

    @GetMapping("/new")
    public String createCoffee(Model model){
        String [] beanType = {"Arabica", "Robusta", "Liberica"};
        model.addAttribute("beanType", beanType);
        String [] coffeeRoast = {"Low", "Medium", "High"};
        model.addAttribute("coffeeRoast", coffeeRoast);
        String [] coffeeFlavorNotes= {"Strong", "Bitter", "Nutty", "Milky", "Chocolatey"};
        model.addAttribute("coffeeFlavorNotes", coffeeFlavorNotes);
        String [] coffeeBrew = {"Drip", "Espresso machine", "Cold Brew", "French Press", "Moka pot"};
        model.addAttribute("coffeeBrew", coffeeBrew);
        Coffee newCoffee = new Coffee();
        return "new";
    }

    @PostMapping("/save")
    public String saveCoffee(@ModelAttribute("newCoffee") @Valid Coffee coffee, BindingResult bindingResult){
        if(bindingResult.hasErrors()) {

            return "create";
        }
        coffeeService.addCoffee(coffee);
        return "redirect:/";
    }

    @GetMapping("/edit")
    public String editCoffee(@RequestParam int id, Model model) {
        Coffee c = coffeeService.getCoffee(id);
        return "redirect:/";
    }

    @PostMapping("/update")
    public String store(@ModelAttribute("coffee") @Valid Coffee coffee, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            String [] beanType = {"Arabica", "Robusta", "Liberica"};
            model.addAttribute("beanType", beanType);
            String [] coffeeRoast = {"Low", "Medium", "High"};
            model.addAttribute("coffeeRoast", coffeeRoast);
            String [] coffeeFlavorNotes= {"Strong", "Bitter", "Nutty", "Milky", "Chocolatey"};
            model.addAttribute("coffeeFlavorNotes", coffeeFlavorNotes);
            String [] coffeeBrew = {"Drip", "Espresso machine", "Cold Brew", "French Press", "Moka pot"};
            model.addAttribute("coffeeBrew", coffeeBrew);
            System.out.println(bindingResult.getAllErrors());
            return "edit";
        }

        Coffee existingCoffee= coffeeService.getCoffee(coffee.getId());
        if(existingCoffee != null){
            coffeeService.updateCoffee(coffee.getId(), coffee);
        }
        return "redirect:/";
    }
}