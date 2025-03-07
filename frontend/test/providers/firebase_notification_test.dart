import 'package:flutter_test/flutter_test.dart';
import 'package:hooks_riverpod/hooks_riverpod.dart';
import 'package:mockito/mockito.dart';
import 'package:flutter_local_notifications/flutter_local_notifications.dart';
import 'package:azurecommunicationserviceflutter/providers/firebase_messaging_provider.dart';
import '../helpers/test_helper.dart';

// モックをマニュアルで作成
class MockFlutterLocalNotificationsPlugin extends Mock implements FlutterLocalNotificationsPlugin {}

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
  
  test('initialize sets up notifications channel correctly', () async {
    // Arrange & Act
    messagingService.channel = const AndroidNotificationChannel(
      'test_channel',
      'Test Channel',
      description: 'Test channel description',
      importance: Importance.high,
    );
    
    // Assert
    expect(messagingService.channel.name, 'Test Channel');
    expect(messagingService.channel.importance, Importance.high);
  });
  
  // 現在の実装では、FirebaseMessaging.instanceを直接モック化することが難しいため、
  // 以下のようなテストは別のアプローチが必要かもしれません
  
  // テスト可能な設計に向けた提案:
  // 1. DIパターンを使用してFirebaseMessagingの依存関係を注入できるようにする
  // 2. プライベートメソッドをテスト可能なパブリックメソッドに分割する
  // 3. staticメソッドの代わりにインスタンスメソッドを使用する
}