package com.example.auth.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.azure.communication.common.CommunicationUserIdentifier;
import com.azure.communication.identity.CommunicationIdentityClient;
import com.azure.core.credential.AccessToken;
import com.microsoft.azure.functions.ExecutionContext;

class CreateAcsUserServiceTest {
    private CreateAcsUserService createAcsUserService;

    @Mock
    private ExecutionContext mockContext;

    @Mock
    private Logger mockLogger;

    @Mock
    private CommunicationIdentityClient mockClient;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        createAcsUserService = new TestCreateAcsUserService(mockClient);

        // Setup Logger mock
        when(mockContext.getLogger()).thenReturn(mockLogger);
    }

    @Test
    void createACSUserId_Success() throws Exception {
        // Arrange
        String expectedUserId = "test-user-id";
        CommunicationUserIdentifier expectedUser = new CommunicationUserIdentifier(expectedUserId);
        when(mockClient.createUser()).thenReturn(expectedUser);

        // Act
        String userId = createAcsUserService.createACSUserId(mockContext);

        // Assert
        assertNotNull(userId);
        assertEquals(expectedUserId, userId);
        verify(mockClient).createUser();
        verify(mockLogger, times(2)).info(anyString());
    }

    @Test
    void createACSToken_Success() throws Exception {
        // Arrange
        String userId = "test-user-id";
        String expectedToken = "test-token";
        AccessToken userToken = new MockCommunicationUserToken(expectedToken);
        when(mockClient.getToken(any(CommunicationUserIdentifier.class), anyList()))
            .thenReturn(userToken);

        // Act
        String token = createAcsUserService.createACSToken(userId, mockContext);

        // Assert
        assertNotNull(token);
        assertEquals(expectedToken, token);
        verify(mockClient).getToken(any(CommunicationUserIdentifier.class), anyList());
        verify(mockLogger, times(2)).info(anyString());
    }

    @Test
    void createACSToken_WithInvalidUserId_ThrowsException() {
        // Arrange
        String invalidUserId = "invalid-user-id";
        when(mockClient.getToken(any(CommunicationUserIdentifier.class), anyList()))
            .thenThrow(new RuntimeException("Invalid user ID"));

        // Act & Assert
        Exception exception = assertThrows(Exception.class, () -> {
            createAcsUserService.createACSToken(invalidUserId, mockContext);
        });
        assertNotNull(exception);
        verify(mockLogger).severe(anyString());
    }

    // テスト用のサブクラスを作成して protected メソッドにアクセス
    private static class TestCreateAcsUserService extends CreateAcsUserService {
        private final CommunicationIdentityClient testClient;

        TestCreateAcsUserService(CommunicationIdentityClient client) {
            this.testClient = client;
        }

        @Override
        CommunicationIdentityClient createAuthenticatedClient() {
            return testClient;
        }
    }
}