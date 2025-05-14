package com.lakshay.blog_be.mappers;

import com.lakshay.blog_be.domain.dto.CommentDto;
import com.lakshay.blog_be.domain.entities.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CommentMapper {

    @Mapping(target = "userName", source = "user.name")
    @Mapping(target = "postId", source = "post.id")
    CommentDto toDto(Comment comment);
}
