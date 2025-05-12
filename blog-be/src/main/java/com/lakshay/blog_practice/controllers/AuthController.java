package com.lakshay.blog_practice.controllers;

import com.lakshay.blog_practice.domain.dto.AuthResponse;
import com.lakshay.blog_practice.domain.dto.LoginRequest;
import com.lakshay.blog_practice.domain.dto.RegisterRequest;
import com.lakshay.blog_practice.domain.dto.RegisterResponse;
import com.lakshay.blog_practice.domain.entities.User;
import com.lakshay.blog_practice.mappers.UserMapper;
import com.lakshay.blog_practice.service.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Date;

@RestController
@RequestMapping(path = "/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationService authenticationService;
    private final UserMapper userMapper;

    @PostMapping(path = "/register")
    public ResponseEntity<RegisterResponse> register(
            @Valid @RequestBody RegisterRequest registerRequest) {
        User user = userMapper.toEntity(registerRequest);
        User registeredUser = authenticationService.register(user);

        RegisterResponse response = RegisterResponse.builder()
                .name(registeredUser.getName())
                .email(registeredUser.getEmail())
                .message("User registered successfully!")
                .registeredAt(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping(path = "/login")
    public ResponseEntity<?> login(
            @Valid @RequestBody LoginRequest loginRequest) {
        try {
            UserDetails user = authenticationService.authenticate(loginRequest.getEmail(), loginRequest.getPassword());
            AuthResponse authResponse = AuthResponse.builder()
                    .token(authenticationService.generateToken(user))
                    .expiresIn(36000)
                    .build();

            return ResponseEntity.ok(authResponse);
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Credentials");
        }
    }
}
