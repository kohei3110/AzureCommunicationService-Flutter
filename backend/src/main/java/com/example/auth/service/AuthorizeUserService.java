package com.example.auth.service;

import java.util.Optional;

import com.microsoft.azure.functions.ExecutionContext;
import com.microsoft.azure.functions.HttpRequestMessage;

/**
 * Service class related to user authentication
 * <p>
 * Provides functionality to process authentication information from HTTP requests and retrieve user IDs.
 * </p>
 */
public class AuthorizeUserService {

    /**
     * Retrieves an access token from the "authorization" request header
     * 
     * @param request HTTP request message
     * @param context Execution context
     * @return Bearer token (token string without the "Bearer " prefix)
     * @throws NullPointerException if the "authorization" header does not exist
     */
    public String getAuthToken(HttpRequestMessage<Optional<String>> request, ExecutionContext context) {
        context.getLogger().info(request.getHeaders().get("authorization").split(" ")[1]); // "authorization"は小文字でないとNullPointerException
        return request.getHeaders().get("authorization").split(" ")[1];
    }

    /**
     * Retrieves a user ID from the access token
     * 
     * @param authToken Authentication token
     * @return User ID
     * @implNote Currently returns a fixed value. Implementation of decoding process is required.
     */
    public String getUserId(String authToken) {
        // FIXME: デコード処理の実装
        return "6a138856-87ef-4cbf-b7e7-8b0692150f5b";
    }

}
