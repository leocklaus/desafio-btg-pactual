package io.github.leocklaus.desafiobtg.domain.listener.dto;

import java.util.List;

public record OrderCreatedEvent(
        Long codigoPedido,
        Long codigoCliente,
        List<OrderItemsEvent> itens
) {
}
