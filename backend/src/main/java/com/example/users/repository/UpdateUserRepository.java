package com.example.users.repository;

import java.util.logging.Logger;

import com.azure.cosmos.CosmosAsyncClient;
import com.azure.cosmos.CosmosAsyncContainer;
import com.azure.cosmos.CosmosAsyncDatabase;
import com.azure.cosmos.CosmosClientBuilder;
import com.example.users.model.User;

import reactor.core.publisher.Mono;

public class UpdateUserRepository {

    private CosmosAsyncClient cosmosAsyncClient;
    private CosmosAsyncDatabase cosmosAsyncDatabase;
    private CosmosAsyncContainer cosmosAsyncContainer;

    private static final String DATABASE_ID = "Example";
    private static final String CONTAINER_ID = "User";

    Logger logger = Logger.getLogger(UpdateUserRepository.class.getName());

    public UpdateUserRepository() {
        this.cosmosAsyncClient = new CosmosClientBuilder()
                .endpoint(System.getenv("COSMOSDB_ENDPOINT"))
                .key(System.getenv("COSMOSDB_KEY"))
                .contentResponseOnWriteEnabled(true)
                .buildAsyncClient();
        this.cosmosAsyncDatabase = this.cosmosAsyncClient.getDatabase(DATABASE_ID);
        this.cosmosAsyncContainer = this.cosmosAsyncDatabase
                .getContainer(CONTAINER_ID);
    }

    public User updateItemAsync(User user) {
        Mono.just(user)
                .flatMap(data -> {
                    this.cosmosAsyncContainer.upsertItem(user).block();
                    return Mono.just(data);
                })
                .subscribe();
        return user;
    }
}
