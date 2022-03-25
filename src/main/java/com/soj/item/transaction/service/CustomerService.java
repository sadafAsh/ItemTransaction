package com.soj.item.transaction.service;

import com.soj.item.transaction.model.Customer;

import java.util.List;

public interface CustomerService {
    public List<Customer> getAllCustomer();

    public Customer addCustomer(Customer customer);

    public Customer getCustomer(Long id);

    public void deleteCustomer(long id);

    public Customer updateCustomer(Customer customer, long id);
}
