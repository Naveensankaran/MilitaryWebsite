package com.military.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.military.app.entity.LoginAttempt;

public interface LoginAttemptRepository
        extends JpaRepository<LoginAttempt, Long> {
}
