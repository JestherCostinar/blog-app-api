package com.jesthercostinar.blog.mapper;

import com.jesthercostinar.blog.dto.PostDto;
import com.jesthercostinar.blog.entities.Post;

public class PostMapper {

    public static Post mapToPost(PostDto postDto) {
        Post post = new Post();
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setImageName(postDto.getImageName());
        post.setDateCreated(postDto.getDateCreated());
        post.setLastUpdated(postDto.getLastUpdated());
//        post.setCategory(postDto.getCategory());
//        post.setUser(postDto.getUser());
        return post;
    }

    public static PostDto mapToPostDto(Post post) {
        PostDto postDto = new PostDto();
        postDto.setTitle(post.getTitle());
        postDto.setContent(post.getContent());
        postDto.setImageName(post.getImageName());
        postDto.setDateCreated(post.getDateCreated());
        postDto.setLastUpdated(post.getLastUpdated());
//        postDto.setCategory(post.getCategory());
//        postDto.setUser(post.getUser());
        return postDto;
    }
}
