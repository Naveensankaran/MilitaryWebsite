package com.military.app.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.military.app.dto.UpdateUserRequest;
import com.military.app.dto.UserProfileResponse;
import com.military.app.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<UserProfileResponse> getAllUsers() {
        return userService.getAllUserProfiles();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserProfileResponse> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserProfileById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateUser(
            @PathVariable Long id,
            @RequestBody UpdateUserRequest request) {

        userService.updateUser(id, request);
        return ResponseEntity.ok("Officer updated successfully");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok("Officer removed successfully");
    }

    @GetMapping("/rank/{rank}")
    public List<UserProfileResponse> getUsersByRank(@PathVariable String rank) {
        return userService.getUsersByRank(rank);
    }

    // ✅ NEW
    @GetMapping("/unit/{unit}")
    public List<UserProfileResponse> getUsersByUnit(@PathVariable String unit) {
        return userService.getUsersByUnit(unit);
    }
}
