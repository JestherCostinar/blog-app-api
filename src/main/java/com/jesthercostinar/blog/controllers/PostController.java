package com.jesthercostinar.blog.controllers;

import com.jesthercostinar.blog.config.AppConstants;
import com.jesthercostinar.blog.dto.ApiResponse;
import com.jesthercostinar.blog.dto.PostDto;
import com.jesthercostinar.blog.dto.PostResponse;
import com.jesthercostinar.blog.services.FileService;
import com.jesthercostinar.blog.services.PostService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/api/")
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private FileService fileService;

    @Value("${project.image}")
    private String path;

    // CREATE
    @PostMapping("/user/{userId}/category/{categoryId}/posts")
    public ResponseEntity<PostDto> createPost(@RequestParam("title") String title,
                                              @RequestParam("content") String content,
                                              @RequestParam("image") MultipartFile image,
                                              @PathVariable int userId,
                                              @PathVariable int categoryId) throws IOException {
        String fileName = fileService.uploadImage(path, image);

        PostDto postDto = new PostDto();
        postDto.setTitle(title);
        postDto.setContent(content);
        postDto.setImageName(fileName);

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

    // POST Image upload REST API
    @PostMapping("post/image/upload/{id}")
    public ResponseEntity<PostDto> uploadPostImage(@RequestParam("image")MultipartFile image,
                                                         @PathVariable int id) throws IOException {
        PostDto postDto = postService.getPostById(id);

        String fileName = fileService.uploadImage(path, image);

        postDto.setImageName(fileName);
        return ResponseEntity.ok(postService.updatePost(postDto, id));
    }

    // Method to serve files
    @GetMapping(value = "post/image/{imageName}", produces = MediaType.IMAGE_JPEG_VALUE)
    public void downloadImage(@PathVariable("imageName") String imageName,
                              HttpServletResponse response) throws IOException {
        InputStream resource = fileService.getResource(path, imageName);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource, response.getOutputStream());
    }
}
