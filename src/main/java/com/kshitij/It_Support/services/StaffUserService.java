package com.kshitij.It_Support.services;

import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.kshitij.It_Support.dto.CreateStaffUserDto;
import com.kshitij.It_Support.dto.StaffUserResponse;
import com.kshitij.It_Support.repository.UserRepository;
import com.kshitij.It_Support.entities.*;
import com.kshitij.It_Support.exception.UserAlreadyExistException;

import java.util.Optional;
@Service
public class StaffUserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    public StaffUserService(UserRepository userRepository, PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
    public StaffUserResponse createStaff(CreateStaffUserDto request){
        Optional<User> existingUser = userRepository.findByEmail(request.getEmail());
        if(existingUser.isPresent()){
            throw new UserAlreadyExistException("email already taken");
        }
        if (request.getRole() != Role.SUPPORT && request.getRole() != Role.ADMIN) {
            throw new IllegalArgumentException( "Only SUPPORT or ADMIN roles are allowed" );
        }
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(request.getRole());
    

        User saved = userRepository.save(user);
        return new StaffUserResponse(saved.getName(),saved.getEmail(),saved.getRole(),saved.getCreatedAt());


    }


}
