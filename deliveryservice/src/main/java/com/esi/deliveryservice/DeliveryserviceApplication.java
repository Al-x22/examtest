package com.esi.deliveryservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DeliveryserviceApplication {

	public static void main(String[] args) {
		DatabaseInitializer.initialize("deliveryservice_db");
		SpringApplication.run(DeliveryserviceApplication.class, args);
	}

}
