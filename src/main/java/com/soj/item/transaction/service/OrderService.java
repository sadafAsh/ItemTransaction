package com.soj.item.transaction.service;

import com.soj.item.transaction.model.Order;

import java.util.List;

public interface OrderService {

    public List<Order> getAll();

    public Order getOrder(Long id);

    public Order createOrder(Order order);

    public void deleteOrder(long id);

    public Order updateOrder(long id, Order order);
}
