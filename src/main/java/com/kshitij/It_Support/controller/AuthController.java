package com.kshitij.It_Support.controller;

import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.kshitij.It_Support.services.*;

import jakarta.validation.Valid;

import com.kshitij.It_Support.dto.*;
@RestController 
public class AuthController {
    private final AuthService authService;
    private final UserService userService;
    public AuthController(AuthService authService , UserService userService){
        this.authService = authService;
        this.userService = userService;
        
    }
    @GetMapping("/auth/login") 
    public ResponseEntity<LoginResponseDto> login(@Valid @RequestBody LoginRequestDto request ){
        LoginResponseDto response =  authService.login(request);
        return ResponseEntity
        .status(HttpStatus.OK)
        .body(response);
        
    }
    @PostMapping("/auth/register")
    public ResponseEntity<UserResponseDto> register(@RequestBody CreateUserDto request){
        UserResponseDto response = userService.createUser(request);
        return ResponseEntity
        .status(HttpStatus.OK)
        .body(response);    
    }


}
