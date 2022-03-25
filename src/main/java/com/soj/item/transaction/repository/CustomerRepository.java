package com.soj.item.transaction.repository;

import com.soj.item.transaction.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CustomerRepository extends JpaRepository<Customer, Long> {

}
