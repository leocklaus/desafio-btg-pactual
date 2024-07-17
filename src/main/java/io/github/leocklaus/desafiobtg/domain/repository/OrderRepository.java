package io.github.leocklaus.desafiobtg.domain.repository;

import io.github.leocklaus.desafiobtg.domain.entities.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OrderRepository extends MongoRepository<Order, Long> {
    Page<Order> findAllByCustomerId(Long customerId, Pageable pageable);
}
