package com.soj.item.transaction.service.impl;

import com.soj.item.transaction.controller.CustomerController;
import com.soj.item.transaction.model.Bank;
import com.soj.item.transaction.model.Customer;
import com.soj.item.transaction.repository.BankRepository;
import com.soj.item.transaction.repository.CustomerRepository;
import com.soj.item.transaction.service.CustomerService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class CustomerServiceImplTest {

    @Autowired
    private CustomerService service;

    @MockBean
    private CustomerRepository repository;
    @MockBean
    private BankRepository bankRepository;

    @Test
    void testToGetAllCustomer() {
        List<Customer> customers = new ArrayList<>();
        Customer customer = new Customer();
        customer.setId(1l);
        customers.add(customer);
        when(repository.findAll()).thenReturn(customers);
        service.getAllCustomer();
        Assertions.assertEquals(1, customers.size());
    }

    @Test
    void testToGetCustomerById() {
        Customer customer = new Customer();
        customer.setId(1l);
        when(repository.findById(1l)).thenReturn(java.util.Optional.of(customer));
        service.getCustomer(1l);
        Assertions.assertEquals(1l, customer.getId());
    }

    @Test
    void testToGetExceptionCustomerById() {
        Assertions.assertThrows(IllegalArgumentException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                Customer customer = new Customer();
                customer.setId(1l);
                when(repository.findById(2l)).thenReturn(java.util.Optional.of(customer));
                service.getCustomer(1l);
            }
        });
    }

    @Test
    void testToAddNewCustomerIfBankAccNoIsNotPresent() {
        Customer customer = new Customer();
        customer.setId(1l);
        Bank bank = new Bank();
        bank.setAccNo(123);
        customer.setBank(bank);
        when(repository.saveAndFlush(any())).thenReturn(customer);
        service.addCustomer(customer);
        Assertions.assertEquals(1l, customer.getId());
    }

    @Test
    void testToAddNewCustomerIfBankAccNoIsAlreadyExist() {
        Customer customer = new Customer();
        Bank bank = new Bank();
        customer.setId(1l);
        bank.setAccNo(12);
        customer.setBank(bank);
        long bankAccNo = 12;
        List<Bank> accNo = bankRepository.findByAccNo(bankAccNo);
        accNo.add(bank);

        when(bankRepository.findByAccNo(12)).thenReturn(accNo);

        when(repository.saveAndFlush(any())).thenReturn(customer);
        service.addCustomer(customer);
        Assertions.assertEquals(12, customer.getBank().getAccNo());
        Assertions.assertEquals(1l, customer.getId());

    }

    @Test
    void testToDeleteCustomerById() {
        Customer customer = new Customer();
        customer.setId(1l);
        doNothing().when(repository).deleteById(1l);
        service.deleteCustomer(1l);
        Assertions.assertEquals(1l, customer.getId());
    }


    @Test
    void testToDeleteExceptionCustomerById() {
        assertThrows(IllegalArgumentException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                Customer customer = new Customer();
                customer.setId(1l);
                doThrow(IllegalArgumentException.class).when(repository).deleteById(1l);
                service.deleteCustomer(1l);
            }
        });
    }


    @Test
    void testUpdateCustomerById() {
        Customer customer = new Customer();
        customer.setId(1l);
        Bank bank = new Bank();
        bank.setId(1l);
        customer.setBank(bank);
        when(repository.findById(1l)).thenReturn(java.util.Optional.of(customer));
        when(repository.saveAndFlush(any())).thenReturn(customer);
        service.updateCustomer(customer, 1l);
        Assertions.assertEquals(1l, customer.getId());
    }

    @Test
    void testToThrowExceptionInUpdateCustomerById() {
        Assertions.assertThrows(IllegalArgumentException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                Customer customer = new Customer();
                customer.setId(1l);
                when(repository.findById(2l)).thenReturn(java.util.Optional.of(customer));
                when(repository.saveAndFlush(any())).thenReturn(customer);
                service.updateCustomer(customer, 1l);

            }
        });
    }
}
