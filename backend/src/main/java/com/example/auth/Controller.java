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

public class Controller {

    Factory factory = new Factory();

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
