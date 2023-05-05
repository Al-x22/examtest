package com.esi.paymentservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PaymentserviceApplication {

	public static void main(String[] args) {
		DatabaseInitializer.initialize("paymentservice_db");
		SpringApplication.run(PaymentserviceApplication.class, args);
	}
}
