package com.jesthercostinar.blog.repositories;

import com.jesthercostinar.blog.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
}
