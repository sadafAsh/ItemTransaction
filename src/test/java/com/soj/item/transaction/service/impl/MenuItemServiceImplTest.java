package com.soj.item.transaction.service.impl;

import com.soj.item.transaction.model.Bank;
import com.soj.item.transaction.model.MenuItem;
import com.soj.item.transaction.repository.MenuItemRepository;
import com.soj.item.transaction.service.MenuItemService;
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
class MenuItemServiceImplTest {
    @Autowired
    private MenuItemService service;

    @MockBean
    private MenuItemRepository repository;

    @Test
    void testToGetAllMenuItem() {
        List<MenuItem> list = new ArrayList<>();
        MenuItem menuItem = new MenuItem();
        menuItem.setId(1l);
        list.add(menuItem);
        when(repository.findAll()).thenReturn(list);
        List<MenuItem> menuItems = service.findAll();
        Assertions.assertEquals(1, list.size());
    }

    @Test
    void testToGetMenuItemById() {
        MenuItem menuItem = new MenuItem();
        menuItem.setId(1l);
        when(repository.findById(1l)).thenReturn(java.util.Optional.of(menuItem));
        service.getMenuItem(1l);

        Assertions.assertEquals(1l, menuItem.getId());
    }

    @Test
    void testToGetExceptionMenuItemById() {
        Assertions.assertThrows(IllegalArgumentException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                MenuItem menuItem = new MenuItem();
                menuItem.setId(1l);
                when(repository.findById(2l)).thenReturn(java.util.Optional.of(menuItem));
                service.getMenuItem(1l);
            }
        });
    }

    @Test
    void testToAddNewMenuItem() {
        MenuItem menuItem = new MenuItem();
        menuItem.setId(1l);
        when(repository.saveAndFlush(any())).thenReturn(menuItem);
        service.createMenuItem(menuItem);
        Assertions.assertEquals(1l, menuItem.getId());
    }

    @Test
    void testToDeleteMenuItemById() {
        MenuItem menuItem = new MenuItem();
        menuItem.setId(1l);
        doNothing().when(repository).deleteById(1l);
        service.deleteMenuItem(1l);
        Assertions.assertEquals(1l, menuItem.getId());
    }

    @Test
    void testToDeleteExceptionMenuItemById() {
        assertThrows(IllegalArgumentException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                MenuItem menuItem = new MenuItem();
                menuItem.setId(1l);
                doThrow(IllegalArgumentException.class).when(repository).deleteById(1l);
                service.deleteMenuItem(1l);
            }
        });
    }

    @Test
    void testUpdateMenuItemById() {
        MenuItem menuItem = new MenuItem();
        menuItem.setId(1l);
        when(repository.findById(1l)).thenReturn(java.util.Optional.of(menuItem));
        when(repository.saveAndFlush(any())).thenReturn(menuItem);
        service.updateMenuItem(1l, menuItem);
        Assertions.assertEquals(1l, menuItem.getId());
    }

    @Test
    void testToThrowExceptionInUpdateMenuItemById() {
        Assertions.assertThrows(IllegalArgumentException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                MenuItem menuItem = new MenuItem();
                menuItem.setId(1l);
                when(repository.findById(2l)).thenReturn(java.util.Optional.of(menuItem));
                when(repository.saveAndFlush(any())).thenReturn(menuItem);
                service.updateMenuItem(1l, menuItem);

            }
        });
    }
}