package com.zentro;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication

public class ZentroApplication {

	public static void main(String[] args) {
		SpringApplication.run(ZentroApplication.class, args);
		System.out.println("application started1");
		System.out.print("application started");
	}


}
