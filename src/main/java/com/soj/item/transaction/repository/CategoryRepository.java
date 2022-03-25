package com.soj.item.transaction.repository;

import com.soj.item.transaction.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category,Long> {
}
