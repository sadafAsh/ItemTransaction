package com.soj.item.transaction.service.impl;

import com.soj.item.transaction.model.Bank;
import com.soj.item.transaction.model.Category;
import com.soj.item.transaction.model.SubCategory;
import com.soj.item.transaction.repository.CategoryRepository;
import com.soj.item.transaction.service.CategoryService;
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

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class CategoryServiceImplTest {

    @Autowired
    private CategoryService service;

    @MockBean
    private CategoryRepository repository;

    @Test
    void testToGetAllCategory() {
        List<Category> categoryList = new ArrayList<>();
        Category category = new Category();
        category.setId(1l);
        List<SubCategory> subCategories = new ArrayList<>();
        SubCategory subCategory = new SubCategory();
        subCategory.setName("chicken gravy");
        subCategories.add(subCategory);
        category.setSubCategories(subCategories);
        categoryList.add(category);
        when(repository.findAll()).thenReturn(categoryList);
        List<Category> category1 = service.getAllCategory();
        Assertions.assertEquals(1, category1.size());
    }

    @Test
    void testToGetCategoryById() {
        Category category = new Category();
        category.setId(1l);
        List<SubCategory> subCategories = new ArrayList<>();
        SubCategory subCategory = new SubCategory();
        subCategory.setName("chicken gravy");
        subCategories.add(subCategory);
        category.setSubCategories(subCategories);
        when(repository.findById(1l)).thenReturn(java.util.Optional.of(category));
        Category category1 = service.getCategory(1l);
        Assertions.assertEquals(1l, category1.getId());
        Assertions.assertEquals(1, category1.getSubCategories().size());
    }

    @Test
    void testToGetExceptionCategoryById() {
        Exception exception = assertThrows(IllegalArgumentException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                Category category = new Category();
                category.setId(1l);
                when(repository.findById(2l)).thenReturn(java.util.Optional.of(category));
                Category category1 = service.getCategory(1l);
            }
        });
        Assertions.assertEquals("Id not present", exception.getMessage());
    }

    @Test
    void testToAddNewCategory() {
        Category category = new Category();
        category.setId(1l);
        when(repository.saveAndFlush(any())).thenReturn(category);
        Category category1 = service.createNewCategory(category);
        Assertions.assertEquals(1l, category1.getId());
    }

    @Test
    void testToDeleteCategoryById() {
        Category category = new Category();
        category.setId(1l);
        doNothing().when(repository).deleteById(1l);
        service.deleteCategory(1l);
        Assertions.assertEquals(1l, category.getId());
    }

    @Test
    void testUpdateCategoryById() {
        Category category = new Category();
        category.setId(1l);
        when(repository.findById(1l)).thenReturn(java.util.Optional.of(category));
        when(repository.saveAndFlush(any())).thenReturn(category);
        Category category1 = service.updateCategory(1l, category);
        Assertions.assertEquals(1l, category1.getId());
    }

    @Test
    void testToThrowExceptionInUpdateCategoryById() {
        Assertions.assertThrows(IllegalArgumentException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                Category category = new Category();
                category.setId(1l);
                when(repository.findById(2l)).thenReturn(java.util.Optional.of(category));
                when(repository.saveAndFlush(any())).thenReturn(category);
                Category category1 = service.updateCategory(1l, category);

            }
        });
    }

    @Test
    void testToDeleteExceptionCategoryById() {
        assertThrows(IllegalArgumentException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                Category category = new Category();
                category.setId(1l);
                doThrow(IllegalArgumentException.class).when(repository).deleteById(1l);
                service.deleteCategory(1l);
            }
        });
    }


}
