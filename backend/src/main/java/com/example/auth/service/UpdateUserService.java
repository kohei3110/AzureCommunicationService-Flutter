package com.example.auth.service;

import com.example.auth.model.User;
import com.example.auth.repository.UpdateUserRepository;

public class UpdateUserService {

    private UpdateUserRepository repository;

    public UpdateUserService(UpdateUserRepository repository) {
        this.repository = repository;
    }

    public User updateUser(String userId, String acsUserId) {
        return this.repository.updateUser(userId, acsUserId);
    }

}
