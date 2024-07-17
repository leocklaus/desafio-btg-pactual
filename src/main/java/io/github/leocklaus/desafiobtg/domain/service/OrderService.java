package io.github.leocklaus.desafiobtg.domain.service;

import io.github.leocklaus.desafiobtg.api.controller.ClientOrdersDTO;
import io.github.leocklaus.desafiobtg.domain.entities.Order;
import io.github.leocklaus.desafiobtg.domain.listener.dto.ClientTotalExpensesDTO;
import io.github.leocklaus.desafiobtg.domain.listener.dto.OrderCreatedEvent;
import io.github.leocklaus.desafiobtg.domain.repository.OrderRepository;
import org.bson.Document;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final MongoTemplate mongoTemplate;

    public OrderService(OrderRepository orderRepository, MongoTemplate mongoTemplate) {
        this.orderRepository = orderRepository;
        this.mongoTemplate = mongoTemplate;
    }

    public Page<ClientOrdersDTO> getAllOrdersByCustomerId(Long customerId, Pageable pageable){
        return orderRepository.findAllByCustomerId(customerId, pageable)
                .map(order-> new ClientOrdersDTO(order));
    }

    public void save(OrderCreatedEvent orderCreatedEvent){
        Order order = new Order(orderCreatedEvent);

        orderRepository.save(order);
    }

    public ClientTotalExpensesDTO findTotalExpensesByClient(Long customerId){
        var aggregation = newAggregation(
                match(Criteria.where("customerId").is(customerId)),
                group().sum("total").as("total")
        );

        var response = mongoTemplate.aggregate(aggregation, "tb_orders", Document.class);

        var totalExpenses = new BigDecimal(response.getUniqueMappedResult().get("total").toString());

        return new ClientTotalExpensesDTO(customerId, totalExpenses);
    }

}
