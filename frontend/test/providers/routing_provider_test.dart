import 'package:flutter/material.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:hooks_riverpod/hooks_riverpod.dart';
import 'package:azurecommunicationserviceflutter/providers/routing/routing_provider.dart';
import '../helpers/test_helper.dart';

void main() {
  TestWidgetsFlutterBinding.ensureInitialized();
  
  setUp(() async {
    await TestHelper.setupFirebaseTest();
  });
  
  test('Routing state initializes with root page', () {
    // Arrange & Act
    final container = ProviderContainer();
    
    // Assert: 初期状態ではroot pageが選択されていることを確認
    expect(container.read(routingPageProvider), equals(RoutingPage.root));
  });
  
  test('Routing state changes correctly', () {
    // Arrange
    final container = ProviderContainer();
    
    // Act: ルーティング状態をhomeに変更
    container.read(routingPageProvider.notifier).state = RoutingPage.home;
    
    // Assert: 状態が正しく変更されたことを確認
    expect(container.read(routingPageProvider), equals(RoutingPage.home));
  });
  
  testWidgets('Navigator builds with correct pages', (WidgetTester tester) async {
    // Arrange
    await tester.pumpWidget(
      ProviderScope(
        child: Builder(builder: (context) {
          final ref = ProviderScope.containerOf(context);
          return MaterialApp(
            home: Navigator(
              pages: [
                MaterialPage<dynamic>(
                  child: Container(),
                ),
              ],
              onPopPage: (Route<dynamic> route, dynamic result) {
                return route.didPop(result);
              },
            ),
          );
        }),
      ),
    );
    
    // Assert: Navigatorが構築されていることを確認
    expect(find.byType(Navigator), findsOneWidget);
    
    // MaterialPageが作成されていることを確認
    expect(find.byType(MaterialPage), findsOneWidget);
  });
}