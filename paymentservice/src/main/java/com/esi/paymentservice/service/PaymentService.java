package com.esi.paymentservice.service;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.esi.paymentservice.dto.OrderDto;
import com.esi.paymentservice.dto.OrderStatus;
import com.esi.paymentservice.dto.PaymentStatus;
import com.esi.paymentservice.model.UserBalance;
import com.esi.paymentservice.repository.PaymentRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@RequiredArgsConstructor
@Service
public class PaymentService {

@Autowired
private PaymentRepository paymentRepository;

private final KafkaTemplate<String, OrderDto> kafkaTemplate;

/*  Task 3  */

@KafkaListener(topics = "orderCreatedTopic", groupId = "orderEventGroup" )
public void processpayment(OrderDto orderDto){

UserBalance userBalance = paymentRepository.findById(orderDto.getUserId()).get();
    log.info("Log message - userBalance: {} ", userBalance.getBalance());

    if (userBalance.getBalance().compareTo(orderDto.getPrice()) == 1) {
        userBalance.setBalance(userBalance.getBalance().subtract(orderDto.getPrice()));
        paymentRepository.save(userBalance);

        orderDto.setPaymentStatus(PaymentStatus.PAYMENT_COMPLETED);
        orderDto.setOrderStatus(OrderStatus.ORDER_COMPLETED);

        kafkaTemplate.send("paymentTopic", orderDto);

      } else {

        orderDto.setPaymentStatus(PaymentStatus.PAYMENT_FAILED);
        orderDto.setOrderStatus(OrderStatus.ORDER_CANCELLED);

        kafkaTemplate.send("paymentTopic", orderDto);
      } 
}

    public PaymentStatusResponse getPayment() {
        return PaymentStatusResponse.builder().status("Correct").message( "Payment Service is running").build();
    }

    @Data
    @AllArgsConstructor
    @Builder
    public  static class PaymentStatusResponse {
        private String status;
        private String message;
    }
    /*  Task 3  */
}

