package com.example.demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    @GetMapping("/users")
    public ResponseEntity<String> getAllUsers() {
        // This endpoint is only accessible to admins
        return ResponseEntity.ok("Access to admin panel granted");
    }

    @PostMapping("/system/config")
    public ResponseEntity<String> updateSystemConfig() {
        // This endpoint is only accessible to admins
        return ResponseEntity.ok("System configuration updated");
    }
}