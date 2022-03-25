package com.soj.item.transaction.service.impl;

import com.soj.item.transaction.model.Bank;
import com.soj.item.transaction.model.SubCategory;
import com.soj.item.transaction.repository.BankRepository;
import com.soj.item.transaction.repository.SubCategoryRepository;
import com.soj.item.transaction.service.BankService;
import com.soj.item.transaction.service.SubCategoryService;
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
class SubCategoryServiceImplTest {

    @Autowired
    private SubCategoryService service;
    @MockBean
    private SubCategoryRepository repository;

    @Test
    void testToGetAllSubCategory() {
        List<SubCategory> list = new ArrayList<>();
        SubCategory subCategory = new SubCategory();
        subCategory.setId(1l);
        list.add(subCategory);
        when(repository.findAll()).thenReturn(list);
        List<SubCategory> subCategory1 = service.getAllSubCategory();
        Assertions.assertEquals(1, list.size());
    }

    @Test
    void testToGetSubCategoryById() {
        SubCategory subCategory = new SubCategory();
        subCategory.setId(1l);
        when(repository.findById(1l)).thenReturn(java.util.Optional.of(subCategory));
        SubCategory subCategory1 = service.getSubCategory(1l);
        Assertions.assertEquals(1l, subCategory1.getId());
    }

    @Test
    void testToGetExceptionSubCategoryById() {
        Assertions.assertThrows(IllegalArgumentException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                SubCategory subCategory = new SubCategory();
                subCategory.setId(1l);
                when(repository.findById(2l)).thenReturn(java.util.Optional.of(subCategory));

                SubCategory subCategory1 = service.getSubCategory(1l);
            }
        });
    }

    @Test
    void testToAddNewSubCategory() {
        SubCategory subCategory = new SubCategory();
        subCategory.setId(1l);
        when(repository.saveAndFlush(any())).thenReturn(subCategory);
        SubCategory subCategory1 = service.createNewSubCategory(subCategory);
        Assertions.assertEquals(1l, subCategory.getId());
    }

    @Test
    void testToDeleteSubCategoryById() {
        SubCategory subCategory = new SubCategory();
        subCategory.setId(1l);
        doNothing().when(repository).deleteById(1l);
        service.deleteSubCategory(1l);
        Assertions.assertEquals(1l, subCategory.getId());
    }

    @Test
    void testUpdateSubCategoryById() {
        SubCategory subCategory = new SubCategory();
        subCategory.setId(1l);
        when(repository.findById(1l)).thenReturn(java.util.Optional.of(subCategory));
        when(repository.saveAndFlush(any())).thenReturn(subCategory);
        SubCategory subCategory1 = service.updateSubCategory(1l, subCategory);
        Assertions.assertEquals(1l, subCategory1.getId());
    }

    @Test
    void testToThrowExceptionInUpdateSubCategoryById() {
        Assertions.assertThrows(IllegalArgumentException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                SubCategory subCategory = new SubCategory();
                subCategory.setId(1l);
                when(repository.findById(2l)).thenReturn(java.util.Optional.of(subCategory));
                when(repository.saveAndFlush(any())).thenReturn(subCategory);
                SubCategory subCategory1 = service.updateSubCategory(1l, subCategory);

            }
        });
    }


    @Test
    void testToDeleteExceptionSubCategoryById() {
        assertThrows(IllegalArgumentException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                SubCategory subCategory = new SubCategory();
                subCategory.setId(1l);
                doThrow(IllegalArgumentException.class).when(repository).deleteById(1l);
                service.deleteSubCategory(1l);
            }
        });
    }

}


