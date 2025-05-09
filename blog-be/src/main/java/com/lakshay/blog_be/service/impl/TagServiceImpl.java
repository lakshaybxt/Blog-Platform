package com.lakshay.blog_be.service.impl;

import com.lakshay.blog_be.domain.entities.Tag;
import com.lakshay.blog_be.repositories.TagRepository;
import com.lakshay.blog_be.service.TagService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;

    @Override
    public List<Tag> getTags() {
        return tagRepository.findAllWithPostCount();
    }

    @Transactional
    @Override
    public List<Tag> createTags(Set<String> tagName) {
        List<Tag> existingTags = tagRepository.findByNameIn(tagName);

        Set<String> existingTagsNames = existingTags.stream()
                .map(tag -> tag.getName())
                .collect(Collectors.toSet());

        List<Tag> newTags = tagName.stream()
                .filter(name -> !existingTagsNames.contains(name))
                .map(name -> Tag.builder()
                        .name(name)
                        .posts(new HashSet<>())
                        .build())
                .toList();

        List<Tag> savedTags = new ArrayList<>();
        if(!newTags.isEmpty()) savedTags = tagRepository.saveAll(newTags);

        savedTags.addAll(existingTags);

        return savedTags;
    }

    @Transactional
    @Override
    public void deleteTag(UUID id) {
        tagRepository.findById(id).ifPresent(tag -> {
            if(!tag.getPosts().isEmpty()) {
                throw new IllegalStateException("Cannot delete tag with posts");
            }
            tagRepository.deleteById(id);
        });
    }
}
