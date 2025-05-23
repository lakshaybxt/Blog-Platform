package com.lakshay.blog_be.mappers;

import com.lakshay.blog_be.domain.PostStatus;
import com.lakshay.blog_be.domain.dto.TagDto;
import com.lakshay.blog_be.domain.entities.Post;
import com.lakshay.blog_be.domain.entities.Tag;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.util.Set;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TagMapper {

    @Mapping(target = "postCount", source = "posts", qualifiedByName = "calculatePostCount")
    TagDto toDto(Tag tag);

    @Named("calculatePostCount")
    default long calculatePostCount(Set<Post> posts) {
        if(null == posts) return 0;
        return posts.stream()
                .filter(post -> PostStatus.PUBLISHED.equals(post.getStatus()))
                .count();
    }
}
