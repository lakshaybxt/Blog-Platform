package com.lakshay.blog_be.service;

import com.lakshay.blog_be.domain.entities.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.security.core.userdetails.UserDetails;

public interface AuthenticationService {
    User register(User registerRequest);
    UserDetails authenticate(@NotBlank(message = "Email is required") @Email(message = "Invalid email format") String email, @NotBlank(message = "Password is required") String password);
    String generateToken(UserDetails userDetails);
    String extractUsername(String token);
    Boolean validateToken(String token, UserDetails userDetails);
}
