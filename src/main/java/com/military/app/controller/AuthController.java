package com.military.app.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
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
import com.military.app.service.AuditService;
import com.military.app.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;
    private final AuditService auditService;

    public AuthController(UserService userService,
                          AuditService auditService) {
        this.userService = userService;
        this.auditService = auditService;
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody UserRequest request) {
        User user = userService.createUser(request);
        auditService.logUserAction(user.getId(), "REGISTER");
        return ResponseEntity.ok(user);
    }

    @GetMapping("/profile")
    public ResponseEntity<UserProfileResponse> profile(Authentication authentication) {
        return ResponseEntity.ok(
                userService.getCurrentUserProfile(authentication.getName())
        );
    }

    @PutMapping("/change-password")
    public ResponseEntity<String> changePassword(
            @RequestBody ChangePasswordRequest request,
            Authentication authentication) {

        String username = authentication.getName();

        userService.changePassword(
                username,
                request.getOldPassword(),
                request.getNewPassword()
        );

        auditService.logUserAction(
                userService.findByUsername(username).getId(),
                "CHANGE PASSWORD"
        );

        return ResponseEntity.ok("Password changed successfully");
    }

//    @PostMapping("/logout")
//    public ResponseEntity<String> logout(Authentication authentication) {
//        auditService.logUserAction(
//                userService.findByUsername(authentication.getName()).getId(),
//                "LOGOUT"
//        );
//        return ResponseEntity.ok("Logout successful");
//    }
}
