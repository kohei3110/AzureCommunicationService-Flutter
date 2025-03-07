package com.example.users.service;

import java.util.Optional;
import java.util.logging.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.users.model.User;
import com.example.users.repository.UpdateUserRepository;

/**
 * Service responsible for handling user update operations.
 * This class processes request data and interacts with the database
 * to update user information.
 */
public class UpdateUserService {

    /**
     * Repository used for database operations related to user updates.
     */
    private UpdateUserRepository repository;

    /**
     * Constructs a new UpdateUserService with the specified repository.
     *
     * @param repository The repository to use for user update operations
     */
    public UpdateUserService(UpdateUserRepository repository) {
        this.repository = repository;
    }

    /**
     * Object mapper for JSON serialization and deserialization.
     */
    ObjectMapper mapper = new ObjectMapper();
    
    /**
     * Logger for this class.
     */
    Logger logger = Logger.getLogger(UpdateUserService.class.getName());

    /**
     * Processes a request to update a user in the database.
     *
     * @param requestBody The optional JSON string containing user data
     * @return The updated user object
     * @throws Exception If the request is invalid or an error occurs during update
     */
    public User requestCosmosDBAsync(Optional<String> requestBody) throws Exception {
        String body = requestBody.get();
        User user = parseRequestBody(body);
        return repository.updateItemAsync(user);
    }

    /**
     * Parses a JSON string into a User object.
     *
     * @param body The JSON string to parse
     * @return The parsed User object
     * @throws Exception If the JSON cannot be parsed into a valid User object
     */
    private User parseRequestBody(String body) throws Exception {
        try {
            User user = mapper.readValue(body, User.class);
            return user;
        } catch (Exception e) {
            logger.warning(e.getMessage());
            throw new Exception("Invalid parameters");
        }
    }
}
