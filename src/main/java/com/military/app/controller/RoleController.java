package com.military.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.military.app.entity.Role;
import com.military.app.repository.RoleRepository;

@RestController
@RequestMapping("/api/roles")
public class RoleController {

    @Autowired
    private RoleRepository roleRepository;

    // GET ALL ROLES
    @GetMapping
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }
}
