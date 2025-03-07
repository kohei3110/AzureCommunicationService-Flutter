package com.example.users.repository;

import java.util.logging.Logger;

import com.azure.cosmos.CosmosAsyncClient;
import com.azure.cosmos.CosmosAsyncContainer;
import com.azure.cosmos.CosmosAsyncDatabase;
import com.azure.cosmos.CosmosClientBuilder;
import com.azure.cosmos.models.CosmosItemResponse;
import com.azure.cosmos.models.PartitionKey;
import com.example.users.model.User;

import reactor.core.publisher.Mono;

/**
 * Repository class responsible for retrieving user data from Cosmos DB by user ID.
 * This class provides an interface to interact with the User container in the Cosmos DB database.
 */
public class GetUserByUserIdRepository {

    private CosmosAsyncClient cosmosAsyncClient;
    private CosmosAsyncDatabase cosmosAsyncDatabase;
    private CosmosAsyncContainer cosmosAsyncContainer;

    private static final String DATABASE_ID = "Example";
    private static final String CONTAINER_ID = "User";

    Logger logger = Logger.getLogger(GetUserByUserIdRepository.class.getName());

    /**
     * Initializes the repository by setting up the Cosmos DB client and container.
     * Connects to the database using endpoint and key values from environment variables.
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
     * Retrieves a user from the database by their user ID.
     *
     * @param userId The unique identifier of the user to retrieve
     * @return The User object if found
     * @throws Exception If the user cannot be found or if there's a database error
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
