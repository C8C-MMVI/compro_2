package com.coffeemenu.coffeeSpring.serviceCodes;

import com.coffeemenu.coffeeSpring.modelCodes.UserID;
import jakarta.annotation.PostConstruct;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserIDService {
    private List<UserID> appUsers;

    @PostConstruct
    public void init() throws IOException {
        appUsers = new ArrayList<>();
        File file = new File("data/users.csv"); // path relative to project root

        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line;
        reader.readLine(); // skip header
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(",");
            UserID appUser = new UserID();
            appUser.setUsername(parts[0]);
            appUser.setPassword(parts[1]);
            appUsers.add(appUser);
        }
    }

    public UserID findByUsername(String username) {
        return appUsers.stream()
                .filter(u -> u.getUsername().equals(username))
                .findFirst()
                .orElse(null);
    }

    public void save(UserID appUser) {
        appUser.setPassword(new BCryptPasswordEncoder().encode(appUser.getPassword()));
        //to save new user like saving student in the Student Service
    }
}

