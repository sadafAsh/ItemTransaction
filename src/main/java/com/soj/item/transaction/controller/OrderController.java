package com.soj.item.transaction.controller;

import com.soj.item.transaction.model.Order;
import com.soj.item.transaction.model.Product;
import com.soj.item.transaction.service.OrderService;
import com.soj.item.transaction.util.Resource;
import com.soj.item.transaction.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static com.soj.item.transaction.model.Order.OBJECT_TYPE;

@RestController
@RequestMapping("/api/v1")
public class OrderController {

    @Autowired
    private OrderService service;

    @GetMapping("/order")
    public ResponseEntity<List<Resource<Order>>> findAllOrder() {

        List<Resource<Order>> resources = new ArrayList<>();
        if (resources.isEmpty()) {
            List<Order> orders = service.getAll();
            orders.forEach(x -> {
                Resource<Order> resource = new Resource<>(x.getId(), OBJECT_TYPE, x);
                resources.add(resource);
            });
        }
        return new ResponseEntity<>(resources, HttpStatus.OK);
    }

    @GetMapping("/order/{id}")
    public ResponseEntity<Resource<Order>> getOrderById(@PathVariable long id) {
        Order order = service.getOrder(id);
        Resource<Order> resource = new Resource<>(order.getId(), OBJECT_TYPE, order);
        return new ResponseEntity<>(resource, HttpStatus.OK);
    }

    @PostMapping("/order")
    public ResponseEntity<Resource<Response>> addNewOrder(@RequestBody Resource<Order> request) {
        Order order = request.getAttribute();
        Order order1 = service.createOrder(order);
        Response response = new Response(order1.getId(), "create successfully");
        Resource<Response> resource = new Resource<>(order1.getId(), OBJECT_TYPE, response);
        return new ResponseEntity<>(resource, HttpStatus.OK);

    }

    @DeleteMapping("/order/{id}")
    public ResponseEntity<Resource<Response>> deleteOrderById(@PathVariable long id) {
        service.deleteOrder(id);
        Response response = new Response(id, "delete successfully");
        Resource<Response> resource = new Resource<>(id, Product.OBJECT_TYPE, response);
        return new ResponseEntity<>(resource, HttpStatus.OK);
    }

    @PutMapping("/order/{id}")
    public ResponseEntity<Resource<Response>> updateOrderById(@PathVariable long id, @RequestBody Resource<Order> request) {
        Order order = request.getAttribute();
        Order order1 = service.updateOrder(id, order);

        Response response = new Response(order1.getId(), "update successfully");
        Resource<Response> resource = new Resource<>(order1.getId(), Product.OBJECT_TYPE, response);
        return new ResponseEntity<>(resource, HttpStatus.OK);
    }
}
