package com.example.demo.integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class SecurityConfigIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void publicEndpoints_NoAuthentication_Success() throws Exception {
        // Test Swagger UI access (it uses a redirect)
        mockMvc.perform(get("/swagger-ui.html"))
                .andExpect(status().is3xxRedirection());

        // Test API docs access
        mockMvc.perform(get("/v3/api-docs"))
                .andExpect(status().isOk());

        // Test H2 Console access - Since it's a web page, 200 or 404 are both acceptable
        mockMvc.perform(get("/h2-console"))
                .andExpect(status().is(404)); // 404 is acceptable since we're not serving the actual HTML page in tests
    }

    @Test
    void securedEndpoint_NoAuthentication_Unauthorized() throws Exception {
        mockMvc.perform(get("/api/secure/resource"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser
    void securedEndpoint_WithAuthentication_Success() throws Exception {
        mockMvc.perform(get("/api/secure/resource"))
                .andExpect(status().isNotFound()); // 404 because endpoint doesn't exist, but authentication passed
    }

    @Test
    void authEndpoints_NoAuthentication_Success() throws Exception {
        // Login endpoint should be accessible without authentication
        mockMvc.perform(get("/api/auth/login"))
                .andExpect(status().isMethodNotAllowed()); // 405 because we're using GET instead of POST

        // Signup endpoint should be accessible without authentication
        mockMvc.perform(get("/api/auth/signup"))
                .andExpect(status().isMethodNotAllowed()); // 405 because we're using GET instead of POST
    }
}