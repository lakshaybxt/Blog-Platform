package com.lakshay.blog_be.controllers;

import com.lakshay.blog_be.domain.dto.LikeResponseDto;
import com.lakshay.blog_be.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api/v1/post")
@RequiredArgsConstructor
public class LikeController {

    private final LikeService likeService;

    @PostMapping(path = "/{postId}/like")
    public ResponseEntity<?> likePost(
            @RequestAttribute UUID userId,
            @PathVariable UUID postId) {
        likeService.likePost(userId, postId);
        int count = likeService.getLikeCount(postId);
        return ResponseEntity.ok(Map.of(
                "message", "Post liked",
                "likeCount", count,
                "liked", true
        ));
    }

    @DeleteMapping(path = "/{postId}/like")
    public ResponseEntity<LikeResponseDto> unlikePost(
            @RequestAttribute UUID userId,
            @PathVariable UUID postId) {
        likeService.unlikePost(userId, postId);
        int count = likeService.getLikeCount(postId);
        LikeResponseDto response = LikeResponseDto.builder()
                .message("Post unliked")
                .likeCount(count)
                .liked(false)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/{postId}/likes/count")
    public ResponseEntity<Integer> getLikeCount(@PathVariable UUID postId) {
        int count =  likeService.getLikeCount(postId);
        return ResponseEntity.ok(count);
    }

    @GetMapping(path = "/{postId}/likes/has-liked")
    public ResponseEntity<Boolean> hasUserLiked(
            @RequestAttribute UUID userId,
            @PathVariable UUID postId) {
        boolean liked = likeService.hasUserLiked(userId, postId);
        return ResponseEntity.ok(liked);
    }
}
