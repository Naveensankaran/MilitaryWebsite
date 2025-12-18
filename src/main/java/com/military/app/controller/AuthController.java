package com.military.app.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.military.app.dto.ChangePasswordRequest;
import com.military.app.dto.LoginRequest;
import com.military.app.dto.LoginResponse;
import com.military.app.dto.UserProfileResponse;
import com.military.app.dto.UserRequest;
import com.military.app.entity.User;
import com.military.app.service.AuditService;
import com.military.app.service.UserService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final AuditService auditService;
    private final AuthenticationManager authenticationManager;

    public AuthController(UserService userService,
                          PasswordEncoder passwordEncoder,
                          AuditService auditService,
                          AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.auditService = auditService;
        this.authenticationManager = authenticationManager;
    }

    // ---------------- REGISTER ----------------
    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody UserRequest request) {

        User user = userService.createUser(request);
        auditService.logUserAction(user.getId(), "REGISTER");

        return ResponseEntity.ok(user);
    }

    // ---------------- LOGIN ----------------
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {

        try {
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);

            User user = userService.findByUsername(request.getUsername());
            auditService.logUserAction(user.getId(), "LOGIN");

            return ResponseEntity.ok(
                    new LoginResponse("Login successful!", user.getId())
            );

        } catch (Exception ex) {

            auditService.logUserAction(
                    null,
                    "FAILED LOGIN for username: " + request.getUsername()
            );

            return ResponseEntity.status(401)
                    .body(new LoginResponse("Invalid username or password", null));
        }
    }

    // ---------------- PROFILE ----------------
    @GetMapping("/profile")
    public ResponseEntity<UserProfileResponse> getProfile(Authentication authentication) {

        return ResponseEntity.ok(
                userService.getCurrentUserProfile(authentication.getName())
        );
    }

    // ---------------- LOGOUT ----------------
    @PostMapping("/logout")
    public ResponseEntity<String> logout(Authentication authentication) {

        User user = userService.findByUsername(authentication.getName());
        auditService.logUserAction(user.getId(), "LOGOUT");

        SecurityContextHolder.clearContext();

        return ResponseEntity.ok("Logout successful");
    }

    // ---------------- CHANGE PASSWORD ----------------
    @PutMapping("/change-password")
    public ResponseEntity<String> changePassword(
            @RequestBody ChangePasswordRequest request,
            Authentication authentication) {

        User user = userService.findByUsername(authentication.getName());

        userService.changePassword(
                authentication.getName(),
                request.getOldPassword(),
                request.getNewPassword()
        );

        auditService.logUserAction(user.getId(), "CHANGE PASSWORD");

        return ResponseEntity.ok("Password changed successfully");
    }
}
