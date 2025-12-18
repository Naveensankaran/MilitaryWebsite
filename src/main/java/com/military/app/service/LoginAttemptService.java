package com.military.app.service;

public interface LoginAttemptService {

    void logAttempt(String username, boolean success, String ipAddress);
}
