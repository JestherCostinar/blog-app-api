package com.jesthercostinar.blog.controllers;

import com.jesthercostinar.blog.dto.ApiResponse;
import com.jesthercostinar.blog.dto.CommentDto;
import com.jesthercostinar.blog.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping("post/{postId}/comments")
    public ResponseEntity<CommentDto> createComment(@RequestBody CommentDto commentDto,
                                                    @PathVariable int postId) {
        return new ResponseEntity<>(commentService.createComment(commentDto, postId), HttpStatus.CREATED);
    }

    @DeleteMapping("comment/{id}")
    public ResponseEntity<ApiResponse> deleteComment(@PathVariable int id) {
        commentService.deleteComment(id);

        return new ResponseEntity<>(new ApiResponse("Comment has been deleted", true), HttpStatus.OK);
    }
}
