package io.github.leocklaus.desafiobtg.domain.listener.dto;

import java.math.BigDecimal;

public record OrderItemsEvent(
        String produto,
        Integer quantidade,
        BigDecimal preco

) {
}
