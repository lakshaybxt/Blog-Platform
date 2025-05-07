package com.lakshay.blog_be.services;

import org.springframework.security.core.userdetails.UserDetails;

public interface AuthenticationService {
    UserDetails authenticate(String name, String pass);
    String generateToken(UserDetails userDetails);
}
