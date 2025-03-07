package com.example.auth;

import com.example.auth.repository.GetUserByUserIdRepository;
import com.example.auth.repository.UpdateUserRepository;
import com.example.auth.service.AuthorizeUserService;
import com.example.auth.service.CreateAcsUserService;
import com.example.auth.service.GetUserByUserIdService;
import com.example.auth.service.UpdateUserService;

/**
 * Factory class for creating and providing service instances.
 * This class implements the Factory pattern to centralize the creation
 * of service objects and their dependencies.
 */
public class Factory {

    /**
     * Service for user authorization operations.
     */
    private AuthorizeUserService authorizeUserService;
    
    /**
     * Repository for retrieving user data by user ID.
     */
    private GetUserByUserIdRepository getUserByUserIdRepository;
    
    /**
     * Service for retrieving user data by user ID.
     */
    private GetUserByUserIdService getUserByUserIdService;
    
    /**
     * Service for creating Azure Communication Services users.
     */
    private CreateAcsUserService createAcsUserService;
    
    /**
     * Repository for updating user data.
     */
    private UpdateUserRepository updateUserRepository;
    
    /**
     * Service for updating user data.
     */
    private UpdateUserService updateUserService;

    /**
     * Constructs a new Factory instance.
     * Initializes all service and repository instances.
     */
    public Factory() {
        this.authorizeUserService = new AuthorizeUserService();
        this.getUserByUserIdRepository = new GetUserByUserIdRepository();
        this.getUserByUserIdService = new GetUserByUserIdService(this.getUserByUserIdRepository);
        this.createAcsUserService = new CreateAcsUserService();
        this.updateUserRepository = new UpdateUserRepository();
        this.updateUserService = new UpdateUserService(this.updateUserRepository);
    }

    /**
     * Provides the AuthorizeUserService instance.
     *
     * @return The AuthorizeUserService instance.
     */
    public AuthorizeUserService injectAuthorizeUserService() {
        return this.authorizeUserService;
    }

    /**
     * Provides the GetUserByUserIdService instance.
     *
     * @return The GetUserByUserIdService instance.
     */
    public GetUserByUserIdService injectGetUserByUserIdService() {
        return this.getUserByUserIdService;
    }

    /**
     * Provides the CreateAcsUserService instance.
     *
     * @return The CreateAcsUserService instance.
     */
    public CreateAcsUserService injectCreateAcsUserService() {
        return this.createAcsUserService;
    }

    /**
     * Provides the UpdateUserService instance.
     *
     * @return The UpdateUserService instance.
     */
    public UpdateUserService injectUpdateUserService() {
        return this.updateUserService;
    }
}
