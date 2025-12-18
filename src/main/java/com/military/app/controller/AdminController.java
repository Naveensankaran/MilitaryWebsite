package com.military.app.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.military.app.dto.AdminDashboardResponse;
import com.military.app.service.AdminService;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    // ✅ GET /api/admin/dashboard
    @GetMapping("/dashboard")
    public ResponseEntity<AdminDashboardResponse> getDashboard() {
        return ResponseEntity.ok(
                adminService.getDashboardData()
        );
    }
    
    @PutMapping("/disable-user/{id}")
    public ResponseEntity<String> disableUser(
            @PathVariable Long id,
            Authentication authentication) {

        String adminUsername = authentication.getName();

        adminService.disableUser(id, adminUsername);

        return ResponseEntity.ok("Officer disabled successfully");
    }

    
    @PutMapping("/enable-user/{id}")
    public ResponseEntity<String> enableUser(
            @PathVariable Long id,
            Authentication authentication) {

        adminService.enableUser(id, authentication.getName());

        return ResponseEntity.ok("Officer enabled successfully");
    }
}
