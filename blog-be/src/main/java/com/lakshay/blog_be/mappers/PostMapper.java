package com.lakshay.blog_be.mappers;

import com.lakshay.blog_be.domain.CreatePostRequest;
import com.lakshay.blog_be.domain.UpdatePostRequest;
import com.lakshay.blog_be.domain.dtos.CreatePostRequestDto;
import com.lakshay.blog_be.domain.dtos.PostDto;
import com.lakshay.blog_be.domain.dtos.UpdatePostRequestDto;
import com.lakshay.blog_be.domain.entities.Post;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

// We add user mapper class for mapping author to author dto.
@Mapper(componentModel = "spring",uses = {UserMapper.class}, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PostMapper {

    @Mapping(target = "author", source = "author")
    @Mapping(target = "category", source = "category")
    @Mapping(target = "tags", source = "tags")
    @Mapping(target = "postStatus", source = "status")
    PostDto toDto(Post post);

    CreatePostRequest toCreatePostRequest(CreatePostRequestDto createPostRequestDto);
    UpdatePostRequest toUpdatePostRequest(UpdatePostRequestDto dto);
}
