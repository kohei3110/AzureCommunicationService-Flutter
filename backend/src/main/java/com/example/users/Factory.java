package com.example.users;

import com.example.users.repository.CreateUserRepository;
import com.example.users.repository.UpdateUserRepository;
import com.example.users.service.CreateUserService;
import com.example.users.service.UpdateUserService;

public class Factory {

    private CreateUserRepository createUserRepository;
    private CreateUserService createUserService;
    private UpdateUserRepository updateUserRepository;
    private UpdateUserService updateUserService;

    public Factory() {
        this.createUserRepository = new CreateUserRepository();
        this.createUserService = new CreateUserService(this.createUserRepository);
        this.updateUserRepository = new UpdateUserRepository();
        this.updateUserService = new UpdateUserService(this.updateUserRepository);
    }

    public CreateUserService injectCreateUserService() {
        return this.createUserService;
    }

    public UpdateUserService injectUpdateUserService() {
        return this.updateUserService;
    }

}
