package com.kshitij.It_Support.services;

import com.kshitij.It_Support.entities.*;

import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.PageRequest;

import com.kshitij.It_Support.dto.CreateUserDto;
import com.kshitij.It_Support.dto.UpdateUserDto;
import com.kshitij.It_Support.dto.UserResponseDto;
import com.kshitij.It_Support.repository.UserRepository;
import com.kshitij.It_Support.exception.*;

import java.util.Optional;

@Service 
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder){
        this.userRepository=userRepository;
        this.passwordEncoder = passwordEncoder;
       
    }

    public UserResponseDto createUser( CreateUserDto request){
        Optional<User> existingUser = userRepository.findByEmail(request.getEmail());
        if(existingUser.isPresent()){
            throw new UserAlreadyExistException("user already present");
        }
        User user = new User(); 
        user.setEmail(request.getEmail());
        user.setName(request.getName());
        user.setRole(Role.CUSTOMER);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        User saved = userRepository.save(user);
        return new UserResponseDto(saved.getId(), saved.getName(),saved.getEmail(), saved.getRole(),saved.getCreatedAt());

    }
    public UserResponseDto mapToDto(User user){
        return new UserResponseDto(user.getId(),user.getName(),user.getEmail(),user.getRole(),user.getCreatedAt());
    }

    public Page<UserResponseDto> getAllUser(int page , int size , String direction , String sort ){
        Sort.Direction sortDirection = "desc".equalsIgnoreCase(direction)?Sort.Direction.DESC:Sort.Direction.ASC; 
        Pageable pageable = PageRequest.of(page , size , Sort.by(sortDirection , sort));
        return userRepository.findAll(pageable).map(this::mapToDto);
    }
    public UserResponseDto getByEmail(String email){
        Optional<User> user = userRepository.findByEmail(email);
        if(user.isEmpty()){
           throw new UserNotFoundException("mail not found");
        }
        return mapToDto(user.get());

    }
    public UserResponseDto getById(Long id){
        return userRepository.findById(id).map(this::mapToDto)
        .orElseThrow(() -> new UserNotFoundException("user not found"));
    }
    public UserResponseDto updatedUser(UpdateUserDto request ,  Long id ){
        User user = userRepository.findById(id)
        .orElseThrow(() -> new UserNotFoundException("User Not Found" + id));
        Optional<User> user2 = userRepository.findByEmail(request.getEmail());
        if(user2.isPresent() && !user.getId().equals(user2.get().getId())){
            throw new UserAlreadyExistException("email already present");
        }
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        User saved = userRepository.save(user);
        return mapToDto(saved);

    }
    public void deletedUser(Long id){
        User user = userRepository.findById(id)
        .orElseThrow(() -> new UserNotFoundException("User Not Found" + id));
        userRepository.delete(user);
        }

    }




    

    
 

