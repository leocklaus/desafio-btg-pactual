package io.github.leocklaus.desafiobtg.api.controller;

import io.github.leocklaus.desafiobtg.builders.OrderBuilder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ClientOrdersDTOTest {

    @Test
    void ShouldMapFromOrderEntityCorrectly(){

        var order = new OrderBuilder()
                .withOrderId(1L)
                .withCustomerId(1L)
                .withTotal(BigDecimal.valueOf(250))
                .build();


        ClientOrdersDTO dto = new ClientOrdersDTO(order);

        assertEquals(1L, dto.orderId());
        assertEquals(1L, dto.customerId());
        assertEquals(BigDecimal.valueOf(250), dto.total());
    }

}