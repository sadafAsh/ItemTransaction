package com.soj.item.transaction.controller;

import com.soj.item.transaction.model.Customer;
import com.soj.item.transaction.model.Order;
import com.soj.item.transaction.service.CustomerService;
import com.soj.item.transaction.service.OrderService;
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

import static com.soj.item.transaction.model.Order.OBJECT_TYPE;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class OrderControllerTest {

    @Autowired
    private OrderController controller;
    @MockBean
    private OrderService service;

    @Test
    void findTheListOfOrder() {
        List<Order> orders = new ArrayList<>();
        Order order = new Order();
        order.setId(1l);
        orders.add(order);
        when(service.getAll()).thenReturn(orders);
        ResponseEntity<List<Resource<Order>>> resource = controller.findAllOrder();
        Assertions.assertEquals(1, orders.size());
    }

    @Test
    void findById() {
        Order order = new Order();
        order.setId(1l);
        when(service.getOrder(1l)).thenReturn(order);
        ResponseEntity<Resource<Order>> resources = controller.getOrderById(1l);
        Assertions.assertEquals(1l, order.getId());
    }

    @Test
    void testToCreateNewOrder() {
        Order order = new Order();
        order.setId(1l);
        Resource<Order> request = new Resource<>(1l, OBJECT_TYPE, order);
        when(service.createOrder(any())).thenReturn(order);
        ResponseEntity<Resource<Response>> resource = controller.addNewOrder(request);
        Assertions.assertEquals(1l, request.getId());
    }

    @Test
    void testToDeleteOrderById() {
        Order order = new Order();
        order.setId(1l);
        doNothing().when(service).deleteOrder(1l);
        ResponseEntity<Resource<Response>> resources = controller.deleteOrderById(1l);
        Assertions.assertEquals(1l, order.getId());
    }

    @Test
    void updateOrderById() {
        Order order = new Order();
        order.setId(1l);
        Resource<Order> request = new Resource<>(1l, OBJECT_TYPE, order);
        when(service.updateOrder(anyLong(), any())).thenReturn(order);
        ResponseEntity<Resource<Response>> resources = controller.updateOrderById(1l, request);
        Assertions.assertEquals(1l, request.getId());

    }


}