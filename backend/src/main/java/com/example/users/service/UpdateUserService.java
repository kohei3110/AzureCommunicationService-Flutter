package com.example.users.service;

import java.util.Optional;
import java.util.logging.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.users.model.User;
import com.example.users.repository.UpdateUserRepository;

public class UpdateUserService {

    private UpdateUserRepository repository;

    public UpdateUserService(UpdateUserRepository repository) {
        this.repository = repository;
    }

    ObjectMapper mapper = new ObjectMapper();
    Logger logger = Logger.getLogger(UpdateUserService.class.getName());

    public User requestCosmosDBAsync(Optional<String> requestBody) throws Exception {
        String body = requestBody.get();
        User user = parseRequestBody(body);
        return repository.updateItemAsync(user);
    }

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
