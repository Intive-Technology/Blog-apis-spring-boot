package com.punittewani.blogapis.blogapis.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.punittewani.blogapis.blogapis.entities.Categories;
import com.punittewani.blogapis.blogapis.exceptions.ResourceNotFoundException;
import com.punittewani.blogapis.blogapis.payloads.CategoryDto;
import com.punittewani.blogapis.blogapis.repositories.CategoryRepo;
import com.punittewani.blogapis.blogapis.services.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        Categories category = this.dtoToCategory(categoryDto);
        Categories savedCategory = this.categoryRepo.save(category);
        return this.categoryToDto(savedCategory);
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId) {
        Categories category = this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category", "Id", categoryId));
        Categories categoryNeedtoUpdate = this.dtoToCategory(categoryDto);
        categoryNeedtoUpdate.setId(category.getId());
        Categories updatedCategory = this.categoryRepo.save(categoryNeedtoUpdate);
        return this.categoryToDto(updatedCategory);
    }

    @Override
    public List<CategoryDto> getAllCategories() {
        List<Categories> categories =  this.categoryRepo.findAll();
        List<CategoryDto> categoryDtos = categories.stream().map(category -> this.categoryToDto(category)).collect(Collectors.toList());
        return categoryDtos;
    }

    @Override
    public CategoryDto getCategoryById(Integer categoryId) {
        Categories category = this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category", "Id", categoryId));
        return this.categoryToDto(category);
    }

    @Override
    public void deleteCategory(Integer categoryId) {
        Categories category = this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category", "Id", categoryId));
        this.categoryRepo.delete(category);
    }
    
    public Categories dtoToCategory(CategoryDto categoryDto){
        return this.modelMapper.map(categoryDto, Categories.class);
    }
    public CategoryDto categoryToDto(Categories category){
        return this.modelMapper.map(category, CategoryDto.class);
    }
}
