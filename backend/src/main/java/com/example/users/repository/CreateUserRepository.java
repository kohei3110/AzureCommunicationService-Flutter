package com.example.users.repository;

import java.time.Duration;
import java.util.logging.Logger;

import com.azure.cosmos.CosmosAsyncClient;
import com.azure.cosmos.CosmosAsyncContainer;
import com.azure.cosmos.CosmosAsyncDatabase;
import com.azure.cosmos.CosmosClientBuilder;
import com.azure.cosmos.DirectConnectionConfig;
import com.azure.cosmos.models.CosmosItemRequestOptions;
import com.azure.cosmos.models.CosmosItemResponse;
import com.azure.cosmos.models.PartitionKey;
import com.example.users.model.User;

import reactor.core.publisher.Mono;

/**
 * Repository class for creating user records in Azure Cosmos DB.
 * Handles the connection to Cosmos DB and provides methods to create new user entries.
 */
public class CreateUserRepository {

    /**
     * Async client for Azure Cosmos DB operations.
     */
    private CosmosAsyncClient cosmosAsyncClient;
    
    /**
     * Reference to the Cosmos DB database.
     */
    private CosmosAsyncDatabase cosmosAsyncDatabase;
    
    /**
     * Reference to the Cosmos DB container.
     */
    private CosmosAsyncContainer cosmosAsyncContainer;

    /**
     * The ID of the database in Cosmos DB.
     */
    private static final String DATABASE_ID = "Example";
    
    /**
     * The ID of the container in Cosmos DB.
     */
    private static final String CONTAINER_ID = "User";

    /**
     * Logger instance for this class.
     */
    Logger logger = Logger.getLogger(CreateUserRepository.class.getName());

    /**
     * Constructor for CreateUserRepository.
     * Initializes the connection to Azure Cosmos DB with the configured settings.
     * Reads Cosmos DB endpoint and key from environment variables.
     */
    public CreateUserRepository() {
        DirectConnectionConfig directConnectionConfig = DirectConnectionConfig.getDefaultConfig();
        directConnectionConfig.setMaxConnectionsPerEndpoint(120);
        directConnectionConfig.setIdleEndpointTimeout(Duration.ofMillis(10000));

        this.cosmosAsyncClient = new CosmosClientBuilder()
                .endpoint(System.getenv("COSMOSDB_ENDPOINT"))
                .key(System.getenv("COSMOSDB_KEY"))
                .contentResponseOnWriteEnabled(true)
                .directMode(directConnectionConfig)
                .buildAsyncClient();
        this.cosmosAsyncDatabase = this.cosmosAsyncClient.getDatabase(DATABASE_ID);
        this.cosmosAsyncContainer = this.cosmosAsyncDatabase
                .getContainer(CONTAINER_ID);
    }

    /**
     * Creates a new user item in the Cosmos DB container.
     *
     * @param user The user object to be created in the database
     * @return The created User object with any server-side modifications
     * @throws Exception If the create operation fails
     */
    public User createItemAsync(User user) throws Exception {
        try {
            Mono<CosmosItemResponse<User>> response = cosmosAsyncContainer.createItem(user,
                    new PartitionKey(user.getUserId()), new CosmosItemRequestOptions());
            return response.block().getItem();
        } catch (Exception e) {
            logger.warning(e.getMessage());
            throw new Exception("create user operation has failed");
        }
    }
}
