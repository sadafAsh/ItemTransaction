package com.soj.item.transaction.controller;

import com.soj.item.transaction.model.Product;
import com.soj.item.transaction.model.SubCategory;
import com.soj.item.transaction.service.ProductService;
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

import static com.soj.item.transaction.model.Product.OBJECT_TYPE;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class ProductControllerTest {

    @Autowired
    private ProductController controller;

    @MockBean
    private ProductService service;

    @Test
    void findTheListOfProduct() {
        List<Product> productList = new ArrayList<>();
        Product product = new Product();
        product.setId(1l);
        productList.add(product);
        when(service.findAll()).thenReturn(productList);
        ResponseEntity<List<Resource<Product>>> resource = controller.getAllProduct();
        Assertions.assertEquals(1, productList.size());
    }

    @Test
    void testToFindById() {
        Product product = new Product();
        product.setId(1l);
        when(service.getProduct(1l)).thenReturn(product);
        ResponseEntity<Resource<Product>> resources = controller.getProductById(1l);
        Assertions.assertEquals(1l, product.getId());
    }

    @Test
    void testToCreateNewProduct() {
        Product product = new Product();
        product.setId(1l);
        Resource<Product> request = new Resource<>(1l, OBJECT_TYPE, product);
        when(service.createProduct(any())).thenReturn(product);
        ResponseEntity<Resource<Response>> resource = controller.addNewProduct(request);
        Assertions.assertEquals(1l, request.getId());
    }

    @Test
    void testToDeleteById() {
        Product product = new Product();
        product.setId(1l);
        doNothing().when(service).deleteProduct(1l);
        ResponseEntity<Resource<Response>> resources = controller.deleteProductById(1l);
        Assertions.assertEquals(1l, product.getId());
    }

    @Test
    void updateProductById() {
        Product product = new Product();
        product.setId(1l);
        Resource<Product> request = new Resource<>(1l, OBJECT_TYPE, product);
        when(service.updateProduct(anyLong(), any())).thenReturn(product);
        ResponseEntity<Resource<Response>> resources = controller.updateProductById(1l, request);
        Assertions.assertEquals(1l, request.getId());

    }

}