package com.lakshay.blog_be.controller;

import com.lakshay.blog_be.domain.dtos.CreateTagRequest;
import com.lakshay.blog_be.domain.dtos.TagDto;
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
    public ResponseEntity<List<TagDto>> getAllTags() {
        List<Tag> tags = tagService.getTags();
        List<TagDto> tagResponse = tags.stream()
                .map(tag -> tagMapper.toTagResponse(tag))
                .toList();

        return ResponseEntity.ok(tagResponse);
    }

    @PostMapping
    public ResponseEntity<List<TagDto>> createTags(@RequestBody CreateTagRequest createTagRequest) {
        List<Tag> savedTags = tagService.createTags(createTagRequest.getNames());
        List<TagDto> cratedTagResponse = savedTags.stream()
                .map(tag -> tagMapper.toTagResponse(tag)).toList();

        return new ResponseEntity<>(cratedTagResponse, HttpStatus.CREATED);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> deleteTag(@PathVariable UUID id) {
        tagService.deleteTag(id);
        return ResponseEntity.noContent().build();
    }
}
