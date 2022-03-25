package com.soj.item.transaction.repository;

import com.soj.item.transaction.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
