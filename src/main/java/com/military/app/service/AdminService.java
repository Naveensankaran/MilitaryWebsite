package com.military.app.service;

import com.military.app.dto.AdminDashboardResponse;

public interface AdminService {
    AdminDashboardResponse getDashboardData();
    
    void disableUser(Long userId, String adminUsername);

    void enableUser(Long userId, String adminUsername);
}
