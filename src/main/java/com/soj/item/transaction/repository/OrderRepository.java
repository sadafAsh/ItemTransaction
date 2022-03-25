package com.soj.item.transaction.repository;

import com.soj.item.transaction.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
