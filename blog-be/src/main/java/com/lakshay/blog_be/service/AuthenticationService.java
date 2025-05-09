package com.lakshay.blog_be.service;

import com.lakshay.blog_be.domain.entities.User;
import org.springframework.security.core.userdetails.UserDetails;

public interface AuthenticationService {
    UserDetails authenticate(String email, String password);
    String generateToken(UserDetails userDetails);
    UserDetails validateToken(String token);
    User register(User user);
}
