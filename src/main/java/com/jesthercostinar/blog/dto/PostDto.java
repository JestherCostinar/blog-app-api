package com.jesthercostinar.blog.dto;

import com.jesthercostinar.blog.entities.Category;
import com.jesthercostinar.blog.entities.User;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostDto {

    private int id;
    private String title;
    private String content;
    private String imageName;
    private LocalDateTime dateCreated;
    private LocalDateTime lastUpdated;
    private CategoryDto category;
    private UserDto user;
}
