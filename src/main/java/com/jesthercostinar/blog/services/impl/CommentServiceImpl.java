package com.jesthercostinar.blog.services.impl;

import com.jesthercostinar.blog.dto.CommentDto;
import com.jesthercostinar.blog.entities.Comment;
import com.jesthercostinar.blog.entities.Post;
import com.jesthercostinar.blog.exceptions.ResourceNotFoundException;
import com.jesthercostinar.blog.repositories.CommentRepository;
import com.jesthercostinar.blog.repositories.PostRepository;
import com.jesthercostinar.blog.services.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private ModelMapper modelMapper;
    @Override
    public CommentDto createComment(CommentDto commentDto, int postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));

        Comment comment = modelMapper.map(commentDto, Comment.class);
        comment.setPost(post);

        return modelMapper.map(commentRepository.save(comment), CommentDto.class);
    }

    @Override
    public void deleteComment(int id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Comment", "id", id));

        commentRepository.deleteById(comment.getId());
    }
}
