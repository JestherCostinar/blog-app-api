package com.jesthercostinar.blog.services.impl;

import com.jesthercostinar.blog.dto.CategoryDto;
import com.jesthercostinar.blog.entities.Category;
import com.jesthercostinar.blog.exceptions.ResourceNotFoundException;
import com.jesthercostinar.blog.mapper.CategoryMapper;
import com.jesthercostinar.blog.repositories.CategoryRepository;
import com.jesthercostinar.blog.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        Category saveCategory = categoryRepository.save(CategoryMapper.mapToCategory(categoryDto));

        return CategoryMapper.mapToCategoryDto(saveCategory);
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, int id) {
        Category existingCategory = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", id));
        existingCategory.setTitle(categoryDto.getTitle());
        existingCategory.setDescription(categoryDto.getDescription());

        Category savedCategory = categoryRepository.save(existingCategory);

        return CategoryMapper.mapToCategoryDto(savedCategory);
    }

    @Override
    public void deleteCategory(int id) {
        Category existingCategory = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", id));

        categoryRepository.deleteById(existingCategory.getId());
    }

    @Override
    public CategoryDto getCategory(int id) {
        Category existingCategory = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", id));

        return CategoryMapper.mapToCategoryDto(existingCategory);
    }

    @Override
    public List<CategoryDto> getAllCategory() {
        List<Category> categories = categoryRepository.findAll();

        return categories.stream()
                .map(category -> CategoryMapper.mapToCategoryDto(category))
                .collect(Collectors.toList());
    }
}
