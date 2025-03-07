package com.example.calls;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Map;
import java.util.Date;
import java.util.Optional;
import java.util.logging.Logger;
import java.time.Duration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.microsoft.azure.functions.ExecutionContext;
import com.microsoft.azure.functions.HttpRequestMessage;
import com.windowsazure.messaging.NotificationHub;
import com.windowsazure.messaging.NotificationHubsException;
import com.example.calls.model.Event;

class NotifyCallTest {
    private NotifyCall notifyCall;

    @Mock
    private HttpRequestMessage<Optional<String>> mockRequest;

    @Mock
    private ExecutionContext mockContext;

    @Mock
    private Logger mockLogger;

    @BeforeEach
    void setUp() throws Exception {
        AutoCloseable closeable = MockitoAnnotations.openMocks(this);
        notifyCall = spy(new NotifyCall());

        // Setup logger mock
        when(mockContext.getLogger()).thenReturn(mockLogger);

        // Setup notification hub mock
        NotificationHub mockHub = mock(NotificationHub.class);
        doReturn(mockHub).when(notifyCall).createNotificationHub(any());
    }

    @Test
    void callHandler_Success() throws Exception {
        // Arrange
        Event event = new Event();
        event.topic = "test-topic";
        event.subject = "test-subject";
        event.eventType = "Microsoft.Communication.CallEnded";
        event.eventTime = new Date();
        event.id = "test-id";
        event.dataVersion = "1.0";
        event.metadataVersion = "1";
        event.data = Map.of("message", "test message");

        // Act & Assert - 例外が発生しないことを確認
        assertDoesNotThrow(() -> notifyCall.callHandler(event, mockContext));
    }

    @Test
    void callHandler_WhenHubFails_ThrowsException() throws Exception {
        // Arrange
        Event event = new Event();
        NotificationHub mockHub = mock(NotificationHub.class);
        doThrow(new NotificationHubsException("Test error", 500, true, Duration.ofSeconds(1)))
            .when(mockHub)
            .sendNotification(any());
        doReturn(mockHub).when(notifyCall).createNotificationHub(any());

        // Act & Assert
        assertThrows(Exception.class, () -> notifyCall.callHandler(event, mockContext));
    }

    @Test
    void debugCallHandler_Success() throws Exception {
        // Act & Assert - 例外が発生しないことを確認
        assertDoesNotThrow(() -> notifyCall.debugCallHandler(mockRequest, mockContext));
    }
}