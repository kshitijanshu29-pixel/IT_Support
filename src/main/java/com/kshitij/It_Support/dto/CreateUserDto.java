package com.kshitij.It_Support.dto;





import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;



public class CreateUserDto {
    @NotBlank 
    private String name;
    @NotBlank 
    private String email;
    @NotBlank@Pattern (regexp = "^(?=.*[a-z])(?=.*\\d)(?=.*[@$!%#*&])[A-Za-z\\d@$!*&#]{8,}$",
    message = "Password must contain at least 8 characters including uppercase lowercase number and special character")
    private String password;
    
    
    public CreateUserDto(){
       
     
      

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
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    



    
}
