package com.soj.item.transaction.service.impl;

import com.soj.item.transaction.model.Product;
import com.soj.item.transaction.repository.ProductRepository;
import com.soj.item.transaction.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository repository;

    public List<Product> findAll() {

        return repository.findAll();
    }

    public Product getProduct(long id) {
        Optional<Product> optional = repository.findById(id);
        if (optional.isPresent()) {
            return optional.get();

        } else {
            throw new IllegalArgumentException("id not found");
        }
    }

    public Product createProduct(Product product) {
        return repository.saveAndFlush(product);

    }

    public void deleteProduct(long id) {

        try {


            repository.deleteById(id);
        } catch (Exception e) {
            throw new IllegalArgumentException("Id not found");
        }

    }

    public Product updateProduct(long id, Product product) {
        Optional<Product> optional = repository.findById(id);
        if (optional.isPresent()) {


            return repository.saveAndFlush(product);

        } else {
            throw new IllegalArgumentException("Id  not found");
        }
    }

}

