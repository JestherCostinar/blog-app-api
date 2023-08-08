package com.jesthercostinar.blog.mapper;

import com.jesthercostinar.blog.dto.CategoryDto;
import com.jesthercostinar.blog.entities.Category;

public class CategoryMapper {
    public static CategoryDto mapToCategoryDto(Category category) {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(category.getId());
        categoryDto.setTitle(category.getTitle());
        categoryDto.setDescription(category.getDescription());

        return categoryDto;
    }

    public static Category mapToCategory(CategoryDto categoryDto) {
        Category category = new Category();
        category.setId(categoryDto.getId());
        category.setTitle(categoryDto.getTitle());
        category.setDescription(categoryDto.getDescription());

        return category;
    }
}
