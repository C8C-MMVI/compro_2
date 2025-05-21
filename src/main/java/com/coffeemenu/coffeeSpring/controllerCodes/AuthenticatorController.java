package com.coffeemenu.coffeeSpring.controllerCodes;

import com.coffeemenu.coffeeSpring.modelCodes.UserID;
import com.coffeemenu.coffeeSpring.serviceCodes.UserIDService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthenticatorController {
    @Autowired
    UserIDService appUserService;

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("user", new UserID());

        return "login";
    }
    @PostMapping("/login")
    public String login(@ModelAttribute("user") @Valid UserID formUser, BindingResult bindingResult, HttpSession session, Model model){
        if(bindingResult.hasErrors()){
            return "login";
        }

        //authenticate
        UserID foundUser = appUserService.findByUsername(formUser.getUsername());
        if(foundUser != null && new BCryptPasswordEncoder().matches(formUser.getPassword(), foundUser.getPassword())){
            session.setAttribute("user", foundUser);
            return "redirect:/";
        }else{
            String error ="Invalid credentials";
            model.addAttribute("error", error);
        }

        return "login";

    }

    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "redirect:/login";
    }
}
