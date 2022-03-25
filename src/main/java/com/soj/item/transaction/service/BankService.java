package com.soj.item.transaction.service;

import com.soj.item.transaction.model.Bank;

import java.util.List;

public interface BankService {
    public List<Bank> getAllBank();
    public Bank getBank(Long id);
    public Bank addNewBank(Bank bank);
    public void deleteBank(long id);
    public Bank updateBank(long id, Bank bank);
}
