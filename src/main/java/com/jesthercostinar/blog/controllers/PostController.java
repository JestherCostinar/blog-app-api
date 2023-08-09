package com.jesthercostinar.blog.controllers;

import com.jesthercostinar.blog.config.AppConstants;
import com.jesthercostinar.blog.dto.ApiResponse;
import com.jesthercostinar.blog.dto.PostDto;
import com.jesthercostinar.blog.dto.PostResponse;
import com.jesthercostinar.blog.entities.Post;
import com.jesthercostinar.blog.services.PostService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/")
public class PostController {

    @Autowired
    private PostService postService;

    // CREATE
    @PostMapping("/user/{userId}/category/{categoryId}/posts")
    public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto,
                                              @PathVariable int userId,
                                              @PathVariable int categoryId) {
        PostDto savedPost = postService.createPost(postDto, userId, categoryId);
        return new ResponseEntity<PostDto>(savedPost, HttpStatus.CREATED);
    }

    // Get Post by Category REST API
    @GetMapping("category/{categoryId}/posts")
    public ResponseEntity<List<PostDto>> getPostsByCategory(@PathVariable int categoryId) {
        List<PostDto> posts = postService.getPostsByCategory(categoryId);

        return ResponseEntity.ok(posts);
    }

    // Get Posts by User REST API
    @GetMapping("user/{userId}/posts")
    public ResponseEntity<List<PostDto>> getPostsByUser(@PathVariable int userId) {
        List<PostDto> posts = postService.getPostsByUser(userId);

        return ResponseEntity.ok(posts);
    }

    // Get All Post
    @GetMapping("posts")
    public ResponseEntity<PostResponse> getAllPosts(@RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) int pageNumber,
                                                    @RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) int pageSize,
                                                    @RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY, required = false)String sortBy,
                                                    @RequestParam(value = "sortDir", defaultValue = AppConstants.SORT_DIR, required = false) String sortDir) {

        return ResponseEntity.ok(postService.getAllPost(pageNumber, pageSize, sortBy, sortDir));
    }

    // Get Post by ID
    @GetMapping("post/{postId}")
    public ResponseEntity<PostDto> getPostById(@PathVariable int postId) {
        return ResponseEntity.ok(postService.getPostById(postId));
    }

    // Delete Post REST API
    @DeleteMapping("post/{id}")
    public ApiResponse deletePost(@PathVariable int id) {
        postService.deletePost(id);

        return new ApiResponse("Post is successfully delete", true);
    }

    // Update Post REST API
    @PutMapping("post/{id}")
    public ResponseEntity<PostDto> updatePost(@PathVariable int id,
                                               @RequestBody PostDto postDto) {
        return ResponseEntity.ok(postService.updatePost(postDto, id));
    }

    /* Search REST API */
    @GetMapping("posts/search/{keyword}")
    public ResponseEntity<List<PostDto>> searchPostByTitle(@PathVariable String keyword) {
        return ResponseEntity.ok(postService.searchPosts(keyword));
    }


}
