package com.soj.item.transaction.service.impl;

import com.soj.item.transaction.model.Bank;
import com.soj.item.transaction.model.Customer;
import com.soj.item.transaction.repository.BankRepository;
import com.soj.item.transaction.repository.CustomerRepository;
import com.soj.item.transaction.service.CustomerService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private CustomerRepository repository;

    @Autowired
    private BankRepository bankRepository;

    public List<Customer> getAllCustomer() {

        return repository.findAll();
    }

    public Customer addCustomer(Customer customer) {
        Customer customer1 = new Customer();
        customer1.setId(customer.getId());
        customer1.setName(customer.getName());
        customer1.setBank(customer.getBank());
        long byAccNo = customer1.getBank().getAccNo();
        List<Bank> bankAccNo = bankRepository.findByAccNo(byAccNo);
        if (bankAccNo.isEmpty()) {
            Bank bank = new Bank();
            bank.setName(customer.getBank().getName());
            bank.setAddress(customer.getBank().getAddress());
            bank.setAccNo(customer.getBank().getAccNo());
            customer1.setBank(bank);
        } else {

            customer1.setBank(bankAccNo.get(0));
        }

        return repository.saveAndFlush(customer1);
    }


    public Customer getCustomer(Long id) {
        Optional<Customer> customer = repository.findById(id);
        if (customer.isPresent()) {
            return customer.get();
        }
        throw new IllegalArgumentException("Id not present");
    }

    public void deleteCustomer(long id) {


        try {


            repository.deleteById(id);
        } catch (Exception e) {
            throw new IllegalArgumentException("Id not found");
        }

    }

    public Customer updateCustomer(Customer customer, long id) {

        Optional<Customer> optionalCustomer = repository.findById(id);
        BeanUtils.copyProperties(customer, optionalCustomer, "customer_id");
        if (optionalCustomer.isPresent()) {
            Customer customer1 = optionalCustomer.get();
            customer1.setName(customer.getName());
            Bank bank = new Bank();
            bank.setName(customer.getBank().getName());
            bank.setAddress(customer.getBank().getAddress());
            bank.setAccNo(customer.getBank().getAccNo());
            customer1.setBank(bank);
            return repository.saveAndFlush(customer1);
        } else {
            throw new IllegalArgumentException("Id not found");
        }
    }

}

