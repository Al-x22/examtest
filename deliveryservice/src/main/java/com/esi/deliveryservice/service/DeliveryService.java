package com.esi.deliveryservice.service;

import com.esi.deliveryservice.dto.DeliveryDto;
import com.esi.deliveryservice.model.Delivery;
import com.esi.deliveryservice.repository.DeliveryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class DeliveryService {

    private final DeliveryRepository deliveryRepository;
    private final KafkaTemplate<String, DeliveryDto> kafkaTemplate;


    public List<DeliveryDto> getAllDeliveries() {
        log.info("Fetching all deliveries");
        List<DeliveryDto> deliveries = deliveryRepository.findAll().stream()
                .map(delivery -> new DeliveryDto(delivery.getId(), delivery.getUserId(), delivery.getProductId(), delivery.getPrice(), delivery.getDeliveryStatus()))
                .toList();
        log.info("Total deliveries fetched: {}", deliveries.size());
        return deliveries;
    }

    public void createDelivery(DeliveryDto deliveryDto) {
        log.info("Creating delivery for user: {}, product: {}, price: {}", deliveryDto.getId(), deliveryDto.getProductId(), deliveryDto.getPrice());
        Delivery delivery = new Delivery();
        delivery.setId(deliveryDto.getId());
        delivery.setUserId(deliveryDto.getUserId());
        delivery.setProductId(deliveryDto.getProductId());
        delivery.setPrice(deliveryDto.getPrice());
        delivery.setDeliveryStatus(deliveryDto.getDeliveryStatus());

        // Save the delivery to the repository
        deliveryRepository.save(delivery);
        log.info("Delivery created successfully with ID: {}", delivery.getId());
        // Send the delivery event to Kafka
        kafkaTemplate.send("deliveryCreatedTopic", deliveryDto);
    }
}
