package com.punittewani.blogapis.blogapis.services;

import java.util.List;

import com.punittewani.blogapis.blogapis.payloads.CategoryDto;

public interface CategoryService {

    CategoryDto createCategory(CategoryDto categoryDto);
    
    CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId);

    List<CategoryDto> getAllCategories();
    
    CategoryDto getCategoryById(Integer categoryId);

    void deleteCategory(Integer categoryId);

    
}
