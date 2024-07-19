package io.github.leocklaus.desafiobtg.factories;

import io.github.leocklaus.desafiobtg.domain.entities.Order;
import io.github.leocklaus.desafiobtg.domain.entities.OrderItem;
import io.github.leocklaus.desafiobtg.domain.listener.dto.OrderCreatedEvent;
import io.github.leocklaus.desafiobtg.domain.listener.dto.OrderItemsEvent;

import java.util.List;

public class OrderCreatedEventFactory {

    public static OrderCreatedEvent withCustomOrder(Order order){
        return new OrderCreatedEvent(
                order.getOrderId(),
                order.getCustomerId(),
                fromOrderItems(order.getItems())
        );
    }

    private static List<OrderItemsEvent> fromOrderItems(List<OrderItem> items){
        return items.stream().map(item -> new OrderItemsEvent(
                item.getProduct(),
                item.getQuantity(),
                item.getPrice())).toList();
    }

}
