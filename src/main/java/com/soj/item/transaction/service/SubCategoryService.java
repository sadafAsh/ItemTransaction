package com.soj.item.transaction.service;

import com.soj.item.transaction.model.SubCategory;

import java.util.List;

public interface SubCategoryService {

    public List<SubCategory> getAllSubCategory();

    public SubCategory getSubCategory(long id);

    public SubCategory createNewSubCategory(SubCategory subCategory);

    public void deleteSubCategory(long id);

    public SubCategory updateSubCategory(long id, SubCategory subCategory);
}
