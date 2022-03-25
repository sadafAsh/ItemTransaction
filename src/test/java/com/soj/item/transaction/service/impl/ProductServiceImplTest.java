package com.soj.item.transaction.service.impl;

import com.soj.item.transaction.model.Bank;
import com.soj.item.transaction.model.Category;
import com.soj.item.transaction.model.Product;
import com.soj.item.transaction.model.SubCategory;
import com.soj.item.transaction.repository.CategoryRepository;
import com.soj.item.transaction.repository.ProductRepository;
import com.soj.item.transaction.service.CategoryService;
import com.soj.item.transaction.service.ProductService;
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
class ProductServiceImplTest {


    @Autowired
    private ProductService service;

    @MockBean
    private ProductRepository repository;

    @Test
    void testToGetAllProduct() {
        List<Product> products = new ArrayList<>();
        Product product = new Product();
        product.setId(1l);
        products.add(product);
        when(repository.findAll()).thenReturn(products);
        List<Product> products1 = service.findAll();
        Assertions.assertEquals(1, products1.size());
    }

    @Test
    void testToGetProductById() {
        Product product = new Product();
        product.setId(1l);
        when(repository.findById(1l)).thenReturn(java.util.Optional.of(product));
        Product product1 = service.getProduct(1l);
        Assertions.assertEquals(1l, product1.getId());

    }

    @Test
    void testToGetExceptionProductById() {
        Exception exception = assertThrows(IllegalArgumentException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                Product product = new Product();
                product.setId(1l);
                when(repository.findById(2l)).thenReturn(java.util.Optional.of(product));
                Product product1 = service.getProduct(1l);
            }
        });
        Assertions.assertEquals("id not found", exception.getMessage());
    }


    @Test
    void testToDeleteExceptionProductById() {
        assertThrows(IllegalArgumentException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                Product product = new Product();
                product.setId(1l);
                doThrow(IllegalArgumentException.class).when(repository).deleteById(1l);
                service.deleteProduct(1l);
            }
        });
    }


    @Test
    void testToAddNewProduct() {
        Product product = new Product();
        product.setId(1l);
        when(repository.saveAndFlush(any())).thenReturn(product);
        Product product1 = service.createProduct(product);
        Assertions.assertEquals(1l, product1.getId());
    }

    @Test
    void testToDeleteProductById() {
        Product product = new Product();
        product.setId(1l);
        doNothing().when(repository).deleteById(1l);
        service.deleteProduct(1l);
        Assertions.assertEquals(1l, product.getId());
    }

    @Test
    void testUpdateProductById() {
        Product product = new Product();
        product.setId(1l);
        when(repository.findById(1l)).thenReturn(java.util.Optional.of(product));
        when(repository.saveAndFlush(any())).thenReturn(product);
        Product product1 = service.updateProduct(1l, product);
        Assertions.assertEquals(1l, product1.getId());
    }

    @Test
    void testToThrowExceptionInUpdateProductById() {
        Assertions.assertThrows(IllegalArgumentException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                Product product = new Product();
                product.setId(1l);
                when(repository.findById(2l)).thenReturn(java.util.Optional.of(product));
                when(repository.saveAndFlush(any())).thenReturn(product);
                Product product1 = service.updateProduct(1l, product);

            }
        });
    }

}