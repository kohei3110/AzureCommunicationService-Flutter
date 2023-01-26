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

public class CreateUserRepository {

    private CosmosAsyncClient cosmosAsyncClient;
    private CosmosAsyncDatabase cosmosAsyncDatabase;
    private CosmosAsyncContainer cosmosAsyncContainer;

    private static final String DATABASE_ID = "Example";
    private static final String CONTAINER_ID = "User";

    Logger logger = Logger.getLogger(CreateUserRepository.class.getName());

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
