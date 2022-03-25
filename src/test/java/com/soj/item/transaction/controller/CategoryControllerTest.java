package com.soj.item.transaction.controller;

import com.soj.item.transaction.model.Bank;
import com.soj.item.transaction.model.Category;
import com.soj.item.transaction.service.CategoryService;
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

import static com.soj.item.transaction.model.Bank.OBJECT_TYPE;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class CategoryControllerTest {

    @Autowired
    private CategoryController controller;
    @MockBean
    private CategoryService service;

    @Test
    void findTheListOfCategory() {
        List<Category> categoryList = new ArrayList<>();
        Category category = new Category();
        category.setId(1l);
        categoryList.add(category);
        when(service.getAllCategory()).thenReturn(categoryList);
        ResponseEntity<List<Resource<Category>>> resource = controller.findAllCategory();
        Assertions.assertEquals(1, categoryList.size());
    }

    @Test
    void findById() {
        Category category = new Category();
        category.setId(1l);
        when(service.getCategory(1l)).thenReturn(category);
        ResponseEntity<Resource<Category>> resources = controller.findCategoryById(1l);
        Assertions.assertEquals(1l, category.getId());
    }

    @Test
    void testToCreateNewCategory() {
        Category category = new Category();
        category.setId(1l);
        Resource<Category> request = new Resource<>(1l, OBJECT_TYPE, category);
        when(service.createNewCategory(any())).thenReturn(category);
        ResponseEntity<Resource<Response>> resource = controller.addNewCategory(request);
        Assertions.assertEquals(1l, request.getId());
    }

    @Test
    void testToDeleteCategoryById() {
        Category category = new Category();
        category.setId(1l);
        doNothing().when(service).deleteCategory(1l);
        ResponseEntity<Resource<Response>> resources = controller.deleteCategoryById(1l);
        Assertions.assertEquals(1l, category.getId());
    }

    @Test
    void updateCategoryById() {
        Category category = new Category();
        category.setId(1l);
        Resource<Category> request = new Resource<>(1l, OBJECT_TYPE, category);
        when(service.updateCategory(anyLong(), any())).thenReturn(category);
        ResponseEntity<Resource<Response>> resources = controller.updateCategoryById(1l, request);
        Assertions.assertEquals(1l, request.getId());

    }
}

