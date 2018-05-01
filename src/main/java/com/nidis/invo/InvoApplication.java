package com.nidis.invo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAutoConfiguration
public class InvoApplication {

	public static void main(String[] args) {
		SpringApplication.run(InvoApplication.class, args);
	}
}
