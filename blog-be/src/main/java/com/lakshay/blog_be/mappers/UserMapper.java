package com.lakshay.blog_be.mappers;

import com.lakshay.blog_be.domain.dtos.AuthorDto;
import com.lakshay.blog_be.domain.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {
    AuthorDto toDto(User user);
}
