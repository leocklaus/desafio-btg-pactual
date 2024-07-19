package io.github.leocklaus.desafiobtg.builders;

import io.github.leocklaus.desafiobtg.domain.entities.Order;
import io.github.leocklaus.desafiobtg.domain.entities.OrderItem;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.math.BigDecimal;
import java.util.List;

public class OrderBuilder {

    private Long orderId = 1L;
    private Long customerId = 1L;
    private BigDecimal total = BigDecimal.valueOf(100);
    private List<OrderItem> items = List.of(new OrderItemBuilder().build());

    public OrderBuilder withOrderId(Long orderId){
        this.orderId = orderId;
        return this;
    }

    public OrderBuilder withCustomerId(Long customerId){
        this.customerId = customerId;
        return this;
    }

    public OrderBuilder withTotal(BigDecimal total){
        this.total = total;
        return this;
    }

    public OrderBuilder withItem(List<OrderItem> items){
        this.items = items;
        return this;
    }

    public Order build(){
        return new Order(this.orderId, this.customerId, this.total, this.items);
    }
}
