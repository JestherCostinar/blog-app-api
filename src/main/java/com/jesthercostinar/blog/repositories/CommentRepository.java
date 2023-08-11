package com.jesthercostinar.blog.repositories;

import com.jesthercostinar.blog.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
}
