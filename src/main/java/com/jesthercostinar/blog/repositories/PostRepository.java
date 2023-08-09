package com.jesthercostinar.blog.repositories;

import com.jesthercostinar.blog.entities.Category;
import com.jesthercostinar.blog.entities.Post;
import com.jesthercostinar.blog.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Integer> {

    List<Post> findByUser(User user);
    List<Post> findByCategory(Category category);
    List<Post> findByTitleContaining(String title);
}
