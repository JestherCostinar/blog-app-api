package com.jesthercostinar.blog.services;

import com.jesthercostinar.blog.dto.CategoryDto;

import java.util.List;

public interface CategoryService {
    CategoryDto createCategory(CategoryDto categoryDto);

    CategoryDto updateCategory(CategoryDto categoryDto, int id);
    void deleteCategory(int id);
    CategoryDto getCategory(int id);
    List<CategoryDto> getAllCategory();
}
