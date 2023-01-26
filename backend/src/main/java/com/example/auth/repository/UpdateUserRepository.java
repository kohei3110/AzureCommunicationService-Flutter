package com.example.auth.repository;

import java.util.logging.Logger;

import com.azure.cosmos.CosmosAsyncClient;
import com.azure.cosmos.CosmosAsyncContainer;
import com.azure.cosmos.CosmosAsyncDatabase;
import com.azure.cosmos.CosmosClientBuilder;
import com.azure.cosmos.models.CosmosItemResponse;
import com.azure.cosmos.models.CosmosPatchOperations;
import com.azure.cosmos.models.PartitionKey;
import com.example.auth.model.User;

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

    public User updateUser(String userId, String acsUserId) {
        CosmosItemResponse<User> response = this.cosmosAsyncContainer.patchItem(userId, new PartitionKey(userId),
                CosmosPatchOperations.create().add("/acsUserId", acsUserId), User.class).block();
        return response.getItem();
    }

}
