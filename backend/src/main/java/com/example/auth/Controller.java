package com.example.auth;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.example.auth.model.User;
import com.example.auth.service.AuthorizeUserService;
import com.example.auth.service.CreateAcsUserService;
import com.example.auth.service.GetUserByUserIdService;
import com.example.auth.service.UpdateUserService;
import com.microsoft.azure.functions.ExecutionContext;
import com.microsoft.azure.functions.HttpMethod;
import com.microsoft.azure.functions.HttpRequestMessage;
import com.microsoft.azure.functions.annotation.*;

/**
 * Controller class for Azure Communication Services user management.
 * Handles HTTP requests related to ACS user creation and token generation.
 */
public class Controller {

    Factory factory = new Factory();

    /**
     * Endpoint for generating ACS user IDs and tokens.
     * This method authenticates the user, retrieves or creates an ACS user ID,
     * and generates an ACS token for the authenticated user.
     *
     * @param request HTTP request message with optional payload
     * @param context Function execution context
     * @return Map containing the ACS user ID and token
     * @throws Exception if authentication fails or token generation fails
     */
    @FunctionName("GetACSUserIdToken")
    public Map<String, String> run(
            @HttpTrigger(name = "req", methods = { HttpMethod.GET,
            }, authLevel = AuthorizationLevel.ANONYMOUS, route = "acs") HttpRequestMessage<Optional<String>> request,
            final ExecutionContext context) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        AuthorizeUserService authorizeUserService = factory.injectAuthorizeUserService();
        GetUserByUserIdService getUserByUserIdService = factory.injectGetUserByUserIdService();
        CreateAcsUserService createAcsUserService = factory.injectCreateAcsUserService();
        UpdateUserService updateUserService = factory.injectUpdateUserService();

        // アクセストークンを取得
        String authToken = authorizeUserService.getAuthToken(request, context);

        // アクセストークンをデコードし、"sub"を取得
        String userId = authorizeUserService.getUserId(authToken);

        User user = getUserByUserIdService.getUserByUserId(userId);
        if (user.getAcsUserId() == null) {
            // ユーザーに acsUserId が付与されていない場合
            String acsUserId = createAcsUserService.createACSUserId(context);
            String acsUserToken = createAcsUserService.createACSToken(acsUserId, context);

            // ユーザーに acsUserId を付与するように CosmosDB にリクエスト
            updateUserService.updateUser(userId, acsUserId);

            map.put("acsUserId", acsUserId);
            map.put("acsUserToken", acsUserToken);
        } else {
            // ユーザーに acsUserId が付与されている場合
            String acsUserId = user.getAcsUserId();
            String acsUserToken = createAcsUserService.createACSToken(acsUserId, context);
            map.put("acsUserId", acsUserId);
            map.put("acsUserToken", acsUserToken);
        }
        return map;
    }

}
