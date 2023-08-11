package com.jesthercostinar.blog.services;

import com.jesthercostinar.blog.dto.CommentDto;

public interface CommentService {

    // Create comment
    CommentDto createComment(CommentDto commentDto, int postId);

    // Delete comment
    void deleteComment(int id);
}
