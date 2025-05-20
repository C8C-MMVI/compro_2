package com.coffeemenu.coffeeSpring.controllerCodes;

import com.coffeemenu.coffeeSpring.modelCodes.Coffee;
import com.coffeemenu.coffeeSpring.serviceCodes.CoffeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

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
        return "new";
    }

    @PostMapping("/save")
    public String saveCoffee(@RequestParam String name,
                             @RequestParam String type,
                             @RequestParam String size,
                             @RequestParam double price,
                             @RequestParam String roastLevel,
                             @RequestParam String origin,
                             @RequestParam Boolean isDecaf,
                             @RequestParam int stock,
                             @RequestParam List<String> flavorNotes,
                             @RequestParam String brewMethod){
        Coffee c = new Coffee(coffeeService.getLastId() + 1,
                name,
                type,
                size,
                price,
                roastLevel,
                origin,
                false,
                stock,
                flavorNotes,
                brewMethod);
        coffeeService.addCoffee(c);
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