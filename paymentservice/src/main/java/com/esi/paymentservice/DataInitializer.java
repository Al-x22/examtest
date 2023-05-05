package com.esi.paymentservice;


import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.esi.paymentservice.model.UserBalance;
import com.esi.paymentservice.repository.PaymentRepository;


import java.math.BigDecimal;


@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner loadProductsData(PaymentRepository paymentRepository) {
        return args -> {
            UserBalance userBalance1 = new UserBalance();
			userBalance1.setUserId(01);
			userBalance1.setBalance(BigDecimal.valueOf(1233));

			paymentRepository.save(userBalance1);

            UserBalance userBalance2 = new UserBalance();
			userBalance2.setUserId(02);
			userBalance2.setBalance(BigDecimal.valueOf(1399));

			paymentRepository.save(userBalance2);
        };
    }
}

