package io.github.leocklaus.desafiobtg.api.controller;

import io.github.leocklaus.desafiobtg.domain.entities.Order;

import java.math.BigDecimal;

public record ClientOrdersDTO(Long orderId,
                              Long customerId,
                              BigDecimal total) {

    public ClientOrdersDTO (Order order){
        this(
                order.getOrderId(),
                order.getCustomerId(),
                order.getTotal()
        );
    }
}
