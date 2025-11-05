package com.example.demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/mod")
@PreAuthorize("hasAnyRole('MODERATOR', 'ADMIN')")
public class ModeratorController {

    @GetMapping("/reports")
    public ResponseEntity<String> getReports() {
        // This endpoint is accessible to moderators and admins
        return ResponseEntity.ok("Access to moderator panel granted");
    }

    @PostMapping("/content/review")
    public ResponseEntity<String> reviewContent() {
        // This endpoint is accessible to moderators and admins
        return ResponseEntity.ok("Content review completed");
    }
}