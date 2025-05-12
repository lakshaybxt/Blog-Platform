package com.lakshay.blog_practice.mappers;

import com.lakshay.blog_practice.domain.CreatePostRequest;
import com.lakshay.blog_practice.domain.UpdatePostRequest;
import com.lakshay.blog_practice.domain.dto.CreatePostRequestDto;
import com.lakshay.blog_practice.domain.dto.PostDto;
import com.lakshay.blog_practice.domain.dto.UpdatePostRequestDto;
import com.lakshay.blog_practice.domain.entities.Post;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", uses = {UserMapper.class}, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PostMapper {

    @Mapping(target = "author", source = "author")
    @Mapping(target = "category", source = "category")
    @Mapping(target = "tags", source = "tags")
    @Mapping(target = "postStatus", source = "status")
    PostDto toDto(Post post);

    CreatePostRequest toCreatePostRequest(CreatePostRequestDto createPostRequestDto);
    UpdatePostRequest toUpdatePostRequest(UpdatePostRequestDto updatePostRequestDto);
}
