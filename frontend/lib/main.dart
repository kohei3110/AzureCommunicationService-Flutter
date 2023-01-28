import 'package:azurecommunicationserviceflutter/views/widgets/app_router.dart';
import 'package:firebase_core/firebase_core.dart';
import 'package:firebase_messaging/firebase_messaging.dart';
import 'package:flutter_callkit_incoming/entities/android_params.dart';
import 'package:flutter_callkit_incoming/entities/call_event.dart';
import 'package:flutter_callkit_incoming/entities/call_kit_params.dart';
import 'package:flutter_callkit_incoming/entities/ios_params.dart';
import 'package:flutter_callkit_incoming/flutter_callkit_incoming.dart';
import 'package:flutter_local_notifications/flutter_local_notifications.dart';
import 'firebase_options.dart';
import 'package:flutter/material.dart';
import 'package:hooks_riverpod/hooks_riverpod.dart';

void main() async {
  WidgetsFlutterBinding.ensureInitialized();
  await Firebase.initializeApp(options: DefaultFirebaseOptions.currentPlatform);
  String? token = await FirebaseMessaging.instance.getToken();
  print('token: $token');
  const AndroidNotificationChannel channel = AndroidNotificationChannel(
    'high_importance_channel', // id
    'High Importance Notifications', // title
    description:
        'This channel is used for important notifications.', // description
    importance: Importance.max,
  );
  FlutterLocalNotificationsPlugin flutterLocalNotificationsPlugin =
      FlutterLocalNotificationsPlugin();
  flutterLocalNotificationsPlugin.initialize(const InitializationSettings(
      android: AndroidInitializationSettings('@mipmap/ic_launcher')));
  await flutterLocalNotificationsPlugin
      .resolvePlatformSpecificImplementation<
          AndroidFlutterLocalNotificationsPlugin>()
      ?.createNotificationChannel(channel);

  // フォアグラウンド時の通知ハンドラ
  FirebaseMessaging.onMessage.listen((RemoteMessage message) {
    _firebaseMessagingForegroundHandler(
        message, flutterLocalNotificationsPlugin, channel);
  });

  // バックグラウンド時の通知ハンドラ
  FirebaseMessaging.onBackgroundMessage(_firebaseMessagingBackgroundHandler);

  runApp(const ProviderScope(child: MyApp()));
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Flutter Demo',
      theme: ThemeData(
        primarySwatch: Colors.blue,
      ),
      home: const MyHomePage(title: 'Flutter Demo Home Page'),
    );
  }
}

class MyHomePage extends HookConsumerWidget {
  const MyHomePage({super.key, required this.title});
  final String title;

  @override
  Widget build(BuildContext context, WidgetRef ref) {
    return MaterialApp(
        title: 'ACS Demo',
        theme: ThemeData(
          primarySwatch: Colors.green,
        ),
        home: Router<dynamic>(routerDelegate: AppRouterDelegate(ref)));
  }
}

void _firebaseMessagingForegroundHandler(
    RemoteMessage message,
    FlutterLocalNotificationsPlugin flutterLocalNotificationsPlugin,
    AndroidNotificationChannel channel) {
  showCallkitIncoming(message.messageId!);
}

Future<void> _firebaseMessagingBackgroundHandler(RemoteMessage message) async {
  // If you're going to use other Firebase services in the background, such as Firestore,
  // make sure you call `initializeApp` before using other Firebase services.
  showCallkitIncoming(message.messageId!);
}

Future<void> showCallkitIncoming(String uuid) async {
  // FIXME: パラメータを provider 経由で取得する
  final params = CallKitParams(
    id: uuid,
    nameCaller: 'Kohei Saito',
    appName: 'Callkit',
    avatar: 'https://i.pravatar.cc/100',
    handle: '0123456789',
    type: 0,
    duration: 30000,
    textAccept: 'Accept',
    textDecline: 'Decline',
    textMissedCall: 'Missed call',
    textCallback: 'Call back',
    extra: <String, dynamic>{'userId': '1a2b3c4d'},
    headers: <String, dynamic>{'apiKey': 'Abc@123!', 'platform': 'flutter'},
    android: AndroidParams(
      isCustomNotification: true,
      isShowLogo: false,
      isShowCallback: true,
      isShowMissedCallNotification: true,
      ringtonePath: 'system_ringtone_default',
      backgroundColor: '#0955fa',
      backgroundUrl: 'assets/test.png',
      actionColor: '#4CAF50',
    ),
    ios: IOSParams(
      iconName: 'CallKitLogo',
      handleType: '',
      supportsVideo: true,
      maximumCallGroups: 2,
      maximumCallsPerCallGroup: 1,
      audioSessionMode: 'default',
      audioSessionActive: true,
      audioSessionPreferredSampleRate: 44100.0,
      audioSessionPreferredIOBufferDuration: 0.005,
      supportsDTMF: true,
      supportsHolding: true,
      supportsGrouping: false,
      supportsUngrouping: false,
      ringtonePath: 'system_ringtone_default',
    ),
  );
  await FlutterCallkitIncoming.showCallkitIncoming(params);
  FlutterCallkitIncoming.onEvent.listen((CallEvent? event) {
    switch (event!.event) {
      case Event.ACTION_CALL_INCOMING:
        print("ACTION_CALL_INCOMING");
        break;
      case Event.ACTION_CALL_START:
        // TODO: started an outgoing call
        // TODO: show screen calling in Flutter
        break;
      case Event.ACTION_CALL_ACCEPT:
        print("電話に出る");
        break;
      case Event.ACTION_CALL_DECLINE:
        // TODO: declined an incoming call
        break;
      case Event.ACTION_CALL_ENDED:
        // TODO: ended an incoming/outgoing call
        break;
      case Event.ACTION_CALL_TIMEOUT:
        // TODO: missed an incoming call
        break;
      case Event.ACTION_CALL_CALLBACK:
        // TODO: only Android - click action `Call back` from missed call notification
        break;
      case Event.ACTION_CALL_TOGGLE_HOLD:
        // TODO: only iOS
        break;
      case Event.ACTION_CALL_TOGGLE_MUTE:
        // TODO: only iOS
        break;
      case Event.ACTION_CALL_TOGGLE_DMTF:
        // TODO: only iOS
        break;
      case Event.ACTION_CALL_TOGGLE_GROUP:
        // TODO: only iOS
        break;
      case Event.ACTION_CALL_TOGGLE_AUDIO_SESSION:
        // TODO: only iOS
        break;
      case Event.ACTION_DID_UPDATE_DEVICE_PUSH_TOKEN_VOIP:
        // TODO: only iOS
        break;
    }
  });
}
