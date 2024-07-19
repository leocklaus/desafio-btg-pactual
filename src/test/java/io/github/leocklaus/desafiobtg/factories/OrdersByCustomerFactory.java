package io.github.leocklaus.desafiobtg.factories;

import io.github.leocklaus.desafiobtg.api.controller.ClientOrdersDTO;
import io.github.leocklaus.desafiobtg.domain.entities.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.math.BigDecimal;
import java.util.List;

public class OrdersByCustomerFactory {

    public static Page<ClientOrdersDTO> withOneProductAsDTO(){

        ClientOrdersDTO clientOrdersDTO = new ClientOrdersDTO(
                1L,
                1L,
                BigDecimal.valueOf(150)
        );

        return new PageImpl<>(List.of(clientOrdersDTO));
    }

    public static Page<ClientOrdersDTO> withCustomOrderAsDTO(Order order){

        ClientOrdersDTO clientOrdersDTO = new ClientOrdersDTO(
                order.getOrderId(),
                order.getCustomerId(),
                order.getTotal()
        );

        return new PageImpl<>(List.of(clientOrdersDTO));
    }

    public static Page<Order> withCustomOrder(Order order){

        return new PageImpl<>(List.of(order));
    }
}
