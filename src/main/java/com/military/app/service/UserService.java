package com.military.app.service;

import com.military.app.dto.UserProfileResponse;
import com.military.app.dto.UserRequest;
import com.military.app.entity.User;

public interface UserService {
    User createUser(UserRequest request);
    User getUserById(Long id);
    User findByUsername(String username);
    UserProfileResponse getCurrentUserProfile(String username);
    void changePassword(String username, String oldPassword, String newPassword);

}
