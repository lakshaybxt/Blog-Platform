package com.lakshay.blog_be.service;

import com.lakshay.blog_be.domain.dto.CreateCommentRequest;
import com.lakshay.blog_be.domain.dto.UpdateCommentRequest;
import com.lakshay.blog_be.domain.entities.Comment;

import java.util.List;
import java.util.UUID;

public interface CommentService {
    Comment createComment(UUID userId, CreateCommentRequest request);
    void deleteComment(UUID commentId);
    List<Comment> getAllCommentByPostId(UUID postId);
    Comment getCommentById(UUID commentId);
    List<Comment> getAllCommentByUserId(UUID userId);
    Comment updateComment(UUID id, UpdateCommentRequest updateCommentRequest);
}
