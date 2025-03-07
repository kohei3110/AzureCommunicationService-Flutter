package com.example.users.repository;

import java.util.logging.Logger;

import com.azure.cosmos.CosmosAsyncClient;
import com.azure.cosmos.CosmosAsyncContainer;
import com.azure.cosmos.CosmosAsyncDatabase;
import com.azure.cosmos.CosmosClientBuilder;
import com.example.users.model.User;

import reactor.core.publisher.Mono;

/**
 * Repository class for updating user data in Azure Cosmos DB.
 * This class provides functionality to connect to Cosmos DB and update user information.
 */
public class UpdateUserRepository {

    private CosmosAsyncClient cosmosAsyncClient;
    private CosmosAsyncDatabase cosmosAsyncDatabase;
    private CosmosAsyncContainer cosmosAsyncContainer;

    private static final String DATABASE_ID = "Example";
    private static final String CONTAINER_ID = "User";

    Logger logger = Logger.getLogger(UpdateUserRepository.class.getName());

    /**
     * Constructor that initializes the connection to Azure Cosmos DB.
     * Creates a Cosmos client with environment-based configuration and connects to the database and container.
     */
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

    /**
     * Updates or inserts a user document in the Cosmos DB container.
     * This method performs an upsert operation which creates a new document if it doesn't exist,
     * or replaces it if it already exists.
     *
     * @param user The User object containing the updated user information
     * @return The User object that was updated in the database
     */
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
