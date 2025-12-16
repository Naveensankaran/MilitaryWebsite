package com.military.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.military.app.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
