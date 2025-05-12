package com.lakshay.blog_practice.service;

import com.lakshay.blog_practice.domain.CreatePostRequest;
import com.lakshay.blog_practice.domain.UpdatePostRequest;
import com.lakshay.blog_practice.domain.entities.Post;
import com.lakshay.blog_practice.domain.entities.User;

import java.util.List;
import java.util.UUID;

public interface PostService {
    Post getPostById(UUID id);
    List<Post> getAllPosts(UUID categoryId, UUID tagId);
    List<Post> getDraftPosts(User loggedInUser);
    Post createPost(User loggedInUser, CreatePostRequest createPostRequest);
    Post updatePost(UUID id, UpdatePostRequest updatePostRequest);
    void deletePost(UUID id);
}
