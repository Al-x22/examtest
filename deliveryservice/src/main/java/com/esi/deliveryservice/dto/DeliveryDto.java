package com.esi.deliveryservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryDto {

    private Integer id;
    private Integer userId;
    private Integer productId;
    private BigDecimal price;
    private String deliveryStatus;
}
