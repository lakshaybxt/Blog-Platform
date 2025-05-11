package com.lakshay.blog_be.service;

import com.lakshay.blog_be.domain.CreatePostRequest;
import com.lakshay.blog_be.domain.UpdatePostRequest;
import com.lakshay.blog_be.domain.entities.Post;
import com.lakshay.blog_be.domain.entities.User;

import java.util.List;
import java.util.UUID;

public interface PostService {
    Post getPost(UUID id);
    List<Post> getAllPosts(UUID categoryId, UUID tagId);
    List<Post> getDraftPosts(User user);
    Post createPost(User user, CreatePostRequest createPostRequest);
    Post updatePost(UUID id, UpdatePostRequest updatePostRequest);
    Post getPostById(UUID id);
    void deltePost(UUID id);
}
