package com.jesthercostinar.blog.services;

import com.jesthercostinar.blog.dto.PostDto;
import com.jesthercostinar.blog.dto.PostResponse;

import java.util.List;

public interface PostService {

    // Create Post
    PostDto createPost(PostDto postDto, int userId, int categoryId);

    // Update Post
    PostDto updatePost(PostDto postDto, int id);

    // Delete Post
    void deletePost(int id);

    // Get all Posts
    PostResponse getAllPost(int pageNumber, int pageSize, String sortBy, String sortDir);

    // Get Post by ID
    PostDto getPostById(int id);

    // Get Posts by Category
    List<PostDto> getPostsByCategory(int categoryId);

    // Get Posts by User ID
    List<PostDto> getPostsByUser(int userId);

    // Search Post
    List<PostDto> searchPosts(String keyword);

}
