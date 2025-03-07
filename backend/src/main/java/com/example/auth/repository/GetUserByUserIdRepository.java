package com.example.auth.repository;

import java.util.logging.Logger;

import com.azure.cosmos.CosmosAsyncClient;
import com.azure.cosmos.CosmosAsyncContainer;
import com.azure.cosmos.CosmosAsyncDatabase;
import com.azure.cosmos.CosmosClientBuilder;
import com.azure.cosmos.models.CosmosItemResponse;
import com.azure.cosmos.models.PartitionKey;
import com.example.auth.model.User;

import reactor.core.publisher.Mono;

/**
 * Repository class responsible for retrieving user data from Azure Cosmos DB
 * by user ID.
 * 
 * This class establishes a connection to the Cosmos DB and provides methods to
 * query user information.
 */
public class GetUserByUserIdRepository {

    /** Asynchronous client for Azure Cosmos DB operations */
    private CosmosAsyncClient cosmosAsyncClient;
    
    /** Reference to the Azure Cosmos DB database */
    private CosmosAsyncDatabase cosmosAsyncDatabase;
    
    /** Reference to the Azure Cosmos DB container that stores user data */
    private CosmosAsyncContainer cosmosAsyncContainer;

    /** The ID of the Cosmos DB database */
    private static final String DATABASE_ID = "Example";
    
    /** The ID of the Cosmos DB container where user data is stored */
    private static final String CONTAINER_ID = "User";

    /** Logger for tracking repository operations */
    Logger logger = Logger.getLogger(GetUserByUserIdRepository.class.getName());

    /**
     * Constructor that initializes the Cosmos DB client and establishes connections
     * to the database and container.
     * 
     * This constructor fetches database connection parameters from environment variables.
     */
    public GetUserByUserIdRepository() {
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
     * Retrieves a user by their unique user ID from the database.
     * 
     * @param userId The unique identifier of the user to retrieve
     * @return User object containing user information
     * @throws Exception If the user cannot be found or if the database operation fails
     */
    public User getUserByUserId(String userId) throws Exception {
        try {
            Mono<CosmosItemResponse<User>> response = cosmosAsyncContainer.readItem(userId,
                    new PartitionKey(userId),
                    User.class);
            return response.block()
                    .getItem();
        } catch (Exception e) {
            logger.warning(e.getMessage());
            throw new Exception("get user operation has failed");
        }
    }

}
