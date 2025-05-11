package com.lakshay.blog_be.service;

import com.lakshay.blog_be.domain.entities.User;

import java.util.UUID;

public interface UserService {
    User getUserById(UUID id);
}
