package com.example.demo.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@SpringBootTest
@AutoConfigureMockMvc
public class ModeratorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(roles = "MODERATOR")
    void modEndpoint_WithModeratorRole_Success() throws Exception {
        mockMvc.perform(get("/api/mod/reports"))
                .andExpect(status().isOk())
                .andExpect(content().string("Access to moderator panel granted"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void modEndpoint_WithAdminRole_Success() throws Exception {
        mockMvc.perform(get("/api/mod/reports"))
                .andExpect(status().isOk())
                .andExpect(content().string("Access to moderator panel granted"));
    }

    @Test
    @WithMockUser(roles = "MODERATOR")
    void contentReview_WithModeratorRole_Success() throws Exception {
        mockMvc.perform(post("/api/mod/content/review"))
                .andExpect(status().isOk())
                .andExpect(content().string("Content review completed"));
    }

    @Test
    @WithMockUser(roles = "USER")
    void modEndpoint_WithUserRole_Forbidden() throws Exception {
        mockMvc.perform(get("/api/mod/reports"))
                .andExpect(status().isForbidden());
    }

    @Test
    void modEndpoint_WithoutAuth_Unauthorized() throws Exception {
        mockMvc.perform(get("/api/mod/reports"))
                .andExpect(status().isUnauthorized());
    }
}