package com.kshitij.It_Support.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import com.kshitij.It_Support.repository.UserRepository;
import com.kshitij.It_Support.entities.User;
import com.kshitij.It_Support.entities.Role;

@Component 
public class AdminBootStrap implements CommandLineRunner {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    public AdminBootStrap(UserRepository userRepository , PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    } 
    @Override 
    public void run(String... args){
        if(userRepository.existsByRole(Role.ADMIN)){
            return;
        }
        String email = System.getenv("ADMIN_EMAIL");
        String password = System.getenv("ADMIN_PASSWORD");
        String name = System.getenv().getOrDefault("ADMIN_NAME", "Admin");
        if(email==null || email.isBlank() || password == null || password.isBlank()){
            throw new IllegalStateException("No ADMIN exists and ADMIN bootstrap environment variables are missing") ;
               

        }
        if(userRepository.findByEmail(email).isPresent()){
            throw new IllegalStateException("ADMIN_EMAIL Already used by other ");
        }
        User admin = new User();
        admin.setEmail(email);
        admin.setPassword(passwordEncoder.encode(password));
        admin.setRole(Role.ADMIN);
        admin.setName(name);
        userRepository.save(admin);
        System.out.println("admin created successfully");
    }
    
}
