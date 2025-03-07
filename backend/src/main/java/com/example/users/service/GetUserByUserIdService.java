package com.example.users.service;

import com.example.users.model.User;
import com.example.users.repository.GetUserByUserIdRepository;

/**
 * Service class responsible for retrieving user information by user ID.
 * This class acts as an intermediary between the controller and repository layers.
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
     * @param userId The unique identifier of the user to be retrieved
     * @return The User object containing user information
     * @throws Exception If the user cannot be found or if there's an error during retrieval
     */
    public User getUserByUserId(String userId) throws Exception {
        return this.repository.getUserByUserId(userId);
    }
}
