package com.military.app.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.military.app.dto.UpdateUserRequest;
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

    // ================= AUTH MODULE =================

    @Override
    public User createUser(UserRequest request) {

        Role role = roleRepository.findById(request.getRoleId())
                .orElseThrow(() -> new ResourceNotFoundException("Role not found"));

        if (request.getUnit() == null || request.getUnit().isBlank()) {
            throw new IllegalArgumentException("Unit is mandatory");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRankName(request.getRankName());
        user.setUnit(request.getUnit());
        user.setRole(role);
        user.setActive(true);

        return userRepository.save(user);
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

        return mapToProfile(user);
    }

    @Override
    public void changePassword(String username, String oldPassword, String newPassword) {

        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new ResourceNotFoundException("User not found");
        }

        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new UnauthorizedException("Old password is incorrect");
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

    // ================= USER MANAGEMENT MODULE =================

    @Override
    public List<UserProfileResponse> getAllUserProfiles() {
        return userRepository.findAll()
                .stream()
                .map(this::mapToProfile)
                .collect(Collectors.toList());
    }

    @Override
    public UserProfileResponse getUserProfileById(Long id) {
        User user = getUserById(id);
        return mapToProfile(user);
    }

    @Override
    public void updateUser(Long id, UpdateUserRequest request) {

        User user = getUserById(id);

        if (request.getRankName() != null) {
            user.setRankName(request.getRankName());
        }

        if (request.getUnit() != null) {
            user.setUnit(request.getUnit());
        }

        if (request.getActive() != null) {
            user.setActive(request.getActive());
        }

        if (request.getRoleId() != null) {
            Role role = roleRepository.findById(request.getRoleId())
                    .orElseThrow(() -> new ResourceNotFoundException("Role not found"));
            user.setRole(role);
        }

        userRepository.save(user);
    }

    @Override
    public void deleteUser(Long id) {
        User user = getUserById(id);
        userRepository.delete(user);
    }

    @Override
    public List<UserProfileResponse> getUsersByRank(String rank) {
        return userRepository.findByRankNameIgnoreCase(rank)
                .stream()
                .map(this::mapToProfile)
                .collect(Collectors.toList());
    }

    // ================= INTERNAL USE ONLY =================

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    // ================= MAPPER =================

    private UserProfileResponse mapToProfile(User user) {
        UserProfileResponse dto = new UserProfileResponse();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setRole(user.getRole().getName());
        dto.setRankName(user.getRankName());
        dto.setUnit(user.getUnit());   // ✅ added
        dto.setActive(user.isActive());
        return dto;
    }

    
    @Override
    public List<UserProfileResponse> getUsersByUnit(String unit) {
        return userRepository.findByUnitIgnoreCase(unit)
                .stream()
                .map(this::mapToProfile)
                .toList();
    }

 

}
