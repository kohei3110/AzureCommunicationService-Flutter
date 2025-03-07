package com.example.auth.service;

import java.util.Arrays;
import java.util.List;

import com.azure.communication.common.CommunicationUserIdentifier;
import com.azure.communication.identity.CommunicationIdentityClient;
import com.azure.communication.identity.CommunicationIdentityClientBuilder;
import com.azure.communication.identity.models.CommunicationTokenScope;
import com.azure.core.credential.AzureKeyCredential;
import com.azure.core.credential.AccessToken;
import com.microsoft.azure.functions.ExecutionContext;

/**
 * Service class that provides functionality for Azure Communication Services (ACS) user management.
 * This class is responsible for creating ACS user identifiers and generating authentication tokens
 * for these users. It utilizes the Azure Communication Identity Client to interact with Azure
 * Communication Services.
 * 
 * The service requires ACS_ENDPOINT and ACS_ACCESSKEY environment variables to be set
 * for authentication with Azure Communication Services.
 */
public class CreateAcsUserService {

    private static final String ENDPOINT = System.getenv("ACS_ENDPOINT");
    private static final String ACCESSKEY = System.getenv("ACS_ACCESSKEY");

    // テストを容易にするためにpackage-privateに変更
    CommunicationIdentityClient createAuthenticatedClient() {
        if (ENDPOINT == null || ENDPOINT.isEmpty()) {
            throw new IllegalStateException("ACS_ENDPOINT is not configured");
        }
        if (ACCESSKEY == null || ACCESSKEY.isEmpty()) {
            throw new IllegalStateException("ACS_ACCESSKEY is not configured");
        }

        return new CommunicationIdentityClientBuilder()
            .endpoint(ENDPOINT)
            .credential(new AzureKeyCredential(ACCESSKEY))
            .buildClient();
    }

    /**
     * Generates an ACS user ID
     * 
     * @param context
     * @return
     * @throws Exception
     */
    public String createACSUserId(ExecutionContext context) throws Exception {
        context.getLogger().info("Creating ACS user ID");
        
        CommunicationIdentityClient client = createAuthenticatedClient();
        CommunicationUserIdentifier user = client.createUser();
        
        context.getLogger().info("ACS user ID created successfully: " + user.getId());
        return user.getId();
    }

    /**
     * Generates an ACS Token
     * 
     * @param userId
     * @param context
     * @return
     * @throws Exception
     */
    public String createACSToken(String userId, ExecutionContext context) throws Exception {
        context.getLogger().info("Creating ACS token for user: " + userId);

        CommunicationIdentityClient client = createAuthenticatedClient();
        List<CommunicationTokenScope> scopes = Arrays.asList(CommunicationTokenScope.VOIP);

        try {
            AccessToken token = client.getToken(new CommunicationUserIdentifier(userId), scopes);
            context.getLogger().info("ACS token created successfully");
            return token.getToken();
        } catch (Exception e) {
            context.getLogger().severe("Failed to create ACS token: " + e.getMessage());
            throw e;
        }
    }
}
