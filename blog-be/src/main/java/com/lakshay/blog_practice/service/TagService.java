package com.lakshay.blog_practice.service;

import com.lakshay.blog_practice.domain.dto.CreateTagRequest;
import com.lakshay.blog_practice.domain.entities.Tag;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface TagService {
    Tag getTagById(UUID tagId);
    List<Tag> getTags();
    List<Tag> createTags(Set<String> tagName);
    List<Tag> getTagByIds(Set<UUID> ids);
    void deleteTag(UUID id);
}
