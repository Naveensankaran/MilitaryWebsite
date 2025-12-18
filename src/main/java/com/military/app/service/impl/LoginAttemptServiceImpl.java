package com.military.app.service.impl;

import org.springframework.stereotype.Service;

import com.military.app.entity.LoginAttempt;
import com.military.app.repository.LoginAttemptRepository;
import com.military.app.service.LoginAttemptService;

@Service
public class LoginAttemptServiceImpl implements LoginAttemptService {

    private final LoginAttemptRepository repository;

    public LoginAttemptServiceImpl(LoginAttemptRepository repository) {
        this.repository = repository;
    }

    @Override
    public void logAttempt(String username,
                           boolean success,
                           String ipAddress) {

        LoginAttempt attempt = new LoginAttempt();
        attempt.setUsername(username);
        attempt.setSuccess(success);
        attempt.setIpAddress(ipAddress);

        repository.save(attempt);
    }
}
