package com.lakshay.blog_be.service;

import com.lakshay.blog_be.domain.CreatePostRequest;
import com.lakshay.blog_be.domain.UpdatePostRequest;
import com.lakshay.blog_be.domain.entities.Post;
import com.lakshay.blog_be.domain.entities.User;

import java.util.List;
import java.util.UUID;

public interface PostService {
    Post getPostById(UUID id);
    List<Post> getAllPosts(UUID categoryId, UUID tagId);
    List<Post> getUserPosts(User loggedInUser,UUID categoryId, UUID tagId);
    List<Post> getDraftPosts(User loggedInUser);
    Post createPost(User loggedInUser, CreatePostRequest createPostRequest);
    Post updatePost(UUID id, UpdatePostRequest updatePostRequest);
    void deletePost(UUID id);
}
