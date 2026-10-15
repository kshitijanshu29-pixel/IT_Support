package com.kshitij.It_Support.controller;

import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.kshitij.It_Support.dto.*;
import com.kshitij.It_Support.services.*;


import jakarta.validation.Valid;

@RestController 
public class UserController {
    private final UserService userService;
    private final StaffUserService staffUserService;
    public UserController(UserService userService , StaffUserService staffUserService){
        this.userService = userService;
        this.staffUserService = staffUserService;
    }

    @PostMapping("/user")
    public ResponseEntity<UserResponseDto> createUser(@Valid @RequestBody CreateUserDto request) {
        UserResponseDto response =  userService.createUser(request);
        return ResponseEntity
        .status(HttpStatus.CREATED)
        .body(response);

    }
    @GetMapping("/users/email")
    public ResponseEntity<UserResponseDto> getUserByEmail(@RequestParam String email){
        UserResponseDto response = userService.getByEmail(email);
        return ResponseEntity
        .status(HttpStatus.OK)
        .body(response);

    }
    @GetMapping("/users")
    public ResponseEntity<Page<UserResponseDto>> getAllUser(@RequestParam (defaultValue = "0")int page, @RequestParam (defaultValue = "10")int size , @RequestParam(defaultValue = "id")String sort , @RequestParam (defaultValue = "asc") String direction ){
       Page<UserResponseDto> response = userService.getAllUser(page, size, direction, sort);
        return ResponseEntity
        .status(HttpStatus.OK)
        .body(response);
    }
    @GetMapping("/users/{id}")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable Long id){
        UserResponseDto response = userService.getById(id);
        return ResponseEntity
        .status(HttpStatus.OK)
        .body(response);
    }
    @PutMapping("/user/{id}")
    public ResponseEntity<UserResponseDto> updateUser(@Valid @RequestBody UpdateUserDto request , @PathVariable  Long id){
        UserResponseDto response = userService.updatedUser(request, id);
        return ResponseEntity
        .status(HttpStatus.OK)
        .body(response);
    }
    @DeleteMapping("/user/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id ){
         userService.deletedUser(id);
         return ResponseEntity.noContent().build();
    }
    @PostMapping("/staffUser")
    public ResponseEntity<StaffUserResponse>createStaff(@Valid @RequestBody CreateStaffUserDto request){
        StaffUserResponse result = staffUserService.createStaff(request);
        return ResponseEntity
        .status(HttpStatus.CREATED)
        .body(result);
        
    }
}
