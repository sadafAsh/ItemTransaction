package com.soj.item.transaction.service.impl;

import com.soj.item.transaction.model.MenuItem;
import com.soj.item.transaction.repository.MenuItemRepository;
import com.soj.item.transaction.service.MenuItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MenuItemServiceImpl implements MenuItemService {

    @Autowired
    private MenuItemRepository repository;

    public List<MenuItem> findAll() {
        return repository.findAll();
    }

    public MenuItem getMenuItem(long id) {

        Optional<MenuItem> optional = repository.findById(id);
        if (optional.isPresent()) {
            return optional.get();
        } else {
            throw new IllegalArgumentException("Id not present");
        }
    }

    public MenuItem createMenuItem(MenuItem menuItem) {
        return repository.saveAndFlush(menuItem);

    }

    public void deleteMenuItem(long id) {
        try {
            repository.deleteById(id);

        } catch (Exception e) {
            throw new IllegalArgumentException("Id not found");
        }
    }


    public MenuItem updateMenuItem(long id, MenuItem menuItem) {
        Optional<MenuItem> optional = repository.findById(id);
        if (optional.isPresent()) {
            return repository.saveAndFlush(menuItem);

        }
        throw new IllegalArgumentException("Id not found");
    }
}
