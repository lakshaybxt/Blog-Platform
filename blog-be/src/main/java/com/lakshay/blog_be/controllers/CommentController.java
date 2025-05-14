package com.lakshay.blog_be.controllers;

import com.lakshay.blog_be.domain.dto.CommentDto;
import com.lakshay.blog_be.domain.dto.CreateCommentRequest;
import com.lakshay.blog_be.domain.dto.UpdateCommentRequest;
import com.lakshay.blog_be.domain.entities.Comment;
import com.lakshay.blog_be.mappers.CommentMapper;
import com.lakshay.blog_be.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api/v1/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;
    private final CommentMapper commentMapper;

    @PostMapping
    public ResponseEntity<CommentDto> createComment(
            @RequestAttribute UUID userId,
            @Valid @RequestBody CreateCommentRequest createCommentRequest) {
        Comment comment = commentService.createComment(userId, createCommentRequest);
        CommentDto commentDto = commentMapper.toDto(comment);
        return new ResponseEntity<>(commentDto, HttpStatus.CREATED);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> deleteComment(
            @RequestAttribute UUID userId,
            @PathVariable UUID id) {
        Comment existingComment = commentService.getCommentById(id);
        UUID commentOwnerId = existingComment.getUser().getId();
        UUID postOwnerId = existingComment.getPost().getAuthor().getId();

        if(!userId.equals(commentOwnerId) && !userId.equals(postOwnerId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        commentService.deleteComment(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(path = "/post/{postId}")
    public ResponseEntity<List<CommentDto>> getPostComments(@PathVariable UUID postId) {
        List<Comment> comments = commentService.getAllCommentByPostId(postId);
        List<CommentDto> commentDtos = comments.stream().map(commentMapper::toDto).toList();
        return ResponseEntity.ok(commentDtos);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<CommentDto> getComment(@PathVariable UUID id) {
        Comment comment = commentService.getCommentById(id);
        CommentDto commentDto = commentMapper.toDto(comment);
        return ResponseEntity.ok(commentDto);
    }

    @GetMapping(path = "/user")
    public ResponseEntity<List<CommentDto>> getAllCommentByUser(@RequestAttribute UUID userId) {
        List<Comment> userComments = commentService.getAllCommentByUserId(userId);
        List<CommentDto> userCommentDtos = userComments.stream().map(commentMapper::toDto).toList();
        return ResponseEntity.ok(userCommentDtos);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<CommentDto> updateComment(
            @RequestAttribute UUID userId,
            @PathVariable UUID id,
            @Valid @RequestBody UpdateCommentRequest updateCommentRequest) {
        Comment existingComment = commentService.getCommentById(id);

        if(!existingComment.getUser().getId().equals(userId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        Comment updatedComment = commentService.updateComment(id, updateCommentRequest);
        CommentDto updatedCommentDto = commentMapper.toDto(updatedComment);

        return ResponseEntity.ok(updatedCommentDto);
    }
}
