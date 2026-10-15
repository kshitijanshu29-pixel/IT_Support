package com.kshitij.It_Support.dto;

import java.time.LocalDateTime;

import com.kshitij.It_Support.entities.Role;
public class StaffUserResponse {
    private String name;
    private String email;
    private LocalDateTime createdAt;
    

    private Role role;
    public StaffUserResponse(String name ,String email , Role role, LocalDateTime createdAt){
        this.name= name;
        this.role=role;
        this.email=email;
        this.createdAt=createdAt;
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
