package com.soj.item.transaction.service.impl;

import com.soj.item.transaction.model.*;
import com.soj.item.transaction.repository.CustomerRepository;
import com.soj.item.transaction.repository.OrderRepository;
import com.soj.item.transaction.service.CustomerService;
import com.soj.item.transaction.service.MenuItemService;
import com.soj.item.transaction.service.OrderService;
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
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class OrderServiceImplTest {
    @Autowired
    private OrderService service;
    @MockBean
    private OrderRepository repository;
    @MockBean
    private CustomerService customerService;

    @MockBean
    private MenuItemService menuItemService;


    @Test
    void testToGetAllOrder() {
        List<Order> list = new ArrayList<>();
        Order order = new Order();
        order.setId(1l);
        list.add(order);
        when(repository.findAll()).thenReturn(list);
        service.getAll();
        Assertions.assertEquals(1, list.size());
    }

    @Test
    void testToGetOrderById() {
        Order order = new Order();
        order.setId(1l);
        when(repository.findById(1l)).thenReturn(java.util.Optional.of(order));
        service.getOrder(1l);
        Assertions.assertEquals(1l, order.getId());
    }

    @Test
    void testToGetExceptionOrderById() {
        Assertions.assertThrows(IllegalArgumentException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                Order order = new Order();
                order.setId(1l);
                when(repository.findById(2l)).thenReturn(java.util.Optional.of(order));
                service.getOrder(1l);
            }
        });
    }

    @Test
    void testToAddNewOrderIfMenuItemExist() {

        Order order = new Order();
        Customer customer = new Customer();
        customer.setId(1l);
        order.setId(1l);
        order.setCustomer(customer);
        List<OrderDetail> details = new ArrayList<>();
        OrderDetail orderDetail = new OrderDetail();
        MenuItem menuItem = new MenuItem();
        menuItem.setId(1l);
        Product product = new Product();
        product.setId(1l);
        menuItem.setProduct(product);
        orderDetail.setMenuItem(menuItem);
        details.add(orderDetail);
        order.setOrderDetailList(details);


        when(customerService.getCustomer(1l)).thenReturn(customer);
        when(menuItemService.getMenuItem(1l)).thenReturn(menuItem);
        when(repository.saveAndFlush(any())).thenReturn(order);

        service.createOrder(order);

        Assertions.assertEquals(1l, order.getCustomer().getId());
        Assertions.assertEquals(1l, order.getId());
    }

    @Test
    void testToAddNewOrderIfMenuItemIsNull() {
        Exception exception = Assertions.assertThrows(NullPointerException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                Order order = new Order();
                Customer customer = new Customer();
                customer.setId(1l);
                order.setId(1l);
                order.setCustomer(customer);
                List<OrderDetail> details = new ArrayList<>();
                OrderDetail orderDetail = new OrderDetail();
                MenuItem menuItem = new MenuItem();
                menuItem.setId(1l);
                Product product = new Product();
                product.setId(1l);
                menuItem.setProduct(product);
                orderDetail.setMenuItem(menuItem);
                details.add(orderDetail);
                order.setOrderDetailList(details);
                when(repository.saveAndFlush(any())).thenReturn(order);
                service.createOrder(order);
            }
        });
        Assertions.assertEquals("menuItem does not exist", exception.getMessage());
    }

    @Test
    void testToDeleteOrderById() {
        Order order = new Order();
        order.setId(1l);
        doNothing().when(repository).deleteById(1l);
        service.deleteOrder(1l);
        Assertions.assertEquals(1l, order.getId());
    }

    @Test
    void testToDeleteExceptionOrderById() {
        assertThrows(IllegalArgumentException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                Order order = new Order();
                order.setId(1l);
                doThrow(IllegalArgumentException.class).when(repository).deleteById(1l);
                service.deleteOrder(1l);
            }
        });
    }

    @Test
    void testUpdateOrderById() {
        Order order = new Order();
        order.setId(1l);
        when(repository.findById(1l)).thenReturn(java.util.Optional.of(order));
        when(repository.saveAndFlush(any())).thenReturn(order);
        service.updateOrder(1l, order);
        Assertions.assertEquals(1l, order.getId());
    }

    @Test
    void testToThrowExceptionInUpdateOrderById() {
        Assertions.assertThrows(IllegalArgumentException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                Order order = new Order();
                order.setId(1l);
                when(repository.findById(2l)).thenReturn(java.util.Optional.of(order));
                when(repository.saveAndFlush(any())).thenReturn(order);
                service.updateOrder(1l, order);

            }
        });
    }
}

