package com.example.auth.service;

import com.example.auth.model.User;
import com.example.auth.repository.GetUserByUserIdRepository;

/**
 * Service class responsible for retrieving user information by user ID.
 * This service acts as an intermediary between the controller and the repository layer.
 */
public class GetUserByUserIdService {

    private GetUserByUserIdRepository repository;

    /**
     * Constructs a new GetUserByUserIdService with the specified repository.
     *
     * @param repository The repository used to fetch user data
     */
    public GetUserByUserIdService(GetUserByUserIdRepository repository) {
        this.repository = repository;
    }

    /**
     * Retrieves a user by their unique user ID.
     *
     * @param userId The unique identifier of the user to retrieve
     * @return The user object corresponding to the provided ID
     * @throws Exception If the user cannot be found or any other error occurs during retrieval
     */
    public User getUserByUserId(String userId) throws Exception {
        return this.repository.getUserByUserId(userId);
    }
}
