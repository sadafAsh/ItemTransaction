package com.soj.item.transaction.controller;

import com.soj.item.transaction.model.Bank;
import com.soj.item.transaction.model.SubCategory;
import com.soj.item.transaction.service.SubCategoryService;
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

import static com.soj.item.transaction.model.SubCategory.OBJECT_TYPE;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class SubCategoryControllerTest {

    @Autowired
    private SubCategoryController controller;

    @MockBean
    private SubCategoryService service;

    @Test
    void findTheListOfSubCategory() {
        List<SubCategory> subCategories = new ArrayList<>();
        SubCategory subCategory = new SubCategory();
        subCategory.setId(1l);
        subCategories.add(subCategory);
        when(service.getAllSubCategory()).thenReturn(subCategories);
        ResponseEntity<List<Resource<SubCategory>>> resource = controller.findAllSubCategory();
        Assertions.assertEquals(1, subCategories.size());
    }

    @Test
    void testToFindById() {
        SubCategory subCategory = new SubCategory();
        subCategory.setId(1l);
        when(service.getSubCategory(1l)).thenReturn(subCategory);
        ResponseEntity<Resource<SubCategory>> resources = controller.findSubCategoryById(1l);
        Assertions.assertEquals(1l, subCategory.getId());
    }

    @Test
    void testToCreateNewSubCategory() {
        SubCategory subCategory = new SubCategory();
        subCategory.setId(1l);
        Resource<SubCategory> request = new Resource<>(1l, OBJECT_TYPE, subCategory);
        when(service.createNewSubCategory(any())).thenReturn(subCategory);
        ResponseEntity<Resource<Response>> resource = controller.addNewCategory(request);
        Assertions.assertEquals(1l, request.getId());
    }

    @Test
    void testToDeleteSubCategoryById() {
        SubCategory subCategory = new SubCategory();
        subCategory.setId(1l);
        doNothing().when(service).deleteSubCategory(1l);
        ResponseEntity<Resource<Response>> resources = controller.deleteSubCategoryById(1l);
        Assertions.assertEquals(1l, subCategory.getId());
    }

    @Test
    void updateSubCategoryById() {
        SubCategory subCategory = new SubCategory();
        subCategory.setId(1l);
        Resource<SubCategory> request = new Resource<>(1l, OBJECT_TYPE, subCategory);
        when(service.updateSubCategory(anyLong(), any())).thenReturn(subCategory);
        ResponseEntity<Resource<Response>> resources = controller.updatingExistingSubCategoryById(1l, request);
        Assertions.assertEquals(1l, request.getId());

    }
}
