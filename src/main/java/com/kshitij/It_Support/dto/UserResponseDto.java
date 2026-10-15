package com.kshitij.It_Support.dto;
import java.time.LocalDateTime;

import com.kshitij.It_Support.entities.*;


public class UserResponseDto {
    private Long id;
    private String name;
    private String email;
    private Role role;
    private LocalDateTime createdAt;
    public UserResponseDto(){}
    public UserResponseDto(Long id ,String name , String email , Role role , LocalDateTime createdAt){
        this.name = name;
        this.email = email;
        this.role = role;
        this.createdAt = createdAt;
        this.id = id;
    }
    public  Long getId(){
        return id;
    }
    public String getName() {
        return name;
    }
    public String getEmail() {
        return email;
    }
    public Role getRole() {
        return role;
    }
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
}
