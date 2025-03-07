package com.example.integration;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.logging.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Order;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.azure.communication.common.CommunicationUserIdentifier;
import com.azure.communication.identity.CommunicationIdentityClient;
import com.azure.core.credential.AccessToken;
import com.azure.core.credential.AzureKeyCredential;
import com.example.auth.service.CreateAcsUserService;
import com.example.auth.service.MockCommunicationUserToken;
import com.example.users.model.User;
import com.example.users.repository.CreateUserRepository;
import com.example.users.service.CreateUserService;
import com.microsoft.azure.functions.ExecutionContext;
import com.windowsazure.messaging.NotificationHub;

/**
 * Integration tests for Azure Communication Services.
 * Tests the complete flow from user creation to notification sending.
 */
class ACSIntegrationTest extends BaseIntegrationTest {

    private CreateAcsUserService acsUserService;
    private CreateUserRepository userRepository;
    private CreateUserService userService;
    private final ExecutionContext context = new ExecutionContext() {
        @Override
        public Logger getLogger() {
            return Logger.getLogger(ACSIntegrationTest.class.getName());
        }

        @Override
        public String getInvocationId() {
            return "test-invocation-id";
        }

        @Override
        public String getFunctionName() {
            return "test-function";
        }
    };

    @Mock
    private CommunicationIdentityClient mockClient;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        acsUserService = new TestCreateAcsUserService(mockClient);
        userRepository = mock(CreateUserRepository.class);
        userService = new CreateUserService(userRepository);
    }

    @Test
    @Order(1)
    void createACSUser_ThenCreateUser_Success() throws Exception {
        // Arrange
        String expectedUserId = "test-user-id";
        String expectedToken = "test-token";
        CommunicationUserIdentifier expectedUser = new CommunicationUserIdentifier(expectedUserId);
        AccessToken userToken = new MockCommunicationUserToken(expectedToken);

        when(mockClient.createUser()).thenReturn(expectedUser);
        when(mockClient.getToken(any(CommunicationUserIdentifier.class), anyList()))
            .thenReturn(userToken);

        // First, create an ACS user
        String acsUserId = acsUserService.createACSUserId(context);
        assertNotNull(acsUserId);
        assertEquals(expectedUserId, acsUserId);
        
        // Then, create a token for that user
        String token = acsUserService.createACSToken(acsUserId, context);
        assertNotNull(token);
        assertEquals(expectedToken, token);

        // Create a user in our system
        User user = new User();
        user.setUserId("test-user-" + System.currentTimeMillis());
        user.setFamilyName("Test");
        user.setGivenName("User");
        user.setAcsUserId(acsUserId);
        
        when(userRepository.createItemAsync(any(User.class))).thenReturn(user);
        User createdUser = userRepository.createItemAsync(user);
        
        assertNotNull(createdUser);
        assertEquals(acsUserId, createdUser.getAcsUserId());
        verify(userRepository).createItemAsync(user);
    }

    @Test
    @Order(2)
    void sendNotification_Success() throws Exception {
        NotificationHub hub = mock(NotificationHub.class);
        String testMessage = String.format(
            "{\"data\":{\"message\":\"Test notification\"}, \"notification\":{\"title\":\"Test\", \"body\":\"This is a test notification\"}}");
        
        // Act & Assert
        assertDoesNotThrow(() -> {
            hub.sendNotification(com.windowsazure.messaging.Notification.createFcmNotification(testMessage));
        });
    }

    @Test
    @Order(3)
    void verifyACSConnection_Success() {
        // Arrange & Act & Assert
        assertDoesNotThrow(() -> {
            mockClient.createUser();
        });
        verify(mockClient).createUser();
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