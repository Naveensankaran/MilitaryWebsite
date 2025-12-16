package com.military.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.military.app.dto.ChangePasswordRequest;
import com.military.app.dto.LoginRequest;
import com.military.app.dto.LoginResponse;
import com.military.app.dto.UserProfileResponse;
import com.military.app.dto.UserRequest;
import com.military.app.entity.User;
import com.military.app.service.UserService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // REGISTER
    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody UserRequest request) {
        User user = userService.createUser(request);
        return ResponseEntity.ok(user);
    }

    // LOGIN
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {

        User user = userService.findByUsername(request.getUsername());

        if (user == null) {
            return ResponseEntity.status(401)
                    .body(new LoginResponse("Invalid username!", null));
        }

        // ✅ CORRECT password validation
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            return ResponseEntity.status(401)
                    .body(new LoginResponse("Invalid password!", null));
        }

        return ResponseEntity.ok(
                new LoginResponse("Login successful!", user.getId())
        );
    }
    
    @GetMapping("/profile")
    public ResponseEntity<UserProfileResponse> getProfile(
            Authentication authentication) {

        return ResponseEntity.ok(
                userService.getCurrentUserProfile(authentication.getName())
        );
    }
    
    @PostMapping("/logout")
    public ResponseEntity<String> logout(Authentication authentication) {

        String username = authentication.getName();

        // Optional: log audit
        // auditService.logAction(userId, "LOGOUT");

        return ResponseEntity.ok("Logout successful");
    }
    
    @PutMapping("/change-password")
    public ResponseEntity<String> changePassword(
            @RequestBody ChangePasswordRequest request,
            Authentication authentication) {

        userService.changePassword(
                authentication.getName(),
                request.getOldPassword(),
                request.getNewPassword()
        );

        return ResponseEntity.ok("Password changed successfully");
    }



}
