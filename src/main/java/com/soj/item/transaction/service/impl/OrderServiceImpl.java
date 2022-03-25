package com.soj.item.transaction.service.impl;

import com.soj.item.transaction.model.*;
import com.soj.item.transaction.repository.OrderRepository;
import com.soj.item.transaction.service.CustomerService;
import com.soj.item.transaction.service.MenuItemService;
import com.soj.item.transaction.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository repository;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private MenuItemService menuItemService;


    public List<Order> getAll() {

        return repository.findAll();
    }

    @Override
    public Order getOrder(Long id) {
        Optional<Order> order = repository.findById(id);
        if (order.isPresent()) {
            return order.get();
        } else {
            throw new IllegalArgumentException("Id not present");
        }
    }

    public Order createOrder(Order order) {
        Order order1 = new Order();
        Customer customer = customerService.getCustomer(order.getCustomer().getId());
        order1.setCustomer(customer);
        List<OrderDetail> list = new ArrayList<>();
        order.getOrderDetailList().forEach(x -> {
            Long menuItemId = x.getMenuItem().getId();
            MenuItem menuItem = menuItemService.getMenuItem(menuItemId);
            if (menuItem == null) {
                throw new NullPointerException("menuItem does not exist");
            }
            OrderDetail detail = new OrderDetail();
            detail.setMenuItem(menuItem);
            menuItem.setCategory(detail.getMenuItem().getCategory());
            menuItem.setProduct(detail.getMenuItem().getProduct());
            long totalPrice = menuItem.getProduct().getPrice() * (long) x.getQty();
            menuItem.getProduct().setPrice(detail.getMenuItem().getProduct().getPrice());
            detail.setQty(x.getQty());
            detail.setTotalPrice(totalPrice);
            list.add(detail);
        });
        order1.setOrderDetailList(list);
        return repository.saveAndFlush(order1);

    }

    public void deleteOrder(long id) {
        try {
            repository.deleteById(id);

        } catch (Exception e) {
            throw new IllegalArgumentException("Id not found");
        }
    }

    public Order updateOrder(long id, Order order) {
        Optional<Order> optional = repository.findById(id);
        if (optional.isPresent()) {
            return repository.saveAndFlush(order);

        }
        throw new IllegalArgumentException("Id not found");
    }
}
