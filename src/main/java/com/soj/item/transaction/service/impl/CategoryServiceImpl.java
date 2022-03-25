package com.soj.item.transaction.service.impl;

import com.soj.item.transaction.model.Category;

import com.soj.item.transaction.repository.CategoryRepository;
import com.soj.item.transaction.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository repository;

    public List<Category> getAllCategory() {
        return repository.findAll();
    }

    public Category getCategory(long id) {
        Optional<Category> optionalCategory = repository.findById(id);
        if (optionalCategory.isPresent()) {
            return optionalCategory.get();
        } else {
            throw new IllegalArgumentException("Id not present");
        }
    }

    public Category createNewCategory(Category category) {
        return repository.saveAndFlush(category);
    }

    public void deleteCategory(long id) {

        try {
            repository.deleteById(id);
        } catch (Exception e) {
            throw new IllegalArgumentException("Id not found ");
        }
    }

    public Category updateCategory(long id, Category category) {
        Optional<Category> optionalCategory = repository.findById(id);
        if (optionalCategory.isPresent()) {
            return repository.saveAndFlush(category);

        } else {
            throw new IllegalArgumentException("Id  not found");
        }
    }
}

