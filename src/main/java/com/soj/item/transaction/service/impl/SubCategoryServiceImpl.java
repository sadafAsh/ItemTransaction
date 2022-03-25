package com.soj.item.transaction.service.impl;

import com.soj.item.transaction.model.SubCategory;
import com.soj.item.transaction.repository.SubCategoryRepository;
import com.soj.item.transaction.service.SubCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SubCategoryServiceImpl implements SubCategoryService {

    @Autowired
    private SubCategoryRepository repository;

    public List<SubCategory> getAllSubCategory() {
        return repository.findAll();
    }

    public SubCategory getSubCategory(long id) {
        Optional<SubCategory> optionalSubCategory = repository.findById(id);
        if (optionalSubCategory.isPresent()) {
            return optionalSubCategory.get();
        } else {
            throw new IllegalArgumentException("Id not present");
        }
    }


    public SubCategory createNewSubCategory(SubCategory subCategory) {
        return repository.saveAndFlush(subCategory);
    }

    public void deleteSubCategory(long id) {

        try {
            repository.deleteById(id);
        } catch (Exception e) {
            throw new IllegalArgumentException("Id not found");
        }
    }

    @Override
    public SubCategory updateSubCategory(long id, SubCategory subCategory) {
        Optional<SubCategory> optional = repository.findById(id);
        if (optional.isPresent()) {
            return repository.saveAndFlush(subCategory);

        } else {
            throw new IllegalArgumentException("Id  not found");
        }
    }
}
