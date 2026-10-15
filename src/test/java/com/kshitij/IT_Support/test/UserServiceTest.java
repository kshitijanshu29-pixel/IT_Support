package com.kshitij.IT_Support.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

import java.util.Optional;

import java.util.List;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;


import com.kshitij.It_Support.services.*;
import com.kshitij.It_Support.repository.*;
import com.kshitij.It_Support.entities.*;
import com.kshitij.It_Support.dto.*;
import com.kshitij.It_Support.exception.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;
    @Test 
    void createUser_shouldReturnUser(){
        User user = new User();
        user.setName("kshitij");
        user.setEmail("abc@gmail.com");
        user.setId(1l);
        user.setRole(Role.CUSTOMER);
        user.setCreatedAt(null);

        CreateUserDto request = new CreateUserDto();
            request.setEmail("abc@gmail.com");
            when(userRepository.findByEmail("abc@gmail.com"))
            .thenReturn(Optional.empty());
            when(userRepository.save(any(User.class)))
            .thenReturn(user); 
            UserResponseDto response =  userService.createUser(request);
            assertEquals("abc@gmail.com" , response.getEmail());      

    }
    @Test
    void createUser_ShouldThrowException_WhenUserAlreadyExist(){
        CreateUserDto request = new CreateUserDto();
        request.setEmail("abc@gmail.com");
        User user = new User();
        user.setEmail("abc@123");
        when(userRepository.findByEmail("abc@gmail.com"))
        .thenReturn(Optional.of(user));
        assertThrows(UserAlreadyExistException.class,() -> userService.createUser(request));
    }
    @Test
    void getUserByEmail_ShouldReturnUser(){
        User user = new User();
         user.setName("kshitij");
        user.setEmail("abc@gmail.com");
        user.setId(1l);
        user.setRole(Role.CUSTOMER);
        user.setCreatedAt(null);
        
        when(userRepository.findByEmail("abc@gmail.com"))
        .thenReturn(Optional.of(user));
        UserResponseDto response = userService.getByEmail("abc@gmail.com");
        assertEquals("abc@gmail.com", response.getEmail());


    }
    @Test
    void getByEmail_ShouldThrowException_whenUserNotFound(){
        User user = new User();
        user.setEmail("abc@gmail.com");
        when(userRepository.findByEmail("abc@gmail.com"))
        .thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> userService.getByEmail("abc@gmail.com"));
    }
    @Test
    void shouldReturnAllUser(){
        User user = new User();
         user.setName("kshitij");
        user.setEmail("abc@gmail.com");
        user.setId(1l);
        user.setRole(Role.CUSTOMER);
        user.setCreatedAt(null);

        User user2 = new User();
        user2.setName("anshu");
        user2.setEmail("def@gmail.com");
        user2.setId(2l);
        user2.setRole(Role.CUSTOMER);
        user2.setCreatedAt(null);
        
        Page<User> userPage = new PageImpl<>(List.of(user, user2));


        when(userRepository.findAll(any(Pageable.class)))
        .thenReturn(userPage);
        Page<UserResponseDto> response = userService.getAllUser(0, 10, null, null);
        assertEquals(2, response.getTotalElements());

        assertEquals("kshitij", response.getContent().get(0).getName());
         assertEquals("abc@gmail.com", response.getContent().get(0).getEmail());
          assertEquals(1l, response.getContent().get(0).getId());
           assertEquals(Role.CUSTOMER, response.getContent().get(0).getRole());
            assertNull(response.getContent().get(0).getCreatedAt());

        assertEquals("anshu", response.getContent().get(1).getName());
         assertEquals("def@gmail.com", response.getContent().get(1).getEmail());
          assertEquals(2l, response.getContent().get(1).getId());
           assertEquals(Role.CUSTOMER, response.getContent().get(1).getRole());
            assertNull(response.getContent().get(1).getCreatedAt());
            
    }
    @Test
    void updateUser_ShouldReturnUpdatedUser(){
        User user = new User();
        user.setName("kshitij");
        user.setEmail("abc@gmail.com");
        user.setId(1l);
        user.setRole(Role.CUSTOMER);
        user.setCreatedAt(null);

        User updatedUser = new User();
        updatedUser.setName("anshu");
        updatedUser.setEmail("def@gmail.com");
        updatedUser.setId(1l);
        updatedUser.setRole(Role.CUSTOMER);
        updatedUser.setCreatedAt(null);

        UpdateUserDto request = new UpdateUserDto();
        request.setEmail("def@gmail.com");
        request.setName("anshu");

        when(userRepository.findById(1l))
                .thenReturn(Optional.of(user));
        when(userRepository.findByEmail("def@gmail.com"))
                .thenReturn(Optional.empty());        
        when(userRepository.save(any(User.class)))
                .thenReturn(updatedUser);

        UserResponseDto response = userService.updatedUser(request,1l);

        assertEquals("anshu", response.getName());
        assertEquals("def@gmail.com", response.getEmail());
    }
    @Test
    void updateUser_ShouldThrowException_WhenUserNotFound(){
        User user = new User();
        user.setName("kshitij");
        user.setEmail("abc@gmail.com");
        user.setId(1l);
        user.setRole(Role.CUSTOMER);
        user.setCreatedAt(null);

        UpdateUserDto request = new UpdateUserDto();
      ;


        when(userRepository.findById(1l))
        .thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> userService.updatedUser(request, 1l));

    }
    @Test
void updateUser_ShouldThrowException_WhenEmailAlreadyExists() {

    User user = new User();
    user.setId(1L);
    user.setName("kshitij");
    user.setEmail("abc@gmail.com");
    user.setRole(Role.CUSTOMER);

    User existingUser = new User();
    existingUser.setId(2L);
    existingUser.setName("anshu");
    existingUser.setEmail("def@gmail.com");
    existingUser.setRole(Role.CUSTOMER);

    UpdateUserDto request = new UpdateUserDto();
    request.setName("kshitij");
    request.setEmail("def@gmail.com");

    when(userRepository.findById(1L))
            .thenReturn(Optional.of(user));

    when(userRepository.findByEmail("def@gmail.com"))
            .thenReturn(Optional.of(existingUser));

    assertThrows(
        UserAlreadyExistException.class,
        () -> userService.updatedUser(request, 1L)
    );
} 
   @Test 
   void findById_ShouldDelteUser(){
    
    User user = new User();
    user.setId(1L);
    user.setName("kshitij");
    user.setEmail("abc@gmail.com");
    user.setRole(Role.CUSTOMER);
    when(userRepository.findById(1L))
    .thenReturn(Optional.of(user));
    userService.deletedUser(1L);
    verify(userRepository).delete(user);
   }




}

