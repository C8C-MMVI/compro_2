package com.coffeemenu.coffeeSpring.modelCodes;

import jakarta.validation.constraints.NotBlank;

public class UserID {
    @NotBlank(message = "Username should not be blank")
    private String username;
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
