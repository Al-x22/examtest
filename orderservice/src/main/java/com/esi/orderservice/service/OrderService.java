package com.esi.orderservice.service;

import java.util.ArrayList;
import java.util.List;

import com.esi.orderservice.dto.DeliveryDto;
import lombok.*;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.esi.orderservice.dto.OrderDto;
import com.esi.orderservice.model.Order;
import com.esi.orderservice.model.OrderStatus;
import com.esi.orderservice.model.PaymentStatus;
import com.esi.orderservice.repository.OrderRepository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.reactive.function.client.WebClient;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    private final KafkaTemplate<String, OrderDto> kafkaTemplate;
    private final WebClient webClient = WebClient.builder()
            .baseUrl("http://localhost:8083")
            .build();


    public   List<OrderDto> getAllOrders(){
        List<Order> orders =  new ArrayList<>();
        orderRepository.findAll().forEach(orders::add);
        return orders.stream().map(this::mapToOrderDto).toList();
    }    
        
    private OrderDto mapToOrderDto(Order order) {
            return OrderDto.builder()
                    .id(order.getId())
                    .userId(order.getUserId())
                    .price(order.getPrice())
                    .productId(order.getProductId())
                    .build();
    }

    public void addOrder(OrderDto orderDto) {
            Order order = Order.builder()
            .id(orderDto.getId())
            .userId(orderDto.getUserId())
            .price(orderDto.getPrice())
            .productId(orderDto.getProductId())
            .build();

            // Setting the Order status to CREATED, and the payment status to Pending
            order.setOrderStatus(OrderStatus.ORDER_CREATED);
            order.setPaymentStatus(PaymentStatus.PAYMENT_PENDING);

            // Save the order to in its current state in the Database
            orderRepository.save(order);
/* Task 2   */
            // Setting the OrderDto status to CREATED, and the payment status to Pending
            orderDto.setOrderStatus(OrderStatus.ORDER_CREATED);
            orderDto.setPaymentStatus(PaymentStatus.PAYMENT_PENDING);

            // Push the orderDto to the orderCreatedTopic topic
            kafkaTemplate.send("orderCreatedTopic", orderDto);

        log.info("An order id: {} is added to the Database", order.getId());
/* Task 2   */
    }

/* Task 4   */
    @KafkaListener(topics = "paymentTopic", groupId = "orderPaymentGroup" )
    public void updatePaymentinfo(OrderDto orderDto){
        Order order = Order.builder()
        .id(orderDto.getId())
        .userId(orderDto.getUserId())
        .price(orderDto.getPrice())
        .productId(orderDto.getProductId())
        .orderStatus(orderDto.getOrderStatus())
        .paymentStatus(orderDto.getPaymentStatus())
        .build();
        orderRepository.save(order);
        log.info("Order {} payment status updated", order.getId());
    }

    @KafkaListener(topics = "deliveryCreatedTopic", groupId = "orderDeliveryGroup" )
    public void updateDeliveryInfo(DeliveryDto deliveryDto) {
        log.info("Delivery status updated {}", deliveryDto.getId());
    }


    public PaymentStatusResponse getPaymentStatus() {
        PaymentStatusResponse response = webClient.get()
                .uri("http://localhost:8083/api/payments")
                .retrieve()
                .bodyToMono(PaymentStatusResponse.class)
                .block();
        return response;
    }

    @Data
    @AllArgsConstructor
    @Builder
    public  static class PaymentStatusResponse {
        private String status;
        private String message;
    }
    /* Task 4   */
}
