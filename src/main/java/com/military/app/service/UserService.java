package com.military.app.service;

import java.util.List;

import com.military.app.dto.UpdateUserRequest;
import com.military.app.dto.UserProfileResponse;
import com.military.app.dto.UserRequest;
import com.military.app.entity.User;

public interface UserService {

    // AUTH
    User createUser(UserRequest request);
    User findByUsername(String username);
    UserProfileResponse getCurrentUserProfile(String username);
    void changePassword(String username, String oldPassword, String newPassword);

    // USER MANAGEMENT
    List<UserProfileResponse> getAllUserProfiles();
    UserProfileResponse getUserProfileById(Long id);
    void updateUser(Long id, UpdateUserRequest request);
    void deleteUser(Long id);

    List<UserProfileResponse> getUsersByRank(String rank);
    List<UserProfileResponse> getUsersByUnit(String unit); // ✅ added

    User getUserById(Long id);
}
