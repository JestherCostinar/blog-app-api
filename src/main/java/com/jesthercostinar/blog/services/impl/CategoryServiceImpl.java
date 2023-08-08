package com.jesthercostinar.blog.services.impl;

import com.jesthercostinar.blog.dto.CategoryDto;
import com.jesthercostinar.blog.entities.Category;
import com.jesthercostinar.blog.exceptions.ResourceNotFoundException;
import com.jesthercostinar.blog.mapper.CategoryMapper;
import com.jesthercostinar.blog.repositories.CategoryRepository;
import com.jesthercostinar.blog.services.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        Category saveCategory = categoryRepository.save(modelMapper.map(categoryDto, Category.class));

        return modelMapper.map(saveCategory, CategoryDto.class);
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, int id) {
        Category existingCategory = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", id));
        existingCategory.setTitle(categoryDto.getTitle());
        existingCategory.setDescription(categoryDto.getDescription());

        Category savedCategory = categoryRepository.save(existingCategory);

        return modelMapper.map(savedCategory, CategoryDto.class);
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

        return modelMapper.map(existingCategory, CategoryDto.class);
    }

    @Override
    public List<CategoryDto> getAllCategory() {
        List<Category> categories = categoryRepository.findAll();

        return categories.stream()
                .map(category -> modelMapper.map(category, CategoryDto.class))
                .collect(Collectors.toList());
    }
}
