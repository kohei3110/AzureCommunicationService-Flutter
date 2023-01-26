package com.example.auth.service;

import com.example.auth.model.User;
import com.example.auth.repository.GetUserByUserIdRepository;

public class GetUserByUserIdService {

    private GetUserByUserIdRepository repository;

    public GetUserByUserIdService(GetUserByUserIdRepository repository) {
        this.repository = repository;
    }

    public User getUserByUserId(String userId) throws Exception {
        return this.repository.getUserByUserId(userId);
    }
}
