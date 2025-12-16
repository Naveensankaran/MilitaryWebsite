package com.military.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.military.app.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
