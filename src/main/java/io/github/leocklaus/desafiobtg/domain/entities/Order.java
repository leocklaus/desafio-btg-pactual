package io.github.leocklaus.desafiobtg.domain.entities;

import io.github.leocklaus.desafiobtg.domain.listener.dto.OrderCreatedEvent;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.math.BigDecimal;
import java.util.List;

@Document(collection = "tb_orders")
public class Order {
    @MongoId
    private Long orderId;
    @Indexed(name = "customer_id_index")
    private Long customerId;
    @Field(targetType = FieldType.DECIMAL128)
    private BigDecimal total;
    private List<OrderItem> items;

    public Order() {
    }

    public Order(OrderCreatedEvent event){
        this.orderId = event.codigoPedido();
        this.customerId = event.codigoCliente();
        this.items = getOrderItems(event);
        this.total = getTotalValue(event);
    }

    private static BigDecimal getTotalValue(OrderCreatedEvent event) {
        return event.itens().stream()
                .map(items -> items.preco().multiply(BigDecimal.valueOf(items.quantidade())))
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);
    }

    private static List<OrderItem> getOrderItems(OrderCreatedEvent event) {
        return event.itens().stream().map(item -> {
            return new OrderItem(item.produto(), item.quantidade(), item.preco());
        }).toList();
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public void setItems(List<OrderItem> items) {
        this.items = items;
    }
}
