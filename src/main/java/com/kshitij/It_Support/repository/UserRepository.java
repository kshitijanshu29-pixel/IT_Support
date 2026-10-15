package com.kshitij.It_Support.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kshitij.It_Support.entities.*;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
    boolean existsByRole(Role role);
    
}
