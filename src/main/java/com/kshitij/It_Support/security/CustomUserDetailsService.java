package com.kshitij.It_Support.security;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.UserDetails;

import com.kshitij.It_Support.repository.UserRepository;
import com.kshitij.It_Support.entities.*;
import com.kshitij.It_Support.exception.UserNotFoundException;
@Service 
public class CustomUserDetailsService implements UserDetailsService{
    private final UserRepository userRepository;
    public CustomUserDetailsService(UserRepository userRepository){
        this.userRepository = userRepository;
    }
    @Override 
    public UserDetails loadUserByUsername(String email){
        User user = userRepository.findByEmail(email)
        .orElseThrow(() -> new UserNotFoundException("wrong credential"));

        return org.springframework.security.core.userdetails.User
        .withUsername(user.getEmail())
        .password(user.getPassword())
        .roles(user.getRole().name())
        .build();
        
    }



}
