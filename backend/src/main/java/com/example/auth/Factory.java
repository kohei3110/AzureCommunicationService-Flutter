package com.example.auth;

import com.example.auth.repository.GetUserByUserIdRepository;
import com.example.auth.repository.UpdateUserRepository;
import com.example.auth.service.AuthorizeUserService;
import com.example.auth.service.CreateAcsUserService;
import com.example.auth.service.GetUserByUserIdService;
import com.example.auth.service.UpdateUserService;

public class Factory {

    private AuthorizeUserService authorizeUserService;
    private GetUserByUserIdRepository getUserByUserIdRepository;
    private GetUserByUserIdService getUserByUserIdService;
    private CreateAcsUserService createAcsUserService;
    private UpdateUserRepository updateUserRepository;
    private UpdateUserService updateUserService;

    public Factory() {
        this.authorizeUserService = new AuthorizeUserService();
        this.getUserByUserIdRepository = new GetUserByUserIdRepository();
        this.getUserByUserIdService = new GetUserByUserIdService(this.getUserByUserIdRepository);
        this.createAcsUserService = new CreateAcsUserService();
        this.updateUserRepository = new UpdateUserRepository();
        this.updateUserService = new UpdateUserService(this.updateUserRepository);
    }

    public AuthorizeUserService injectAuthorizeUserService() {
        return this.authorizeUserService;
    }

    public GetUserByUserIdService injectGetUserByUserIdService() {
        return this.getUserByUserIdService;
    }

    public CreateAcsUserService injectCreateAcsUserService() {
        return this.createAcsUserService;
    }

    public UpdateUserService injectUpdateUserService() {
        return this.updateUserService;
    }
}
