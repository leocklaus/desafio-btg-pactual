package io.github.leocklaus.desafiobtg.domain.service;

import io.github.leocklaus.desafiobtg.api.controller.ClientOrdersDTO;
import io.github.leocklaus.desafiobtg.builders.OrderBuilder;
import io.github.leocklaus.desafiobtg.domain.entities.Order;
import io.github.leocklaus.desafiobtg.domain.listener.dto.OrderCreatedEvent;
import io.github.leocklaus.desafiobtg.domain.repository.OrderRepository;
import io.github.leocklaus.desafiobtg.factories.OrderCreatedEventFactory;
import io.github.leocklaus.desafiobtg.factories.OrdersByCustomerFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    OrderRepository orderRepository;

    @Mock
    MongoTemplate mongoTemplate;

    @Captor
    ArgumentCaptor<Long> customerIdCaptor;

    @Captor
    ArgumentCaptor<Pageable> pageableCaptor;

    @Captor
    ArgumentCaptor<Order> orderArgumentCaptor;

    @InjectMocks
    OrderService orderService;

    @Test
    void getAllOrdersByCustomerIdShouldReturnADTOCorrectly() {

        Long customerId = 1L;
        Pageable pageable = PageRequest.of(0, 10);
        Order order = new OrderBuilder()
                .withCustomerId(customerId)
                .build();
        Page<Order> orderPage = OrdersByCustomerFactory.withCustomOrder(order);
        Page<ClientOrdersDTO> responseDTO = OrdersByCustomerFactory.withCustomOrderAsDTO(order);

        doReturn(orderPage).when(orderRepository).findAllByCustomerId(anyLong(), any());

        var response = orderService.getAllOrdersByCustomerId(customerId, pageable);

        assertEquals(responseDTO, response);

    }

    @Test
    void getAllOrdersByCustomerIdShouldPassTheCorrectParamsToRepositoryQuery() {
        Long customerId = 1L;
        Pageable pageable = PageRequest.of(0, 10);
        Order order = new OrderBuilder()
                .withCustomerId(customerId)
                .build();
        Page<Order> orderPage = OrdersByCustomerFactory.withCustomOrder(order);
        Page<ClientOrdersDTO> responseDTO = OrdersByCustomerFactory.withCustomOrderAsDTO(order);

        doReturn(orderPage).when(orderRepository).findAllByCustomerId(
                customerIdCaptor.capture(), pageableCaptor.capture());

        var response = orderService.getAllOrdersByCustomerId(customerId, pageable);

        assertEquals(customerId, customerIdCaptor.getValue());
        assertEquals(pageable.getPageNumber(), pageableCaptor.getValue().getPageNumber());
        assertEquals(pageable.getPageSize(), pageableCaptor.getValue().getPageSize());
    }

    @Test
    void saveShouldCallRepositoryWithCorrectParams() {

        Order order = new OrderBuilder().build();
        OrderCreatedEvent orderDTO = OrderCreatedEventFactory.withCustomOrder(order);

        doReturn(order).when(orderRepository).save(orderArgumentCaptor.capture());

        orderService.save(orderDTO);

        assertEquals(order.getOrderId(), orderArgumentCaptor.getValue().getOrderId());
        assertEquals(order.getCustomerId(), orderArgumentCaptor.getValue().getCustomerId());
        assertEquals(order.getTotal(), orderArgumentCaptor.getValue().getTotal());
        assertEquals(order.getItems(), orderArgumentCaptor.getValue().getItems());

    }

    @Test
    void findTotalExpensesByClient() {
    }
}