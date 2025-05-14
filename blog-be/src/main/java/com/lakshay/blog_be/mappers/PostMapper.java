package com.lakshay.blog_be.mappers;

import com.lakshay.blog_be.domain.CreatePostRequest;
import com.lakshay.blog_be.domain.UpdatePostRequest;
import com.lakshay.blog_be.domain.dto.CreatePostRequestDto;
import com.lakshay.blog_be.domain.dto.PostDto;
import com.lakshay.blog_be.domain.dto.UpdatePostRequestDto;
import com.lakshay.blog_be.domain.entities.Post;
import com.lakshay.blog_be.domain.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.util.Set;

@Mapper(componentModel = "spring",
        uses = {UserMapper.class, CategoryMapper.class, TagMapper.class, CommentMapper.class},
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PostMapper {

    @Mapping(target = "author", source = "author")
    @Mapping(target = "category", source = "category")
    @Mapping(target = "tags", source = "tags")
    @Mapping(target = "postStatus", source = "status")
    @Mapping(target = "likeCount", source = "likedByUsers", qualifiedByName = "calculateLikeCount")
    @Mapping(target = "comments", source = "comments")
    PostDto toDto(Post post);

    CreatePostRequest toCreatePostRequest(CreatePostRequestDto createPostRequestDto);
    UpdatePostRequest toUpdatePostRequest(UpdatePostRequestDto updatePostRequestDto);

    @Named("calculateLikeCount")
    default long calculatePostCount(Set<User> likedByUser) {
        if(null == likedByUser) return 0;
        return likedByUser.size();
    }
}
