package com.usecase.demobank;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class DemobankApplication {
	public static void main(String[] args) {
		
		SpringApplication.run(DemobankApplication.class, args);
	}

}
