package com.soj.item.transaction.service;

import com.soj.item.transaction.model.Product;

import java.util.List;

public interface ProductService {

    public List<Product> findAll();

    public Product getProduct(long id);

    public Product createProduct(Product product);

    public void deleteProduct(long id);

    public Product updateProduct(long id, Product product);
}
