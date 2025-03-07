package com.example.users.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.users.model.User;
import com.example.users.repository.CreateUserRepository;

class CreateUserServiceTest {
    private CreateUserService createUserService;

    @Mock
    private CreateUserRepository mockRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        createUserService = new CreateUserService(mockRepository);
    }

    @Test
    void requestCosmosDBAsync_Success() throws Exception {
        // Arrange
        String validJson = "{\"userId\":\"test-user\",\"familyName\":\"Doe\",\"givenName\":\"John\"}";
        User expectedUser = new User();
        expectedUser.setUserId("test-user");
        expectedUser.setFamilyName("Doe");
        expectedUser.setGivenName("John");

        when(mockRepository.createItemAsync(any(User.class))).thenReturn(expectedUser);

        // Act
        User result = createUserService.requestCosmosDBAsync(Optional.of(validJson));

        // Assert
        assertNotNull(result);
        assertEquals("test-user", result.getUserId());
        assertEquals("Doe", result.getFamilyName());
        assertEquals("John", result.getGivenName());
    }

    @Test
    void requestCosmosDBAsync_WithInvalidJson_ThrowsException() {
        // Arrange & Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            createUserService.requestCosmosDBAsync(Optional.of("invalid-json"));
        });
    }

    @Test
    void requestCosmosDBAsync_WithNullOptional_ThrowsException() {
        // Arrange & Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            createUserService.requestCosmosDBAsync(null);
        });
    }

    @Test
    void requestCosmosDBAsync_WithEmptyOptional_ThrowsException() {
        // Arrange & Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            createUserService.requestCosmosDBAsync(Optional.empty());
        });
    }

    @Test
    void requestCosmosDBAsync_WithMissingRequiredFields_ThrowsException() throws Exception {
        // Arrange
        String jsonWithMissingFields = "{\"userId\":\"test-user\"}"; // missing familyName and givenName

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            createUserService.requestCosmosDBAsync(Optional.of(jsonWithMissingFields));
        });
    }
}