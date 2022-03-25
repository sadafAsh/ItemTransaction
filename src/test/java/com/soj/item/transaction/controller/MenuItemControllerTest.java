package com.soj.item.transaction.controller;

import com.soj.item.transaction.model.Bank;
import com.soj.item.transaction.model.MenuItem;
import com.soj.item.transaction.service.MenuItemService;
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

import static com.soj.item.transaction.model.MenuItem.OBJECT_TYPE;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class MenuItemControllerTest {

    @Autowired
    private MenuItemController controller;

    @MockBean
    private MenuItemService service;

    @Test
    void findTheListOfMenuItem() {
        List<MenuItem> menuItems = new ArrayList<>();
        MenuItem menuItem = new MenuItem();
        menuItem.setId(1l);
        menuItems.add(menuItem);
        when(service.findAll()).thenReturn(menuItems);
        ResponseEntity<List<Resource<MenuItem>>> resource = controller.getAllMenuItem();
        Assertions.assertEquals(1, menuItems.size());
    }

    @Test
    void findById() {
        MenuItem menuItem = new MenuItem();
        menuItem.setId(1l);
        when(service.getMenuItem(1l)).thenReturn(menuItem);
        ResponseEntity<Resource<MenuItem>> resources = controller.getMenuItemById(1l);
        Assertions.assertEquals(1l, menuItem.getId());
    }

    @Test
    void testToCreateNewMenuItem() {
        MenuItem menuItem = new MenuItem();
        menuItem.setId(1l);
        Resource<MenuItem> request = new Resource<>(1l, OBJECT_TYPE, menuItem);
        when(service.createMenuItem(any())).thenReturn(menuItem);
        ResponseEntity<Resource<Response>> resource = controller.addNewMenuItem(request);
        Assertions.assertEquals(1l, request.getId());
    }

    @Test
    void testToDeleteMenuItemById() {
        MenuItem menuItem = new MenuItem();
        menuItem.setId(1l);
        doNothing().when(service).deleteMenuItem(1l);
        ResponseEntity<Resource<Response>> resources = controller.deleteMenuItemById(1l);
        Assertions.assertEquals(1l, menuItem.getId());
    }

    @Test
    void updateMenuItemById() {
        MenuItem menuItem = new MenuItem();
        menuItem.setId(1l);
        Resource<MenuItem> request = new Resource<>(1l, OBJECT_TYPE, menuItem);
        when(service.updateMenuItem(anyLong(), any())).thenReturn(menuItem);
        ResponseEntity<Resource<Response>> resources = controller.updateMenuItemById(1l, request);
        Assertions.assertEquals(1l, request.getId());

    }
}

