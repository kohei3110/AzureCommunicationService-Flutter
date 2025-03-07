package com.example.auth.service;

import com.example.auth.model.User;
import com.example.auth.repository.UpdateUserRepository;

/**
 * Service class responsible for user update operations.
 */
public class UpdateUserService {

    /**
     * Repository that handles the persistence operations for user updates.
     */
    private UpdateUserRepository repository;

    /**
     * Constructs an UpdateUserService with the specified repository.
     *
     * @param repository The repository to use for user update operations.
     */
    public UpdateUserService(UpdateUserRepository repository) {
        this.repository = repository;
    }

    /**
     * Updates a user with the provided Azure Communication Services user ID.
     *
     * @param userId The ID of the user to update.
     * @param acsUserId The Azure Communication Services user ID to assign to the user.
     * @return The updated User object.
     */
    public User updateUser(String userId, String acsUserId) {
        return this.repository.updateUser(userId, acsUserId);
    }

}
