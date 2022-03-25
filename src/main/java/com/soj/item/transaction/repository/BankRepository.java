package com.soj.item.transaction.repository;

import com.soj.item.transaction.model.Bank;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BankRepository extends JpaRepository<Bank, Long> {

    public List<Bank> findByAccNo(long accNo);
}
