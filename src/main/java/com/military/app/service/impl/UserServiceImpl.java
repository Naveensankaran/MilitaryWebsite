package com.military.app.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.military.app.dto.UserProfileResponse;
import com.military.app.dto.UserRequest;
import com.military.app.entity.Role;
import com.military.app.entity.User;
import com.military.app.exception.ResourceNotFoundException;
import com.military.app.exception.UnauthorizedException;
import com.military.app.repository.RoleRepository;
import com.military.app.repository.UserRepository;
import com.military.app.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User createUser(UserRequest request) {

        Role role = roleRepository.findById(request.getRoleId())
                .orElseThrow(() -> new ResourceNotFoundException("Role not found"));

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRankName(request.getRankName());
        user.setActive(true);
        user.setRole(role);

        return userRepository.save(user);
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
    
    @Override
    public UserProfileResponse getCurrentUserProfile(String username) {

        User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new ResourceNotFoundException("User not found");
        }

        UserProfileResponse response = new UserProfileResponse();
        response.setId(user.getId());
        response.setUsername(user.getUsername());
        response.setRole(user.getRole().getName());
        response.setRankName(user.getRankName());
        response.setActive(user.isActive());

        return response;
    }
    
    @Override
    public void changePassword(String username, String oldPassword, String newPassword) {

        User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new ResourceNotFoundException("User not found");
        }

        // 🔐 Verify old password
        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new UnauthorizedException("Old password is incorrect");
        }

        // 🔐 Encrypt and update new password
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }


}
