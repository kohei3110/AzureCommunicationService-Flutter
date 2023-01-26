package com.example.auth.service;

import java.util.Optional;

import com.microsoft.azure.functions.ExecutionContext;
import com.microsoft.azure.functions.HttpRequestMessage;

public class AuthorizeUserService {

    /**
     * リクエストヘッダ "authorization" から アクセストークンを取得
     */
    public String getAuthToken(HttpRequestMessage<Optional<String>> request, ExecutionContext context) {
        context.getLogger().info(request.getHeaders().get("authorization").split(" ")[1]); // "authorization"は小文字でないとNullPointerException
        return request.getHeaders().get("authorization").split(" ")[1];
    }

    /**
     * アクセストークンからユーザーIDを取得
     */
    public String getUserId(String authToken) {
        // FIXME: デコード処理の実装
        return "6a138856-87ef-4cbf-b7e7-8b0692150f5b";
    }

}
