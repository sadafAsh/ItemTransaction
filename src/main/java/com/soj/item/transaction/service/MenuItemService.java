package com.soj.item.transaction.service;

import com.soj.item.transaction.model.MenuItem;

import java.util.List;

public interface MenuItemService {
    public List<MenuItem> findAll();

    public MenuItem getMenuItem(long id);

    public MenuItem createMenuItem(MenuItem menuItem);

    public void deleteMenuItem(long id);

    public MenuItem updateMenuItem(long id, MenuItem menuItem);

}
