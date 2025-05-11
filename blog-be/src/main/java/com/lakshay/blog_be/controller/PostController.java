package com.lakshay.blog_be.controller;

import com.lakshay.blog_be.domain.CreatePostRequest;
import com.lakshay.blog_be.domain.UpdatePostRequest;
import com.lakshay.blog_be.domain.dtos.CreatePostRequestDto;
import com.lakshay.blog_be.domain.dtos.PostDto;
import com.lakshay.blog_be.domain.dtos.UpdatePostRequestDto;
import com.lakshay.blog_be.domain.entities.Post;
import com.lakshay.blog_be.domain.entities.User;
import com.lakshay.blog_be.mappers.PostMapper;
import com.lakshay.blog_be.service.PostService;
import com.lakshay.blog_be.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api/v1/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final PostMapper postMapper;
    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<PostDto>> getAllPosts(
            @RequestParam(required = false) UUID categoryId,
            @RequestParam(required = false) UUID tagId) {
        List<Post> posts = postService.getAllPosts(categoryId, tagId);
        List<PostDto> postDtos = posts.stream().map(post -> postMapper.toDto(post)).toList();
        return ResponseEntity.ok(postDtos);
    }

    // Here @RequestAttribute fetch the id from the current login user in our JwtAuthenticationFilter
    @GetMapping(path = "/drafts")
    public ResponseEntity<List<PostDto>> getDrafts(@RequestAttribute UUID userId) {
        User loggedInUser = userService.getUserById(userId);
        List<Post> draftPost = postService.getDraftPosts(loggedInUser);
        List<PostDto> postDtos = draftPost.stream().map(post -> postMapper.toDto(post)).toList();
        return ResponseEntity.ok(postDtos);
    }

    @PostMapping
    public ResponseEntity<PostDto> createPost(
            @Valid @RequestBody CreatePostRequestDto createPostRequestDto,
            @RequestAttribute UUID userId) {
        User loggedInUser = userService.getUserById(userId);
        CreatePostRequest createPostRequest = postMapper.toCreatePostRequest(createPostRequestDto);

        Post createdPost = postService.createPost(loggedInUser, createPostRequest);
        PostDto createdPostDto = postMapper.toDto(createdPost);
        return new ResponseEntity<>(createdPostDto, HttpStatus.CREATED);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<PostDto> updatePost(
            @PathVariable UUID id,
            @RequestAttribute UUID userId,
            @Valid @RequestBody UpdatePostRequestDto updatePostRequestDto) {
        Post existingPost = postService.getPostById(id);

        if(!existingPost.getAuthor().getId().equals(userId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        UpdatePostRequest updatePostRequest = postMapper.toUpdatePostRequest(updatePostRequestDto);
        Post updatedPost = postService.updatePost(id, updatePostRequest);
        PostDto updatedPostDto = postMapper.toDto(updatedPost);

        return ResponseEntity.ok(updatedPostDto);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<PostDto> getPost(
            @PathVariable UUID id,
            @RequestAttribute UUID userId
    ) {
        Post existingPost = postService.getPostById(id);

        if(!existingPost.getAuthor().getId().equals(userId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        Post post = postService.getPost(id);
        PostDto postDto = postMapper.toDto(post);

        return ResponseEntity.ok(postDto);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable UUID id, @RequestAttribute UUID userId) {
        Post existingPost = postService.getPostById(id);

        if(!existingPost.getAuthor().getId().equals(userId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        postService.deltePost(id);
        return ResponseEntity.noContent().build();
    }
}
