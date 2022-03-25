package com.soj.item.transaction.controller;

import com.soj.item.transaction.model.SubCategory;
import com.soj.item.transaction.service.SubCategoryService;
import com.soj.item.transaction.util.Resource;
import com.soj.item.transaction.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static com.soj.item.transaction.model.SubCategory.OBJECT_TYPE;

@RestController
@RequestMapping("/api/v1")
public class SubCategoryController {

    @Autowired
    private SubCategoryService service;

    @GetMapping("/subCategory")
    public ResponseEntity<List<Resource<SubCategory>>> findAllSubCategory() {
        List<Resource<SubCategory>> resources = new ArrayList<>();
        List<SubCategory> subCategories = service.getAllSubCategory();
        if (resources.isEmpty()) {
            subCategories.forEach(x -> {
                Resource<SubCategory> resource = new Resource<>(x.getId(), OBJECT_TYPE, x);
                resources.add(resource);
            });
        }
        return new ResponseEntity<>(resources, HttpStatus.OK);

    }

    @GetMapping("/subCategory/{id}")
    public ResponseEntity<Resource<SubCategory>> findSubCategoryById(@PathVariable long id) {
        SubCategory subCategory = service.getSubCategory(id);
        Resource<SubCategory> resource = new Resource<>(id, OBJECT_TYPE, subCategory);
        return new ResponseEntity<>(resource, HttpStatus.OK);
    }

    @PostMapping("/subCategory")
    public ResponseEntity<Resource<Response>> addNewCategory(@RequestBody Resource<SubCategory> request) {
        SubCategory subCategory = request.getAttribute();
        SubCategory subCategory1 = service.createNewSubCategory(subCategory);
        Response response = new Response(subCategory1.getId(), "create successfully");
        Resource<Response> resource = new Resource<>(subCategory1.getId(), OBJECT_TYPE, response);
        return new ResponseEntity<>(resource, HttpStatus.OK);
    }

    @DeleteMapping("/subCategory/{id}")
    public ResponseEntity<Resource<Response>> deleteSubCategoryById(@PathVariable long id) {
        service.deleteSubCategory(id);
        Response response = new Response(id, "delete successfully");
        Resource<Response> resource = new Resource<>(id, OBJECT_TYPE, response);
        return new ResponseEntity<>(resource, HttpStatus.OK);
    }

    @PutMapping("/subCategory/{id}")
    public ResponseEntity<Resource<Response>> updatingExistingSubCategoryById(@PathVariable long id, @RequestBody Resource<SubCategory> request) {
        SubCategory subCategory = request.getAttribute();
        SubCategory subCategory1 = service.updateSubCategory(id, subCategory);
        Response response = new Response(subCategory1.getId(), "update successfully");
        Resource<Response> resource = new Resource<>(subCategory1.getId(), OBJECT_TYPE, response);
        return new ResponseEntity<>(resource, HttpStatus.OK);
    }
}
