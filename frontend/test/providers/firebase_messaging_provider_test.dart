import 'package:flutter_test/flutter_test.dart';
import 'package:hooks_riverpod/hooks_riverpod.dart';
import 'package:mockito/mockito.dart';
import 'package:mockito/annotations.dart';
import 'package:firebase_messaging/firebase_messaging.dart';
import 'package:flutter_local_notifications/flutter_local_notifications.dart';
import '../../lib/providers/firebase_messaging_provider.dart';
import '../helpers/test_helper.dart';

@GenerateNiceMocks([
  MockSpec<FlutterLocalNotificationsPlugin>(),
])
import 'firebase_messaging_provider_test.mocks.dart';

void main() {
  TestWidgetsFlutterBinding.ensureInitialized();

  late ProviderContainer container;
  late FirebaseMessagingService messagingService;
  late MockFlutterLocalNotificationsPlugin mockNotificationsPlugin;

  setUp(() async {
    await TestHelper.setupFirebaseTest();

    mockNotificationsPlugin = MockFlutterLocalNotificationsPlugin();
    
    container = ProviderContainer();
    messagingService = container.read(firebaseMessagingProvider);
    messagingService.flutterLocalNotificationsPlugin = mockNotificationsPlugin;

    when(mockNotificationsPlugin.initialize(any))
        .thenAnswer((_) async => true);
    when(mockNotificationsPlugin
        .resolvePlatformSpecificImplementation<AndroidFlutterLocalNotificationsPlugin>())
        .thenReturn(null);
  });

  test('initialize sets up notifications correctly', () async {
    await messagingService.initialize();

    verify(mockNotificationsPlugin.initialize(any)).called(1);
    expect(messagingService.channel.name, 'High Importance Notifications');
    expect(messagingService.channel.importance, Importance.max);
  });
}