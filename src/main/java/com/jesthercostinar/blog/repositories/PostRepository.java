package com.jesthercostinar.blog.repositories;

import com.jesthercostinar.blog.entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Integer> {


}
