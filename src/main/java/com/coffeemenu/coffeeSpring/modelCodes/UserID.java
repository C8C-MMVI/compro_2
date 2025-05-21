package com.coffeemenu.coffeeSpring.modelCodes;

import jakarta.validation.constraints.NotBlank;

public class UserID {
    @NotBlank(message = "Username is missing/blank")
    private String username;
    @NotBlank(message = "Password is missing/blank")
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
