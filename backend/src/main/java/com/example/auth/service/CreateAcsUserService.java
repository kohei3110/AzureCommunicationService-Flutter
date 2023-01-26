package com.example.auth.service;

import java.util.Arrays;
import java.util.List;

import com.azure.communication.common.CommunicationUserIdentifier;
import com.azure.communication.identity.CommunicationIdentityClient;
import com.azure.communication.identity.CommunicationIdentityClientBuilder;
import com.azure.communication.identity.models.CommunicationTokenScope;
import com.azure.core.credential.AzureKeyCredential;
import com.microsoft.azure.functions.ExecutionContext;

public class CreateAcsUserService {

    private static final String endpoint = System.getenv("ACS_ENDPOINT");
    private static final String credential = System.getenv("ACS_ACCESSKEY");

    /**
     * ACS user Id を生成
     * 
     * @param context
     * @return
     * @throws Exception
     */
    public String createACSUserId(ExecutionContext context) throws Exception {
        CommunicationIdentityClient client = createAuthenticatedClient();
        try {
            CommunicationUserIdentifier identifier = client.createUser();
            return identifier.getId();
        } catch (Exception e) {
            context.getLogger().warning(e.getMessage());
            throw new Exception("createACSUserId operation has failed");
        }
    }

    /**
     * ACS Token を生成
     * 
     * @param userId
     * @param context
     * @return
     * @throws Exception
     */
    public String createACSToken(String userId, ExecutionContext context) throws Exception {
        List<CommunicationTokenScope> scopes = Arrays.asList(CommunicationTokenScope.VOIP);
        CommunicationIdentityClient identityClient = createAuthenticatedClient();
        try {
            CommunicationUserIdentifier userIdentifier = new CommunicationUserIdentifier(userId);
            return identityClient.getToken(userIdentifier, scopes).getToken();
        } catch (Exception e) {
            context.getLogger().warning(e.getMessage());
            throw new Exception("createACSToken operation has failed");
        }
    }

    private CommunicationIdentityClient createAuthenticatedClient() {
        return new CommunicationIdentityClientBuilder()
                .endpoint(endpoint)
                .credential(new AzureKeyCredential(credential))
                .buildClient();
    }
}
