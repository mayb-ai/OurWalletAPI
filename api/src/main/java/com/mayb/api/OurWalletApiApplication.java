package com.mayb.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.security.autoconfigure.SecurityAutoConfiguration;

@SpringBootApplication
public class OurWalletApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(OurWalletApiApplication.class, args);
	}

}
