package com.example.users;

import com.example.users.repository.CreateUserRepository;
import com.example.users.repository.GetUserByUserIdRepository;
import com.example.users.repository.UpdateUserRepository;
import com.example.users.service.CreateUserService;
import com.example.users.service.GetUserByUserIdService;
import com.example.users.service.UpdateUserService;

public class Factory {

    private CreateUserRepository createUserRepository;
    private CreateUserService createUserService;
    private UpdateUserRepository updateUserRepository;
    private UpdateUserService updateUserService;
    private GetUserByUserIdRepository getUserByUserIdRepository;
    private GetUserByUserIdService getUserByUserIdService;

    public Factory() {
        this.createUserRepository = new CreateUserRepository();
        this.createUserService = new CreateUserService(this.createUserRepository);
        this.updateUserRepository = new UpdateUserRepository();
        this.updateUserService = new UpdateUserService(this.updateUserRepository);
        this.getUserByUserIdRepository = new GetUserByUserIdRepository();
        this.getUserByUserIdService = new GetUserByUserIdService(this.getUserByUserIdRepository);
    }

    public CreateUserService injectCreateUserService() {
        return this.createUserService;
    }

    public UpdateUserService injectUpdateUserService() {
        return this.updateUserService;
    }

    public GetUserByUserIdService injectGetUserByUserIdService() {
        return this.getUserByUserIdService;
    }

}
