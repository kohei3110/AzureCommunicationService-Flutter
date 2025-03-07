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

/**
 * Repository class for updating user information in Azure Cosmos DB.
 * This class provides methods to update specific properties of a user record.
 */
public class UpdateUserRepository {
    /** Asynchronous client for Azure Cosmos DB operations. */
    private CosmosAsyncClient cosmosAsyncClient;
    
    /** Asynchronous database reference for Cosmos DB. */
    private CosmosAsyncDatabase cosmosAsyncDatabase;
    
    /** Asynchronous container reference for Cosmos DB. */
    private CosmosAsyncContainer cosmosAsyncContainer;

    /** The database identifier used for Cosmos DB connection. */
    private static final String DATABASE_ID = "Example";
    
    /** The container identifier where user data is stored. */
    private static final String CONTAINER_ID = "User";

    /** Logger for this repository. */
    Logger logger = Logger.getLogger(UpdateUserRepository.class.getName());

    /**
     * Constructor that initializes the connection to Cosmos DB.
     * Uses environment variables for endpoint and key configuration.
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
     * Updates a user's ACS user ID in the database.
     *
     * @param userId The unique identifier of the user to update
     * @param acsUserId The Azure Communication Services user ID to associate with the user
     * @return The updated User object
     */
    public User updateUser(String userId, String acsUserId) {
        CosmosItemResponse<User> response = this.cosmosAsyncContainer.patchItem(userId, new PartitionKey(userId),
                CosmosPatchOperations.create().add("/acsUserId", acsUserId), User.class).block();
        return response.getItem();
    }

}
