package com.lakshay.blog_be.repositories;

import com.lakshay.blog_be.domain.PostStatus;
import com.lakshay.blog_be.domain.entities.Category;
import com.lakshay.blog_be.domain.entities.Post;
import com.lakshay.blog_be.domain.entities.Tag;
import com.lakshay.blog_be.domain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PostRepository extends JpaRepository<Post, UUID> {
    List<Post> findAllByStatusAndCategoryAndTagsContaining(PostStatus status, Category category, Tag tags);
    List<Post> findAllByStatusAndCategory(PostStatus status, Category category);
    List<Post> findAllByStatusAndTagsContaining(PostStatus status, Tag tags);
    List<Post> findAllByStatus(PostStatus status);
    List<Post> findAllByAuthorAndStatus(User author, PostStatus status);
}
