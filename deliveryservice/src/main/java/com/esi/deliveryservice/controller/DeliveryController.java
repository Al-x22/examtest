package com.esi.deliveryservice.controller;


import com.esi.deliveryservice.dto.DeliveryDto;
import com.esi.deliveryservice.service.DeliveryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class DeliveryController {

    private final DeliveryService deliveryService;

    @GetMapping("/deliveries")
    public List<DeliveryDto> getAllDeliveries() {
        return deliveryService.getAllDeliveries();
    }

    @PostMapping("/deliveries")
    public ResponseEntity<String> createDelivery(@RequestBody DeliveryDto deliveryDto) {

        deliveryService.createDelivery(deliveryDto);
        return ResponseEntity.ok("Delivery Created");
    }
}
