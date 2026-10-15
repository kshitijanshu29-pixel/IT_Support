package com.kshitij.It_Support.dto;

public class LoginResponseDto {
    private String email;
    private String token;
    private String role;
    public LoginResponseDto(String email , String token , String role){
        this.email=email;
        this.token = token;
        this.role = role;
    }
    public String getEmail() {
        return email;
    }
    public String getToken() {
        return token;
    }
    public String getRole() {
        return role;
    }
    


}

