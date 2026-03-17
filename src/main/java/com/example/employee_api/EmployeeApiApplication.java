package com.example.employee_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class EmployeeApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmployeeApiApplication.class, args);
	}

}
