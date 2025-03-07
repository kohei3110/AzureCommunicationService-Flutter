package com.example.users.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.users.model.User;
import com.example.users.repository.GetUserByUserIdRepository;

class GetUserByUserIdServiceTest {

    private GetUserByUserIdService getUserByUserIdService;

    @Mock
    private GetUserByUserIdRepository mockRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        getUserByUserIdService = new GetUserByUserIdService(mockRepository);
    }

    @Test
    void getUserByUserId_Success() throws Exception {
        // Arrange
        String userId = "test-user-id";
        User expectedUser = new User();
        expectedUser.setId(userId);
        expectedUser.setUserId(userId);
        expectedUser.setFamilyName("Doe");
        expectedUser.setGivenName("John");
        
        when(mockRepository.getUserByUserId(userId)).thenReturn(expectedUser);

        // Act
        User result = getUserByUserIdService.getUserByUserId(userId);

        // Assert
        assertNotNull(result);
        assertEquals(userId, result.getId());
        assertEquals(userId, result.getUserId());
        assertEquals("Doe", result.getFamilyName());
        assertEquals("John", result.getGivenName());
        verify(mockRepository).getUserByUserId(userId);
    }

    @Test
    void getUserByUserId_WhenUserNotFound_ThrowsException() throws Exception {
        // Arrange
        String userId = "non-existent-user";
        when(mockRepository.getUserByUserId(userId)).thenThrow(new RuntimeException("User not found"));

        // Act & Assert
        Exception exception = assertThrows(RuntimeException.class, () -> {
            getUserByUserIdService.getUserByUserId(userId);
        });
        assertNotNull(exception);
    }

    @Test
    void getUserByUserId_WithNullId_ThrowsException() throws Exception {
        // Arrange & Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            getUserByUserIdService.getUserByUserId(null);
        });
    }
}