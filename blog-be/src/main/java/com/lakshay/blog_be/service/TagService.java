package com.lakshay.blog_be.service;

import com.lakshay.blog_be.domain.entities.Tag;

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
