package io.github.leocklaus.desafiobtg.api.controller;

import io.github.leocklaus.desafiobtg.domain.listener.dto.ClientTotalExpensesDTO;
import io.github.leocklaus.desafiobtg.domain.service.OrderService;
import io.github.leocklaus.desafiobtg.factories.CustomerExpensesFactory;
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
import org.springframework.http.HttpStatusCode;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderControllerTest {

    @Mock
    OrderService orderService;

    @InjectMocks
    OrderController orderController;

    @Captor
    ArgumentCaptor<Long> clientIdCaptor;

    @Captor
    ArgumentCaptor<Pageable> pageableCaptor;

    @Test
    void getCustomerOrderShouldReturnHttpStatusOk() {

        Long customerId = 1L;
        Pageable pageable = PageRequest.of(0,10);
        Page<ClientOrdersDTO> dto = OrdersByCustomerFactory.withOneProductAsDTO();


        doReturn(dto).when(orderService).getAllOrdersByCustomerId(anyLong(), any());

        var response = orderController.getCustomerOrder(customerId, pageable);

        assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());

    }

    @Test
    void getCustomerOrderShouldPassCorrectParamsToService() {

        Long customerId = 1L;
        Pageable pageable = PageRequest.of(0,10);
        Page<ClientOrdersDTO> dto = OrdersByCustomerFactory.withOneProductAsDTO();

        doReturn(dto).when(orderService).getAllOrdersByCustomerId(clientIdCaptor.capture(), pageableCaptor.capture());

        orderController.getCustomerOrder(customerId, pageable);

        assertEquals(1L, clientIdCaptor.getValue());
        assertEquals(0,pageableCaptor.getValue().getPageNumber());
        assertEquals(10, pageableCaptor.getValue().getPageSize());
        verify(orderService, times(1)).getAllOrdersByCustomerId(any(), any());

    }

    @Test
    void getCustomerOrderShouldReturnDTOCorrectly() {
        Long customerId = 1L;
        Pageable pageable = PageRequest.of(0,10);
        Page<ClientOrdersDTO> dto = OrdersByCustomerFactory.withOneProductAsDTO();

        doReturn(dto).when(orderService).getAllOrdersByCustomerId(anyLong(), any());

        var response = orderController.getCustomerOrder(customerId, pageable);

        assertEquals(dto, response.getBody());
    }

    @Test
    void getCustomerTotalExpensesShouldReturnHttpStatusOk() {
        Long customerId = 1L;
        ClientTotalExpensesDTO dto = CustomerExpensesFactory.with200Reais();

        doReturn(dto).when(orderService).findTotalExpensesByClient(any());

        var response = orderController.getCustomerTotalExpenses(customerId);

        assertEquals(response.getStatusCode(), HttpStatusCode.valueOf(200));
    }

    @Test
    void getCustomerTotalExpensesShouldPassCorrectParamsToService() {
        Long customerId = 1L;
        ClientTotalExpensesDTO dto = CustomerExpensesFactory.with200Reais();

        doReturn(dto).when(orderService).findTotalExpensesByClient(clientIdCaptor.capture());

        var response = orderController.getCustomerTotalExpenses(customerId);

        assertEquals(customerId, clientIdCaptor.getValue());
        verify(orderService, times(1)).findTotalExpensesByClient(any());
    }

    @Test
    void getCustomerTotalExpensesShouldReturnDTOCorrectly() {
        Long customerId = 1L;
        ClientTotalExpensesDTO dto = CustomerExpensesFactory.with200Reais();

        doReturn(dto).when(orderService).findTotalExpensesByClient(any());

        var response = orderController.getCustomerTotalExpenses(customerId);

        assertEquals(dto, response.getBody());
    }
}