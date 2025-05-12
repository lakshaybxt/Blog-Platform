package com.lakshay.blog_be.service.impl;

import com.lakshay.blog_be.domain.CreatePostRequest;
import com.lakshay.blog_be.domain.PostStatus;
import com.lakshay.blog_be.domain.UpdatePostRequest;
import com.lakshay.blog_be.domain.entities.Category;
import com.lakshay.blog_be.domain.entities.Post;
import com.lakshay.blog_be.domain.entities.Tag;
import com.lakshay.blog_be.domain.entities.User;
import com.lakshay.blog_be.repositories.PostRepository;
import com.lakshay.blog_be.service.CategoryService;
import com.lakshay.blog_be.service.PostService;
import com.lakshay.blog_be.service.TagService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final CategoryService categoryService;
    private final TagService tagService;

    private static final int WORDS_PER_MINUTE = 200;

    @Override
    public Post getPostById(UUID id) {
        return postRepository.findById(id).orElseThrow(() -> {
            return new EntityNotFoundException("Post not found with id " + id);
        });
    }

    @Transactional(readOnly = true)
    @Override
    public List<Post> getAllPosts(UUID categoryId, UUID tagId) {
        if (categoryId != null && tagId != null) {
            Category category = categoryService.getCategoryById(categoryId);
            Tag tag = tagService.getTagById(tagId);
            return postRepository.findAllByStatusAndCategoryAndTagsContaining(
                    PostStatus.PUBLISHED,
                    category,
                    tag
            );
        }

        if (categoryId != null) {
            Category category = categoryService.getCategoryById(categoryId);
            return postRepository.findAllByStatusAndCategory(
                    PostStatus.PUBLISHED,
                    category
            );
        }

        if (tagId != null) {
            Tag tag = tagService.getTagById(tagId);
            return postRepository.findAllByStatusAndTagsContaining(
                    PostStatus.PUBLISHED,
                    tag
            );
        }

        return postRepository.findAllByStatus(PostStatus.PUBLISHED);
    }

    @Override
    public List<Post> getDraftPosts(User loggedInUser) {
        return postRepository.findAllByAuthorAndStatus(loggedInUser, PostStatus.DRAFT);
    }

    @Override
    @Transactional
    public Post createPost(User loggedInUser, CreatePostRequest createPostRequest) {
        Post newPost = new Post();
        newPost.setTitle(createPostRequest.getTitle());
        String postContent = createPostRequest.getContent();
        newPost.setContent(postContent);
        newPost.setStatus(createPostRequest.getStatus());
        newPost.setAuthor(loggedInUser);
        newPost.setReadingTime(calculateReadingTime(postContent));

        Category category = categoryService.getCategoryById(createPostRequest.getCategoryId());
        newPost.setCategory(category);

        Set<UUID> tagIds = createPostRequest.getTagIds();
        List<Tag> tags = tagService.getTagByIds(tagIds);
        newPost.setTags(new HashSet<>(tags));

        return postRepository.save(newPost);
    }

    @Override
    public Post updatePost(UUID id, UpdatePostRequest updatePostRequest) {
        Post existingPost = getPostById(id);

        existingPost.setTitle(updatePostRequest.getTitle());
        String postContent = updatePostRequest.getContent();
        existingPost.setContent(postContent);
        existingPost.setStatus(updatePostRequest.getStatus());
        existingPost.setReadingTime(calculateReadingTime(postContent));

        UUID updatedPostRequestCategoryId = updatePostRequest.getCategoryId();
        if(!existingPost.getCategory().getId().equals(updatedPostRequestCategoryId)) {
            Category newCategory = categoryService.getCategoryById(updatedPostRequestCategoryId);
            existingPost.setCategory(newCategory);
        }

        Set<UUID> existingTagsId = existingPost.getTags().stream().map(tag -> tag.getId()).collect(Collectors.toSet());
        Set<UUID> updatePostRequestTagsId = updatePostRequest.getTagIds();
        if(existingTagsId.equals(updatePostRequestTagsId)) {
            List<Tag> tags = tagService.getTagByIds(new HashSet<>(updatePostRequestTagsId));
            existingPost.setTags(new HashSet<>(tags));
        }

        return postRepository.save(existingPost);
    }

    @Override
    public void deletePost(UUID id) {
        Post post = getPostById(id);
        postRepository.delete(post);
    }

    private Integer calculateReadingTime(String content) {
        if(null == content || content.isEmpty()) return 0;
        int wordCount = content.trim().split("\\s+").length;
        return (int) Math.ceil((double) wordCount / WORDS_PER_MINUTE);
    }
}
