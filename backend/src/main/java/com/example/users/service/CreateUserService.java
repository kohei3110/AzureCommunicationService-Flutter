package com.example.users.service;

import java.util.Optional;
import java.util.logging.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.users.model.User;
import com.example.users.repository.CreateUserRepository;

/**
 * Service class responsible for creating new users in the system.
 * This class handles parsing user request data and interacting with the user repository.
 */
public class CreateUserService {

    /**
     * Repository instance used for persisting user data.
     */
    private CreateUserRepository repository;

    /**
     * Creates a new CreateUserService with the specified repository.
     * 
     * @param repository The repository implementation for user creation
     */
    public CreateUserService(CreateUserRepository repository) {
        this.repository = repository;
    }

    /**
     * JSON object mapper used for deserializing request bodies into User objects.
     */
    ObjectMapper mapper = new ObjectMapper();
    
    /**
     * Logger for recording service operations and errors.
     */
    Logger logger = Logger.getLogger(CreateUserService.class.getName());

    /**
     * Processes a user creation request by parsing the request body and persisting the user via repository.
     * 
     * @param requestBody Optional string containing the JSON request body
     * @return The created User object with persistence details
     * @throws Exception If the request body is invalid or if persistence fails
     */
    public User requestCosmosDBAsync(Optional<String> requestBody) throws Exception {
        if (requestBody == null || !requestBody.isPresent()) {
            throw new IllegalArgumentException("Request body cannot be null or empty");
        }

        String body = requestBody.get();
        if (body == null || body.trim().isEmpty()) {
            throw new IllegalArgumentException("Request body content cannot be null or empty");
        }

        User user = parseRequestBody(body);
        if (user.getUserId() == null || user.getFamilyName() == null || user.getGivenName() == null) {
            throw new IllegalArgumentException("Required fields (userId, familyName, givenName) cannot be null");
        }

        return repository.createItemAsync(user);
    }

    /**
     * Parses a JSON string into a User object.
     * 
     * @param body String containing the JSON representation of a user
     * @return Parsed User object
     * @throws Exception If parsing fails or the JSON is malformed
     */
    private User parseRequestBody(String body) throws Exception {
        try {
            User user = mapper.readValue(body, User.class);
            return user;
        } catch (Exception e) {
            logger.warning(e.getMessage());
            throw new IllegalArgumentException("Invalid request body format: " + e.getMessage());
        }
    }
}
