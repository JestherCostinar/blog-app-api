package com.jesthercostinar.blog.controllers;

import com.jesthercostinar.blog.dto.ApiResponse;
import com.jesthercostinar.blog.dto.CategoryDto;
import com.jesthercostinar.blog.entities.Category;
import com.jesthercostinar.blog.services.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping
    public ResponseEntity<CategoryDto> savedCategory(@Valid @RequestBody CategoryDto categoryDto) {
        CategoryDto category = categoryService.createCategory(categoryDto);

        return new ResponseEntity<>(category, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<CategoryDto>> getAllCategories() {
        return ResponseEntity.ok(categoryService.getAllCategory());
    }

    @GetMapping("{id}")
    public ResponseEntity<CategoryDto> getCategoryById(@PathVariable Integer id) {
        return ResponseEntity.ok(categoryService.getCategory(id));
    }

    @PutMapping("{id}")
    public ResponseEntity<CategoryDto> updateCategory(@PathVariable Integer id,
                                                      @Valid @RequestBody CategoryDto categoryDto) {
        return ResponseEntity.ok(categoryService.updateCategory(categoryDto, id));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Integer id) {
        categoryService.deleteCategory(id);

        return new ResponseEntity(new ApiResponse("Category has been successfully deleted!", true), HttpStatus.NO_CONTENT);
    }
}
