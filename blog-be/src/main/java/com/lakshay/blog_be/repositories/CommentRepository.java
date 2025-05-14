package com.lakshay.blog_be.repositories;

import com.lakshay.blog_be.domain.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CommentRepository extends JpaRepository<Comment, UUID> {
    List<Comment> findAllByPostId(UUID postId);
    List<Comment> findAllByUserId(UUID userId);
}
