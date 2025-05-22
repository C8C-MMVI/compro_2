package com.coffeemenu.coffeeSpring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class CoffeeSpringApplication {

	public static void main(String[] args) {
		SpringApplication.run(CoffeeSpringApplication.class, args);
		run();
	}

	public static void run(){
		String plainPassword = "CoffeeToAll";
		String hash = new BCryptPasswordEncoder().encode(plainPassword);
		System.out.println("User password (if you forgot): " + plainPassword);
		System.out.println("Hash password: " + hash);
	}
}
