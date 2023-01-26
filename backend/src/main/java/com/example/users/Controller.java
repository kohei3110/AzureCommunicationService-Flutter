package com.example.users;

import java.util.*;
import com.microsoft.azure.functions.annotation.*;
import com.example.users.model.User;
import com.example.users.service.CreateUserService;
import com.example.users.service.UpdateUserService;
import com.microsoft.azure.functions.*;

/**
 * Azure Functions with HTTP Trigger.
 */
public class Controller {

    Factory factory = new Factory();

    @FunctionName("CreateUser")
    public User createUser(
            @HttpTrigger(name = "req", methods = {
                    HttpMethod.POST }, authLevel = AuthorizationLevel.ANONYMOUS, route = "users") HttpRequestMessage<Optional<String>> request,
            final ExecutionContext context) throws Exception {
        CreateUserService createUserService = factory.injectCreateUserService();
        try {
            return createUserService.requestCosmosDBAsync(request.getBody());
        } catch (Exception e) {
            context.getLogger().warning(e.getMessage());
            throw new Exception("create user operation has failed");
        }
    }

    @FunctionName("UpdateUser")
    public User updateUser(
            @HttpTrigger(name = "req", methods = {
                    HttpMethod.PUT }, authLevel = AuthorizationLevel.ANONYMOUS, route = "users") HttpRequestMessage<Optional<String>> request,
            final ExecutionContext context) throws Exception {
        UpdateUserService updateUserService = factory.injectUpdateUserService();
        try {
            return updateUserService.requestCosmosDBAsync(request.getBody());
        } catch (Exception e) {
            context.getLogger().warning(e.getMessage());
            throw new Exception("create user operation has failed");
        }
    }

}
