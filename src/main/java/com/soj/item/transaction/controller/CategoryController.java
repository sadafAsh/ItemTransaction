package com.soj.item.transaction.controller;

import com.soj.item.transaction.model.Category;
import com.soj.item.transaction.service.CategoryService;
import com.soj.item.transaction.util.Resource;
import com.soj.item.transaction.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static com.soj.item.transaction.model.Category.OBJECT_TYPE;

@RestController
@RequestMapping("/api/v1")
public class CategoryController {

    @Autowired
    private CategoryService service;

    @GetMapping("/category")
    public ResponseEntity<List<Resource<Category>>> findAllCategory() {
        List<Resource<Category>> resources = new ArrayList<>();
        List<Category> subCategories = service.getAllCategory();
        if (resources.isEmpty()) {
            subCategories.forEach(x -> {
                Resource<Category> resource = new Resource<>(x.getId(), OBJECT_TYPE, x);
                resources.add(resource);
            });
        }
        return new ResponseEntity<>(resources, HttpStatus.OK);
    }

    @GetMapping("/category/{id}")
    public ResponseEntity<Resource<Category>> findCategoryById(@PathVariable long id) {
        Category category = service.getCategory(id);
        Resource<Category> resource = new Resource<>(id, OBJECT_TYPE, category);
        return new ResponseEntity<>(resource, HttpStatus.OK);
    }

    @PostMapping("/category")
    public ResponseEntity<Resource<Response>> addNewCategory(@RequestBody Resource<Category> request) {
        Category category = request.getAttribute();
        Category category1 = service.createNewCategory(category);
        Response response = new Response(category1.getId(), "create successfully");
        Resource<Response> resource = new Resource<>(category1.getId(), OBJECT_TYPE, response);
        return new ResponseEntity<>(resource, HttpStatus.OK);
    }


    @DeleteMapping("/category/{id}")
    public ResponseEntity<Resource<Response>> deleteCategoryById(@PathVariable long id) {
        service.deleteCategory(id);
        Response response = new Response(id, "delete successfully");
        Resource<Response> resource = new Resource<>(id, OBJECT_TYPE, response);
        return new ResponseEntity<>(resource, HttpStatus.OK);
    }


    @PutMapping("/category/{id}")
    public ResponseEntity<Resource<Response>> updateCategoryById(@PathVariable long id, @RequestBody Resource<Category> request) {
        Category category = request.getAttribute();
        Category category1 = service.updateCategory(id, category);

        Response response = new Response(category1.getId(), "update successfully");
        Resource<Response> resource = new Resource<>(category1.getId(), OBJECT_TYPE, response);
        return new ResponseEntity<>(resource, HttpStatus.OK);
    }
}





