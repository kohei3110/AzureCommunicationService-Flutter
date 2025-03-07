package com.example.auth.service;

import com.azure.core.credential.AccessToken;
import java.time.OffsetDateTime;

public class MockCommunicationUserToken extends AccessToken {
    public MockCommunicationUserToken(String token) {
        super(token, OffsetDateTime.now().plusHours(1));
    }
}