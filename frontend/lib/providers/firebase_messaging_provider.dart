import 'package:firebase_core/firebase_core.dart';
import 'package:firebase_messaging/firebase_messaging.dart';
import 'package:flutter_local_notifications/flutter_local_notifications.dart';
import 'package:hooks_riverpod/hooks_riverpod.dart';

import '../firebase_options.dart';

final firebaseMessagingProvider = Provider<FirebaseMessagingService>((ref) {
  return FirebaseMessagingService();
});

class FirebaseMessagingService {
  late final FlutterLocalNotificationsPlugin flutterLocalNotificationsPlugin;
  late final AndroidNotificationChannel channel;

  Future<void> initialize() async {
    await Firebase.initializeApp(options: DefaultFirebaseOptions.currentPlatform);
    await _setupNotifications();
    _setupMessageHandlers();
  }

  Future<String?> getToken() async {
    return await FirebaseMessaging.instance.getToken();
  }

  Future<void> _setupNotifications() async {
    channel = const AndroidNotificationChannel(
      'high_importance_channel',
      'High Importance Notifications',
      description: 'This channel is used for important notifications.',
      importance: Importance.max,
    );

    flutterLocalNotificationsPlugin = FlutterLocalNotificationsPlugin();
    await flutterLocalNotificationsPlugin.initialize(
      const InitializationSettings(
        android: AndroidInitializationSettings('@mipmap/ic_launcher'),
      ),
    );

    await flutterLocalNotificationsPlugin
        .resolvePlatformSpecificImplementation<
            AndroidFlutterLocalNotificationsPlugin>()
        ?.createNotificationChannel(channel);
  }

  void _setupMessageHandlers() {
    FirebaseMessaging.onMessage.listen((RemoteMessage message) {
      _firebaseMessagingForegroundHandler(message);
    });

    FirebaseMessaging.onBackgroundMessage(_firebaseMessagingBackgroundHandler);
  }

  void _firebaseMessagingForegroundHandler(RemoteMessage message) {
    // 既存の処理をここに移動
    showCallkitIncoming(message.messageId!);
  }
}

@pragma('vm:entry-point')
Future<void> _firebaseMessagingBackgroundHandler(RemoteMessage message) async {
  // 既存の処理をここに移動
  showCallkitIncoming(message.messageId!);
}

// showCallkitIncoming関数も同じファイルに移動
Future<void> showCallkitIncoming(String uuid) async {
  // ...既存のshowCallkitIncoming実装...
}