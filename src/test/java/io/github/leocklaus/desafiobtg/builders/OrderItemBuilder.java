package io.github.leocklaus.desafiobtg.builders;

import io.github.leocklaus.desafiobtg.domain.entities.OrderItem;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.math.BigDecimal;

public class OrderItemBuilder {
    private String product = "Mouse";
    private Integer quantity = 1;
    private BigDecimal price = BigDecimal.valueOf(100);

    public OrderItemBuilder withProduct(String product){
        this.product = product;
        return this;
    }

    public OrderItemBuilder withQuantity(Integer quantity){
        this.quantity = quantity;
        return this;
    }

    public OrderItemBuilder withPrice(BigDecimal price){
        this.price = price;
        return this;
    }

    public OrderItem build(){
        return new OrderItem(this.product, this.quantity, this.price);
    }

}
