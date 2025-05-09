package com.lakshay.blog_be.controller;

import com.lakshay.blog_be.domain.dtos.AuthResponse;
import com.lakshay.blog_be.domain.dtos.LoginRequest;
import com.lakshay.blog_be.domain.entities.User;
import com.lakshay.blog_be.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/auth")
public class AuthController {

    private final AuthenticationService authenticationService;

    @PostMapping(path = "/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest loginRequest) {
        UserDetails user = authenticationService.authenticate(loginRequest.getEmail(), loginRequest.getPassword());

        AuthResponse authResponse = AuthResponse.builder()
                .token(authenticationService.generateToken(user))
                .expiresIn(36000)
                .build();
        return ResponseEntity.ok(authResponse);
    }

    @PostMapping(path = "/register")
    public ResponseEntity<LoginRequest> register(@RequestBody User user) {
        User registerUser = authenticationService.register(user);
        LoginRequest loginRequest = LoginRequest.builder()
                .email(user.getEmail())
                .password(user.getPassword())
                .build();
        return new ResponseEntity<>(loginRequest, HttpStatus.CREATED);
    }
}
