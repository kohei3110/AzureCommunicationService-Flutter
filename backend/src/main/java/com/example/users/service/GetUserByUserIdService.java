package com.example.users.service;

import com.example.users.model.User;
import com.example.users.repository.GetUserByUserIdRepository;

public class GetUserByUserIdService {

    private GetUserByUserIdRepository repository;

    public GetUserByUserIdService(GetUserByUserIdRepository repository) {
        this.repository = repository;
    }

    public User getUserByUserId(String userId) throws Exception {
        return this.repository.getUserByUserId(userId);
    }
}
