package com.jesthercostinar.blog.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, length = 100)
    private String title;

    @Column(length = 10000)
    private String content;
    private String imageName;

    @CreationTimestamp
    private LocalDateTime dateCreated;

    @UpdateTimestamp
    private LocalDateTime lastUpdated;
    @ManyToOne
    private Category category;
    @ManyToOne
    private User user;
}
