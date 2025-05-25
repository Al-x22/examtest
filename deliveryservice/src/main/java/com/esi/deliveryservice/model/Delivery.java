package com.esi.deliveryservice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "deliveries")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Delivery {

    @Id
    private Integer id;
    private Integer userId;
    private Integer productId;
    private BigDecimal price;
    private String deliveryStatus;

}
