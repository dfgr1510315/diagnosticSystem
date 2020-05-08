package com.ljz.diagnostic_system.service;

import com.ljz.diagnostic_system.model.UserProfile;

public interface UmsAdminService {

    UserProfile login(String email,String password);

    int register(UserProfile userProfile);

    UserProfile loadUserByEmail(String email);

    UserProfile loadUserById(String id);

    int newUsername(String id,String newName);

    int newPassword(String id,String newPassword);
}
