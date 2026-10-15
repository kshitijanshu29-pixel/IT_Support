package com.kshitij.It_Support.services;

import com.kshitij.It_Support.dto.LoginRequestDto;
import com.kshitij.It_Support.dto.LoginResponseDto;
import com.kshitij.It_Support.security.JwtService;
import org.springframework.security.core.Authentication;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
@Service 
public class AuthService {
    private JwtService jwtService;
    private AuthenticationManager authenticationManager; 
    
    public AuthService(JwtService jwtService , AuthenticationManager authenticationManager ){
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        
    }
    public LoginResponseDto login(LoginRequestDto request){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(),request.getPassword()));
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String token = jwtService.generateToken(userDetails);
        String role = userDetails
        .getAuthorities()
        .iterator()
        .next() 
        .getAuthority();
        return new LoginResponseDto(userDetails.getUsername(), token, role);
    }


}
