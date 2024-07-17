package io.github.leocklaus.desafiobtg.api.controller;

import io.github.leocklaus.desafiobtg.domain.listener.dto.ClientTotalExpensesDTO;
import io.github.leocklaus.desafiobtg.domain.service.OrderService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/customers/{customerId}/orders")
    public ResponseEntity<Page<ClientOrdersDTO>> getCustomerOrder(@PathVariable Long customerId, Pageable pageable){
        var orders = orderService.getAllOrdersByCustomerId(customerId, pageable);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/customers/{customerId}/total")
    public ResponseEntity<ClientTotalExpensesDTO> getCustomerTotalExpenses(@PathVariable Long customerId){
        ClientTotalExpensesDTO totalExpensesByClient = orderService.findTotalExpensesByClient(customerId);
        return ResponseEntity.ok(totalExpensesByClient);
    }
}
