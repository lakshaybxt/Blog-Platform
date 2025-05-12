package com.lakshay.blog_practice.service;

import com.lakshay.blog_practice.domain.entities.User;

import java.util.UUID;

public interface UserService {
    User getUserById(UUID userId);
}
