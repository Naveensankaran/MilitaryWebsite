package com.military.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.military.app.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);

    List<User> findByRankNameIgnoreCase(String rankName);
}
