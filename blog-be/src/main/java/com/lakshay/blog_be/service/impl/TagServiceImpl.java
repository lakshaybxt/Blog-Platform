package com.lakshay.blog_be.service.impl;

import com.lakshay.blog_be.domain.entities.Tag;
import com.lakshay.blog_be.repositories.TagRepository;
import com.lakshay.blog_be.service.TagService;
import jakarta.persistence.EntityNotFoundException;
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
    public Tag getTagById(UUID tagId) {
        return tagRepository.findById(tagId).orElseThrow(() ->
                new EntityNotFoundException("Tag not found with ID " + tagId));
    }

    @Override
    public List<Tag> getTags() {
        return tagRepository.findAllWithPostCount();
    }

    @Override
    @Transactional
    public List<Tag> createTags(Set<String> tagName) {
        List<Tag> existingTags = tagRepository.findByNameIn(tagName);
        Set<String> existingTagsName = existingTags.stream()
                .map(tag -> tag.getName())
                .collect(Collectors.toSet());

        List<Tag> newTags = tagName.stream()
                .filter(name -> !existingTagsName.contains(name))
                .map(name -> Tag.builder()
                        .name(name)
                        .posts(new HashSet<>())
                        .build()
                ).toList();

        List<Tag> savedTags = new ArrayList<>();
        if(!newTags.isEmpty()) savedTags = tagRepository.saveAll(newTags);

        savedTags.addAll(existingTags);
        return savedTags;
    }

    @Override
    public List<Tag> getTagByIds(Set<UUID> ids) {
        List<Tag> foundTags = tagRepository.findAllById(ids);
        if(foundTags.size() != ids.size()) {
            throw new EntityNotFoundException("Not all specified tag Ids exist");
        }
        return foundTags;
    }

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
