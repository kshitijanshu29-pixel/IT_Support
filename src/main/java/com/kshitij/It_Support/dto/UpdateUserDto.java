package com.kshitij.It_Support.dto;



import jakarta.validation.constraints.NotBlank;



public class UpdateUserDto {
    @NotBlank 
    private String name;
    @NotBlank 
    private String email;
    
    
    
    public UpdateUserDto( ){
        
      

    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    
}


