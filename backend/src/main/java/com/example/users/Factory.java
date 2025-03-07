package com.example.users;

import com.example.users.repository.CreateUserRepository;
import com.example.users.repository.GetUserByUserIdRepository;
import com.example.users.repository.UpdateUserRepository;
import com.example.users.service.CreateUserService;
import com.example.users.service.GetUserByUserIdService;
import com.example.users.service.UpdateUserService;

/**
 * Factory class responsible for creating and injecting user-related service instances.
 * This class implements a simple dependency injection pattern to provide services
 * with their required repository dependencies.
 */
public class Factory {

    /** Repository for creating new user records */
    private CreateUserRepository createUserRepository;
    
    /** Service for handling user creation logic */
    private CreateUserService createUserService;
    
    /** Repository for updating existing user information */
    private UpdateUserRepository updateUserRepository;
    
    /** Service for handling user update logic */
    private UpdateUserService updateUserService;
    
    /** Repository for retrieving user information by user ID */
    private GetUserByUserIdRepository getUserByUserIdRepository;
    
    /** Service for handling user retrieval logic by user ID */
    private GetUserByUserIdService getUserByUserIdService;

    /**
     * Initializes a new Factory instance.
     * Creates all necessary repositories and services with their dependencies.
     */
    public Factory() {
        this.createUserRepository = new CreateUserRepository();
        this.createUserService = new CreateUserService(this.createUserRepository);
        this.updateUserRepository = new UpdateUserRepository();
        this.updateUserService = new UpdateUserService(this.updateUserRepository);
        this.getUserByUserIdRepository = new GetUserByUserIdRepository();
        this.getUserByUserIdService = new GetUserByUserIdService(this.getUserByUserIdRepository);
    }

    /**
     * Provides access to the CreateUserService instance.
     *
     * @return The CreateUserService instance for user creation operations
     */
    public CreateUserService injectCreateUserService() {
        return this.createUserService;
    }

    /**
     * Provides access to the UpdateUserService instance.
     *
     * @return The UpdateUserService instance for user update operations
     */
    public UpdateUserService injectUpdateUserService() {
        return this.updateUserService;
    }

    /**
     * Provides access to the GetUserByUserIdService instance.
     *
     * @return The GetUserByUserIdService instance for retrieving user information by ID
     */
    public GetUserByUserIdService injectGetUserByUserIdService() {
        return this.getUserByUserIdService;
    }
}
