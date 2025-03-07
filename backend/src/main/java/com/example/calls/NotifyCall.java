package com.example.calls;

import java.util.*;
import com.microsoft.azure.functions.annotation.*;
import com.windowsazure.messaging.FcmInstallation;
import com.windowsazure.messaging.Notification;
import com.windowsazure.messaging.NotificationHub;
import com.windowsazure.messaging.NotificationHubsException;
import com.example.calls.model.Event;
import com.microsoft.azure.functions.*;

/**
 * Sends notifications when calls are received
 */
public class NotifyCall {
    private static final String NOTIFICATION_HUBS_CONNECTION_STRING = System.getenv("NOTIFICATION_HUBS_CONNECTION_STRING");
    private static final String NOTIFICATION_HUBS_PATH = System.getenv("NOTIFICATION_HUBS_PATH");

    // テスト可能にするためにprotectedに変更
    protected NotificationHub createNotificationHub(ExecutionContext context) throws Exception {
        if (NOTIFICATION_HUBS_CONNECTION_STRING == null || NOTIFICATION_HUBS_CONNECTION_STRING.isEmpty()) {
            String message = "Notification Hubs connection string is not configured";
            context.getLogger().severe(message);
            throw new IllegalStateException(message);
        }
        if (NOTIFICATION_HUBS_PATH == null || NOTIFICATION_HUBS_PATH.isEmpty()) {
            String message = "Notification Hubs path is not configured";
            context.getLogger().severe(message);
            throw new IllegalStateException(message);
        }
        return new NotificationHub(NOTIFICATION_HUBS_CONNECTION_STRING, NOTIFICATION_HUBS_PATH);
    }

    private String createNotificationBody() {
        String toast = System.getenv("TOAST");
        if (toast == null || toast.isEmpty()) {
            toast = "New call received"; // デフォルトメッセージ
        }
        return String.format(
            "{\"data\":{\"message\":\"%s\"}, \"notification\":{\"title\":\"Calling!!\", \"body\":\"電話です\", \"hashCode\":\"bodyhoge\"}, \"android\":{\"notification\":{\"hoge\":\"fuga\"}}}",
            toast);
    }

    @FunctionName("CallHandler")
    public void callHandler(@EventGridTrigger(name = "event") Event event,
                        final ExecutionContext context) throws Exception {
        context.getLogger().info("Processing call notification for event: " + event.id);
        
        NotificationHub hub = createNotificationHub(context);
        // FIXME: DB からデバイストークンを取得する
        FcmInstallation installation = new FcmInstallation(UUID.randomUUID().toString(),
                        "d8QKorUAQO-EeqdXBd3DeR:APA91bERbDQW4Lxr7w7DgcgnJf575O0lTH6AXV6D0nfvZEgH08JFlngfRNsaiSl8mNSFqr07FeI5hIgcIWNSqSZC3qXtOYt_83kVwlqGJIeSnKL3XQaFNJukFGYKrII1JAa72tK6nlO6");

        String body = createNotificationBody();
        Notification notification = Notification.createFcmNotification(body);

        try {
            hub.createOrUpdateInstallation(installation);
            hub.sendNotification(notification);
            context.getLogger().info("Notification sent successfully");
        } catch (NotificationHubsException e) {
            context.getLogger().severe("Failed to send notification: " + e.getMessage());
            throw new Exception("callHandler operation has failed: " + e.getMessage());
        }
    }

    @FunctionName("DebugCallHandler")
    public void debugCallHandler(
                    @HttpTrigger(name = "req", methods = {
                                    HttpMethod.POST }, authLevel = AuthorizationLevel.ANONYMOUS, route = "debug/callHandler") HttpRequestMessage<Optional<String>> request,
                    final ExecutionContext context) throws Exception {
        context.getLogger().info("Processing debug call notification");
        
        NotificationHub hub = createNotificationHub(context);
        // FIXME: DB からデバイストークンを取得する
        FcmInstallation installation = new FcmInstallation(UUID.randomUUID().toString(),
                        "d8QKorUAQO-EeqdXBd3DeR:APA91bERbDQW4Lxr7w7DgcgnJf575O0lTH6AXV6D0nfvZEgH08JFlngfRNsaiSl8mNSFqr07FeI5hIgcIWNSqSZC3qXtOYt_83kVwlqGJIeSnKL3XQaFNJukFGYKrII1JAa72tK6nlO6");

        String body = createNotificationBody();
        Notification notification = Notification.createFcmNotification(body);

        try {
            hub.createOrUpdateInstallation(installation);
            hub.sendNotification(notification);
            context.getLogger().info("Debug notification sent successfully");
        } catch (NotificationHubsException e) {
            context.getLogger().severe("Failed to send debug notification: " + e.getMessage());
            throw new Exception("debugCallHandler operation has failed: " + e.getMessage());
        }
    }
}
