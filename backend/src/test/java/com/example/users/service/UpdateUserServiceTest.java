package com.example.users.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.users.model.User;
import com.example.users.repository.UpdateUserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

class UpdateUserServiceTest {

    private UpdateUserService updateUserService;

    @Mock
    private UpdateUserRepository mockRepository;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        updateUserService = new UpdateUserService(mockRepository);
        objectMapper = new ObjectMapper();
    }

    @Test
    void requestCosmosDBAsync_Success() throws Exception {
        // Arrange
        User user = new User();
        user.setId("test-id");
        user.setUserId("test-user-id");
        user.setFamilyName("Doe");
        user.setGivenName("John");
        user.setEmails(Arrays.asList("john.doe@example.com"));
        
        String requestBody = objectMapper.writeValueAsString(user);
        when(mockRepository.updateItemAsync(any(User.class))).thenReturn(user);

        // Act
        User result = updateUserService.requestCosmosDBAsync(Optional.of(requestBody));

        // Assert
        assertNotNull(result);
        assertEquals("test-id", result.getId());
        assertEquals("test-user-id", result.getUserId());
        assertEquals("Doe", result.getFamilyName());
        assertEquals("John", result.getGivenName());
        assertEquals("john.doe@example.com", result.getEmails().get(0));
        verify(mockRepository).updateItemAsync(any(User.class));
    }

    @Test
    void requestCosmosDBAsync_WithInvalidJson_ThrowsException() {
        // Arrange
        String invalidJson = "invalid-json";

        // Act & Assert
        Exception exception = assertThrows(Exception.class, () -> {
            updateUserService.requestCosmosDBAsync(Optional.of(invalidJson));
        });
        assertEquals("Invalid parameters", exception.getMessage());
    }

    @Test
    void requestCosmosDBAsync_WhenRepositoryThrowsException_ThrowsException() throws Exception {
        // Arrange
        User user = new User();
        user.setId("test-id");
        String requestBody = objectMapper.writeValueAsString(user);
        
        when(mockRepository.updateItemAsync(any(User.class)))
            .thenThrow(new RuntimeException("Update failed"));

        // Act & Assert
        assertThrows(Exception.class, () -> {
            updateUserService.requestCosmosDBAsync(Optional.of(requestBody));
        });
    }

    @Test
    void requestCosmosDBAsync_WithEmptyOptional_ThrowsException() {
        // Arrange & Act & Assert
        assertThrows(Exception.class, () -> {
            updateUserService.requestCosmosDBAsync(Optional.empty());
        });
    }
}