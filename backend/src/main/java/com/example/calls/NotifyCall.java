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

        private static final String NOTIFICATION_HUBS_CONNECTION_STRING = System
                        .getenv("NOTIFICATION_HUBS_CONNECTION_STRING");
        private static final String NOTIFICATION_HUBS_PATH = System.getenv("NOTIFICATION_HUBS_PATH");

        @FunctionName("CallHandler")
        public void callHandler(@EventGridTrigger(name = "event") Event event,
                        final ExecutionContext context) throws Exception {
                NotificationHub hub = new NotificationHub(NOTIFICATION_HUBS_CONNECTION_STRING,
                                NOTIFICATION_HUBS_PATH);
                // FIXME: DB からデバイストークンを取得する
                FcmInstallation installation = new FcmInstallation(UUID.randomUUID().toString(),
                                "d8QKorUAQO-EeqdXBd3DeR:APA91bERbDQW4Lxr7w7DgcgnJf575O0lTH6AXV6D0nfvZEgH08JFlngfRNsaiSl8mNSFqr07FeI5hIgcIWNSqSZC3qXtOYt_83kVwlqGJIeSnKL3XQaFNJukFGYKrII1JAa72tK6nlO6");
                String toast = System.getenv("TOAST");
                String body = String.format(
                                "{\"data\":{\"message\":\"%s\"}, \"notification\":{\"title\":\"Calling!!\", \"body\":\"電話です\", \"hashCode\":\"bodyhoge\"}, \"android\":{\"notification\":{\"hoge\":\"fuga\"}}}",
                                toast);

                // String body = String.format("{\"data\":{\"message\":\"%s\"}}", toast); //
                // 形式が違うと、Error: HTTP/2.0 400 Bad Request
                // - Could not read notification body
                // from the request
                // String body = String.format(
                // "{\"message\": \"notification\":{\"title\":\"FCMMessage\",
                // \"body\":\"bodyhoge\"}}",
                // toast); // Error: HTTP/2.0 400 Bad Request -
                // <Error><Code>400</Code><Detail>The supplied notification payload is
                // invalid.TrackingId:e409d189-6580-4cea-9087-d1817f32d609_G4,TimeStamp:1/7/2023
                // 4:27:12 AM</Detail></Error>
                Notification notification = Notification.createFcmNotification(body);
                try {
                        hub.createOrUpdateInstallation(installation);
                        hub.sendNotification(notification);
                } catch (NotificationHubsException e) {
                        context.getLogger().warning(e.getMessage());
                        throw new Exception("callHandler operation has failed");
                }
        }

        @FunctionName("DebugCallHandler")
        public void debugCallHandler(
                        @HttpTrigger(name = "req", methods = {
                                        HttpMethod.POST }, authLevel = AuthorizationLevel.ANONYMOUS, route = "debug/callHandler") HttpRequestMessage<Optional<String>> request,
                        final ExecutionContext context) throws Exception {
                NotificationHub hub = new NotificationHub(NOTIFICATION_HUBS_CONNECTION_STRING,
                                NOTIFICATION_HUBS_PATH);
                // FIXME: DB からデバイストークンを取得する
                FcmInstallation installation = new FcmInstallation(UUID.randomUUID().toString(),
                                "d8QKorUAQO-EeqdXBd3DeR:APA91bERbDQW4Lxr7w7DgcgnJf575O0lTH6AXV6D0nfvZEgH08JFlngfRNsaiSl8mNSFqr07FeI5hIgcIWNSqSZC3qXtOYt_83kVwlqGJIeSnKL3XQaFNJukFGYKrII1JAa72tK6nlO6");
                String toast = System.getenv("TOAST");
                String body = String.format(
                                "{\"data\":{\"message\":\"%s\"}, \"notification\":{\"title\":\"Calling!!\", \"body\":\"電話です\", \"hashCode\":\"bodyhoge\"}, \"android\":{\"notification\":{\"hoge\":\"fuga\"}}}",
                                toast);

                // String body = String.format("{\"data\":{\"message\":\"%s\"}}", toast); //
                // 形式が違うと、Error: HTTP/2.0 400 Bad Request
                // - Could not read notification body
                // from the request
                // String body = String.format(
                // "{\"message\": \"notification\":{\"title\":\"FCMMessage\",
                // \"body\":\"bodyhoge\"}}",
                // toast); // Error: HTTP/2.0 400 Bad Request -
                // <Error><Code>400</Code><Detail>The supplied notification payload is
                // invalid.TrackingId:e409d189-6580-4cea-9087-d1817f32d609_G4,TimeStamp:1/7/2023
                // 4:27:12 AM</Detail></Error>
                Notification notification = Notification.createFcmNotification(body);
                try {
                        hub.createOrUpdateInstallation(installation);
                        hub.sendNotification(notification);
                } catch (NotificationHubsException e) {
                        context.getLogger().warning(e.getMessage());
                        throw new Exception("callHandler operation has failed");
                }
        }

}
