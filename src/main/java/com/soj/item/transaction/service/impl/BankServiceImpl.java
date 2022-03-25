package com.soj.item.transaction.service.impl;

import com.soj.item.transaction.model.Bank;
import com.soj.item.transaction.repository.BankRepository;
import com.soj.item.transaction.service.BankService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class BankServiceImpl implements BankService {
    @Autowired
    private BankRepository repository;

    public List<Bank> getAllBank() {

        return repository.findAll();
    }


    public Bank getBank(Long id) {
        Optional<Bank> bank = repository.findById(id);
        if (bank.isPresent()) {
            return bank.get();
        }
        throw new IllegalArgumentException("Id not found");
    }

    public Bank addNewBank(Bank bank) {

        return repository.saveAndFlush(bank);
    }

    public void deleteBank(long id) {

        try {


            repository.deleteById(id);
        } catch (Exception e) {
            throw new IllegalArgumentException("Id not found");
        }
    }

    public Bank updateBank(long id, Bank bank) {

        Optional<Bank> optional = repository.findById(id);
        BeanUtils.copyProperties(bank, optional, "bank_id");
        if (optional.isPresent()) {
            Bank bank1 = optional.get();
            bank1.setName(bank.getName());
            bank1.setAccNo(bank.getAccNo());
            bank1.setAddress(bank.getAddress());
            return repository.saveAndFlush(bank1);
        } else {
            throw new IllegalArgumentException("Id not present");
        }
    }
}
