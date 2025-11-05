package com.example.demo.integration;

import com.example.demo.dto.SignupRequest;
import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.JwtUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Set;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class RoleBasedSecurityIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    private SignupRequest userSignupRequest;

    @BeforeEach
    void setUp() {
        // Create regular user
        userSignupRequest = new SignupRequest();
        userSignupRequest.setUsername("user");
        userSignupRequest.setEmail("user@example.com");
        userSignupRequest.setPassword("user123");
        // No roles set - should get ROLE_USER by default
    }

    @AfterEach
    void cleanup() {
        userRepository.deleteAll();
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void adminEndpoints_WithAdminRole_Success() throws Exception {
        mockMvc.perform(get("/api/admin/users"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "MODERATOR")
    void modEndpoints_WithModeratorRole_Success() throws Exception {
        mockMvc.perform(get("/api/mod/reports"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void modEndpoints_WithAdminRole_Success() throws Exception {
        mockMvc.perform(get("/api/mod/reports"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "USER")
    void adminEndpoints_WithUserRole_Forbidden() throws Exception {
        mockMvc.perform(get("/api/admin/users"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "USER")
    void modEndpoints_WithUserRole_Forbidden() throws Exception {
        mockMvc.perform(get("/api/mod/reports"))
                .andExpect(status().isForbidden());
    }

    @Test
    void defaultRoleAssignment_ForNewUser() throws Exception {
        // Register a user without specifying roles
        User user = userService.registerUser(userSignupRequest);
        
        // Verify that the user has ROLE_USER
        org.junit.jupiter.api.Assertions.assertTrue(
            user.getRoles().contains(Role.ROLE_USER),
            "New user should have ROLE_USER by default"
        );
        
        org.junit.jupiter.api.Assertions.assertEquals(
            1,
            user.getRoles().size(),
            "New user should only have one role"
        );
    }
}