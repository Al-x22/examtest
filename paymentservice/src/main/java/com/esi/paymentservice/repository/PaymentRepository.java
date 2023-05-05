package com.esi.paymentservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.esi.paymentservice.model.UserBalance;

public interface PaymentRepository extends JpaRepository<UserBalance, Integer> {
}
