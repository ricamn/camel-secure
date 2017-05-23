package com.ricamn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@ImportResource("spring-security.xml")
public class CamelSecureApplication {

	public static void main(String[] args) {
		SpringApplication.run(CamelSecureApplication.class, args);
	}
}
