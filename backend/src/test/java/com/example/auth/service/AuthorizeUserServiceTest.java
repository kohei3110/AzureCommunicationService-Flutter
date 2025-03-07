package com.example.auth.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Map;
import java.util.Optional;
import java.util.logging.Logger;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.microsoft.azure.functions.ExecutionContext;
import com.microsoft.azure.functions.HttpRequestMessage;

class AuthorizeUserServiceTest {
    private AuthorizeUserService authorizeUserService;

    @Mock
    private HttpRequestMessage<Optional<String>> mockRequest;

    @Mock
    private ExecutionContext mockContext;

    @Mock
    private Logger mockLogger;

    @BeforeEach
    void setUp() {
        AutoCloseable closeable = MockitoAnnotations.openMocks(this);
        authorizeUserService = new AuthorizeUserService();
        
        // Setup Logger mock
        when(mockContext.getLogger()).thenReturn(mockLogger);
    }

    @Test
    void getAuthToken_WithValidHeader_ReturnsToken() throws Exception {
        // Arrange
        String authHeader = "Bearer test-token";
        Map<String, String> headers = Map.of("authorization", authHeader);
        when(mockRequest.getHeaders()).thenReturn(headers);

        // Act
        String token = authorizeUserService.getAuthToken(mockRequest, mockContext);

        // Assert
        assertEquals("test-token", token);
        verify(mockContext.getLogger()).info(anyString());
    }

    @Test
    void getAuthToken_WithMissingHeader_ThrowsException() {
        // Arrange
        when(mockRequest.getHeaders()).thenReturn(Map.of());

        // Act & Assert
        Exception exception = assertThrows(NullPointerException.class, () -> {
            authorizeUserService.getAuthToken(mockRequest, mockContext);
        });
        assertNotNull(exception);
    }

    @Test
    void getAuthToken_WithInvalidHeader_ThrowsException() {
        // Arrange
        Map<String, String> headers = Map.of("authorization", "InvalidToken");
        when(mockRequest.getHeaders()).thenReturn(headers);

        // Act & Assert
        Exception exception = assertThrows(ArrayIndexOutOfBoundsException.class, () -> {
            authorizeUserService.getAuthToken(mockRequest, mockContext);
        });
        assertNotNull(exception);
    }
}