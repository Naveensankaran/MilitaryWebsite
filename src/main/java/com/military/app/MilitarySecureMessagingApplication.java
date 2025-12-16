package com.military.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.security.autoconfigure.SecurityAutoConfiguration;

@SpringBootApplication //(exclude = {SecurityAutoConfiguration.class})
public class MilitarySecureMessagingApplication {

	public static void main(String[] args) {
		SpringApplication.run(MilitarySecureMessagingApplication.class, args);
		System.out.println("military website");
	}

}
