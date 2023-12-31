package com.jesthercostinar.blog.services.impl;

import com.jesthercostinar.blog.dto.PostDto;
import com.jesthercostinar.blog.dto.PostResponse;
import com.jesthercostinar.blog.entities.Category;
import com.jesthercostinar.blog.entities.Post;
import com.jesthercostinar.blog.entities.User;
import com.jesthercostinar.blog.exceptions.ResourceNotFoundException;
import com.jesthercostinar.blog.repositories.CategoryRepository;
import com.jesthercostinar.blog.repositories.PostRepository;
import com.jesthercostinar.blog.repositories.UserRepository;
import com.jesthercostinar.blog.services.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public PostDto createPost(PostDto postDto, int userId, int categoryId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "User ID", userId));

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "Category ID", categoryId));

        Post post = modelMapper.map(postDto, Post.class);
//        post.setImageName("default.png");
        post.setUser(user);
        post.setCategory(category);

        return modelMapper.map(postRepository.save(post), PostDto.class);
    }

    @Override
    public PostDto updatePost(PostDto postDto, int id) {
        Post existingPost = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "Id", id));

        existingPost.setTitle(postDto.getTitle());
        existingPost.setContent(postDto.getContent());
        existingPost.setImageName(postDto.getImageName());

        return modelMapper.map(postRepository.save(existingPost), PostDto.class);
    }

    @Override
    public void deletePost(int id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "Id", id));

        postRepository.delete(post);
    }

    @Override
    public PostResponse getAllPost(int pageNumber, int pageSize, String sortBy, String sortDir) {
        Sort sort = (sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending());

        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

        Page<Post> pagePosts = postRepository.findAll(pageable);

        List<Post> posts = pagePosts.getContent();

        List<PostDto> postDtos = posts.stream()
                .map(post -> modelMapper.map(post, PostDto.class))
                .collect(Collectors.toList());

        PostResponse postResponse = new PostResponse();
        postResponse.setContent(postDtos);
        postResponse.setPageNumber(pagePosts.getNumber());
        postResponse.setPageSize(pagePosts.getSize());
        postResponse.setTotalElements(pagePosts.getNumberOfElements());
        postResponse.setTotalPages(pagePosts.getTotalPages());
        postResponse.setLastPage(pagePosts.isLast());

        return postResponse;
    }

    @Override
    public PostDto getPostById(int id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "Id", id));

        return modelMapper.map(post, PostDto.class);
    }

    @Override
    public List<PostDto> getPostsByCategory(int categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "Id", categoryId));

        List<Post> posts = postRepository.findByCategory(category);

        return posts.stream()
                .map(post -> modelMapper.map(post, PostDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<PostDto> getPostsByUser(int userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));

        List<Post> posts = postRepository.findByUser(user);

        return posts.stream()
                .map(post -> modelMapper.map(post, PostDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<PostDto> searchPosts(String keyword) {
        List<Post> posts = postRepository.findByTitleContaining(keyword);
        return posts.stream()
                .map(post -> modelMapper.map(post, PostDto.class))
                .collect(Collectors.toList());
    }
}
