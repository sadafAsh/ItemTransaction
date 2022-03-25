package com.soj.item.transaction.controller;

import com.soj.item.transaction.model.Bank;
import com.soj.item.transaction.model.Customer;
import com.soj.item.transaction.service.BankService;
import com.soj.item.transaction.service.CustomerService;
import com.soj.item.transaction.util.Resource;
import com.soj.item.transaction.util.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static com.soj.item.transaction.model.Customer.OBJECT_TYPE;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class CustomerControllerTest {

    @Autowired
    private CustomerController controller;
    @MockBean
    private CustomerService service;

    @Test
    void findTheListOfCustomer() {
        List<Customer> customers = new ArrayList<>();
        Customer customer = new Customer();
        customer.setId(1l);
        customers.add(customer);
        when(service.getAllCustomer()).thenReturn(customers);
        ResponseEntity<List<Resource<Customer>>> resource = controller.findAllCustomer();
        Assertions.assertEquals(1, customers.size());
    }

    @Test
    void findById() {
        Customer customer = new Customer();
        customer.setId(1l);
        when(service.getCustomer(1l)).thenReturn(customer);
        ResponseEntity<Resource<Customer>> resources = controller.findById(1l);
        Assertions.assertEquals(1l, customer.getId());
    }

    @Test
    void testToCreateNewCustomer() {
        Customer customer = new Customer();
        customer.setId(1l);
        Resource<Customer> request = new Resource<>(1l, OBJECT_TYPE, customer);
        when(service.addCustomer(any())).thenReturn(customer);
        ResponseEntity<Resource<Response>> resource = controller.creteNewCustomer(request);
        Assertions.assertEquals(1l, request.getId());
    }

    @Test
    void testToDeleteCustomerById() {
        Customer customer = new Customer();
        customer.setId(1l);
        doNothing().when(service).deleteCustomer(1l);
        ResponseEntity<Resource<Response>> resources = controller.deleteCustomerById(1l);
        Assertions.assertEquals(1l, customer.getId());
    }

    @Test
    void updateCustomerById() {
        Customer customer = new Customer();
        customer.setId(1l);
        Resource<Customer> request = new Resource<>(1l, OBJECT_TYPE, customer);
        when(service.updateCustomer(any(), anyLong())).thenReturn(customer);
        ResponseEntity<Resource<Response>> resources = controller.updatingCustomerById(1l, request);
        Assertions.assertEquals(1l, request.getId());

    }


}