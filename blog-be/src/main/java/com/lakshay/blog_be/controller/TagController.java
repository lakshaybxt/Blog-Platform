package com.lakshay.blog_be.controller;

import com.lakshay.blog_be.domain.dtos.CreateTagRequest;
import com.lakshay.blog_be.domain.dtos.TagResponse;
import com.lakshay.blog_be.domain.entities.Tag;
import com.lakshay.blog_be.mappers.TagMapper;
import com.lakshay.blog_be.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api/v1/tags")
@RequiredArgsConstructor
public class TagController {

    private final TagService tagService;
    private final TagMapper tagMapper;

    @GetMapping
    public ResponseEntity<List<TagResponse>> getAllTags() {
        List<Tag> tags = tagService.getTags();
        List<TagResponse> tagResponses = tags.stream()
                .map(tag -> tagMapper.toTagResponse(tag))
                .toList();

        return ResponseEntity.ok(tagResponses);
    }

    @PostMapping
    public ResponseEntity<List<TagResponse>> createTags(@RequestBody CreateTagRequest createTagRequest) {
        List<Tag> savedTags = tagService.createTags(createTagRequest.getNames());
        List<TagResponse> cratedTagResponses = savedTags.stream()
                .map(tag -> tagMapper.toTagResponse(tag)).toList();

        return new ResponseEntity<>(cratedTagResponses, HttpStatus.CREATED);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> deleteTag(@PathVariable UUID id) {
        tagService.deleteTag(id);
        return ResponseEntity.noContent().build();
    }
}
