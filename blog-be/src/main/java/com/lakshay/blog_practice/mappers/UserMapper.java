package com.lakshay.blog_practice.mappers;

import com.lakshay.blog_practice.domain.dto.RegisterRequest;
import com.lakshay.blog_practice.domain.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {
    User toEntity(RegisterRequest registerRequest);
}
