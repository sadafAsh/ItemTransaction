package com.soj.item.transaction.controller;

import com.soj.item.transaction.model.Product;
import com.soj.item.transaction.service.ProductService;
import com.soj.item.transaction.util.Resource;
import com.soj.item.transaction.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static com.soj.item.transaction.model.Product.OBJECT_TYPE;

@RestController
@RequestMapping("/api/v1/product")
public class ProductController {
    @Autowired
    private ProductService service;

    @GetMapping
    public ResponseEntity<List<Resource<Product>>> getAllProduct() {
        List<Resource<Product>> resources = new ArrayList<>();
        List<Product> products = service.findAll();
        if (resources.isEmpty()) {
            products.forEach(x -> {
                Resource<Product> resource = new Resource<>(x.getId(), OBJECT_TYPE, x);
                resources.add(resource);
            });

        }
        return new ResponseEntity<>(resources, HttpStatus.OK);


    }

    @GetMapping("{id}")
    public ResponseEntity<Resource<Product>> getProductById(@PathVariable long id) {
        Product product = service.getProduct(id);
        Resource<Product> resource = new Resource<>(id, OBJECT_TYPE, product);
        return new ResponseEntity<>(resource, HttpStatus.OK);
    }


    @PostMapping
    public ResponseEntity<Resource<Response>> addNewProduct(@RequestBody Resource<Product> request) {
        Product product = request.getAttribute();
        Product product1 = service.createProduct(product);
        Response response = new Response(product1.getId(), "create successfully");
        Resource<Response> resource = new Resource<>(product1.getId(), OBJECT_TYPE, response);
        return new ResponseEntity<>(resource, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Resource<Response>> deleteProductById(@PathVariable long id) {
        service.deleteProduct(id);

        Response response = new Response(id, "delete successfully");
        Resource<Response> resource = new Resource<>(id, OBJECT_TYPE, response);
        return new ResponseEntity<>(resource, HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<Resource<Response>> updateProductById(@PathVariable long id, @RequestBody Resource<Product> request) {
        Product product = request.getAttribute();
        Product product1 = service.updateProduct(id, product);
        Response response = new Response(id, "update successfully");
        Resource<Response> resource = new Resource<>(product1.getId(), OBJECT_TYPE, response);
        return new ResponseEntity<>(resource, HttpStatus.OK);
    }
}
