package com.example.demo.integration;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserRepositoryIntegrationTest {

    @Autowired
    private UserRepository userRepository;

    private User user;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
        
        user = new User();
        user.setUsername("testuser");
        user.setEmail("test@example.com");
        user.setPassword("password123");
    }

    @Test
    void saveUser_Success() {
        User savedUser = userRepository.save(user);
        
        assertNotNull(savedUser.getId());
        assertEquals(user.getUsername(), savedUser.getUsername());
        assertEquals(user.getEmail(), savedUser.getEmail());
    }

    @Test
    void findByUsername_Success() {
        userRepository.save(user);
        
        User foundUser = userRepository.findByUsername("testuser").orElse(null);
        
        assertNotNull(foundUser);
        assertEquals(user.getEmail(), foundUser.getEmail());
    }

    @Test
    void findByEmail_Success() {
        userRepository.save(user);
        
        User foundUser = userRepository.findByEmail("test@example.com").orElse(null);
        
        assertNotNull(foundUser);
        assertEquals(user.getUsername(), foundUser.getUsername());
    }

    @Test
    void existsByUsername_ReturnsTrue() {
        userRepository.save(user);
        
        boolean exists = userRepository.existsByUsername("testuser");
        
        assertTrue(exists);
    }

    @Test
    void existsByEmail_ReturnsTrue() {
        userRepository.save(user);
        
        boolean exists = userRepository.existsByEmail("test@example.com");
        
        assertTrue(exists);
    }

    @Test
    void existsByUsername_ReturnsFalse() {
        boolean exists = userRepository.existsByUsername("nonexistent");
        
        assertFalse(exists);
    }

    @Test
    void existsByEmail_ReturnsFalse() {
        boolean exists = userRepository.existsByEmail("nonexistent@example.com");
        
        assertFalse(exists);
    }
}