package com.lakshay.blog_be.service.impl;

import com.lakshay.blog_be.domain.dto.CreateCommentRequest;
import com.lakshay.blog_be.domain.dto.UpdateCommentRequest;
import com.lakshay.blog_be.domain.entities.Comment;
import com.lakshay.blog_be.domain.entities.Post;
import com.lakshay.blog_be.domain.entities.User;
import com.lakshay.blog_be.repositories.CommentRepository;
import com.lakshay.blog_be.service.CommentService;
import com.lakshay.blog_be.service.PostService;
import com.lakshay.blog_be.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final UserService userService;
    private final PostService postService;

    @Transactional
    @Override
    public Comment createComment(UUID userId, CreateCommentRequest request) {
        User user = userService.getUserById(userId);
        Post post = postService.getPostById(request.getPostId());
        Comment userComment = Comment.builder()
                .content(request.getContent())
                .user(user)
                .post(post)
                .build();
        return commentRepository.save(userComment);
    }

    @Override
    public void deleteComment(UUID commentId) {
        Comment comment = getCommentById(commentId);
        commentRepository.delete(comment);
    }

    @Override
    public List<Comment> getAllCommentByPostId(UUID postId) {
        postService.getPostById(postId);
        return commentRepository.findAllByPostId(postId);
    }

    @Override
    public Comment getCommentById(UUID commentId) {
        return commentRepository.findById(commentId).orElseThrow(() -> {
            return new EntityNotFoundException("Comment not found with id " + commentId);
        });
    }

    @Override
    public List<Comment> getAllCommentByUserId(UUID userId) {
        userService.getUserById(userId);
        return commentRepository.findAllByUserId(userId);
    }

    @Override
    public Comment updateComment(UUID id, UpdateCommentRequest updateCommentRequest) {
        Comment existingComment = getCommentById(id);
        existingComment.setContent(updateCommentRequest.getContent());
        return commentRepository.save(existingComment);
    }
}
