package com.soj.item.transaction.service;

import com.soj.item.transaction.model.Category;

import java.util.List;

public interface CategoryService {
    public List<Category> getAllCategory();

    public Category getCategory(long id);

    public Category createNewCategory(Category category);

    public void deleteCategory(long id);

    public Category updateCategory(long id, Category category);
}
