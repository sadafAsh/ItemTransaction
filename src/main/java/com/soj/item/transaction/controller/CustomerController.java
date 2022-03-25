package com.soj.item.transaction.controller;

import com.soj.item.transaction.model.Customer;
import com.soj.item.transaction.service.CustomerService;
import com.soj.item.transaction.util.Resource;
import com.soj.item.transaction.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static com.soj.item.transaction.model.Customer.OBJECT_TYPE;

@RestController
@RequestMapping("api/v1")
public class CustomerController {

    @Autowired
    private CustomerService service;

    @GetMapping("/customer")
    public ResponseEntity<List<Resource<Customer>>> findAllCustomer() {
        List<Resource<Customer>> resources = new ArrayList<>();
        List<Customer> customers = service.getAllCustomer();
        if (resources.isEmpty()) {
            customers.forEach(x -> {
                Resource<Customer> resource = new Resource<>(x.getId(), OBJECT_TYPE, x);
                resources.add(resource);
            });
        }
        return new ResponseEntity<>(resources, HttpStatus.OK);
    }

    @GetMapping("/customer/{id}")
    public ResponseEntity<Resource<Customer>> findById(@PathVariable long id) {
        Customer customer = service.getCustomer(id);
        Resource<Customer> resource = new Resource<>(customer.getId(), OBJECT_TYPE, customer);
        return new ResponseEntity<>(resource, HttpStatus.OK);


    }

    @PostMapping("/customer")
    public ResponseEntity<Resource<Response>> creteNewCustomer(@RequestBody Resource<Customer> request) {
        Customer customer = request.getAttribute();
        Customer customer1 = service.addCustomer(customer);
        Response response = new Response(customer1.getId(), "create successfully");
        Resource<Response> resource = new Resource<>(customer1.getId(), OBJECT_TYPE, response);
        return new ResponseEntity<>(resource, HttpStatus.OK);
    }

    @DeleteMapping("/customer/{id}")
    public ResponseEntity<Resource<Response>> deleteCustomerById(@PathVariable long id) {
        service.deleteCustomer(id);
        Response response = new Response(id, "delete successfully");
        Resource<Response> resource = new Resource<>(id, OBJECT_TYPE, response);
        return new ResponseEntity<>(resource, HttpStatus.OK);
    }

    @PutMapping("/customer/{id}")
    public ResponseEntity<Resource<Response>> updatingCustomerById(@PathVariable long id, @RequestBody Resource<Customer> request) {
        Customer customer = request.getAttribute();
        Customer customer1 = service.updateCustomer(customer, id);
        Response response = new Response(id, "update successfully");
        Resource<Response> resource = new Resource<>(customer1.getId(), OBJECT_TYPE, response);
        return new ResponseEntity<>(resource, HttpStatus.OK);
    }
}
