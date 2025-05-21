package com.coffeemenu.coffeeSpring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class CoffeeApplication {

	public static void main(String[] args) {
		SpringApplication.run(CoffeeApplication.class, args);
	}

	public static void run(){
		String plainPassword = "CoffeeToAll";
		String hash = new BCryptPasswordEncoder().encode(plainPassword);
		System.out.println(hash);
	}
}
