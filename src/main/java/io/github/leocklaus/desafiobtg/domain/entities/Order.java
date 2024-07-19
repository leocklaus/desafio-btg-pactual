package io.github.leocklaus.desafiobtg.domain.entities;

import io.github.leocklaus.desafiobtg.domain.listener.dto.OrderCreatedEvent;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

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

    public Order(Long orderId, Long customerId, BigDecimal total, List<OrderItem> items) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.total = total;
        this.items = items;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(orderId, order.orderId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(orderId);
    }
}
