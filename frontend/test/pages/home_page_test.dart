import 'package:flutter/material.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:hooks_riverpod/hooks_riverpod.dart';
import 'package:azurecommunicationserviceflutter/views/pages/home/home_page.dart';
import '../helpers/test_helper.dart';

void main() {
  TestWidgetsFlutterBinding.ensureInitialized();
  
  setUp(() async {
    await TestHelper.setupFirebaseTest();
  });
  
  testWidgets('HomePage renders with correct title and button', (WidgetTester tester) async {
    // Arrange: Widgetをレンダリング
    await tester.pumpWidget(
      const ProviderScope(
        child: MaterialApp(
          home: HomePage(),
        ),
      ),
    );
    await tester.pumpAndSettle();
    
    // Assert: ホーム画面のタイトルが表示されていることを確認
    expect(find.text('ホーム'), findsOneWidget);
    
    // Assert: CallkitIncomingボタンが表示されていることを確認
    expect(find.text('CallkitIncoming'), findsOneWidget);
    expect(find.byType(OutlinedButton), findsOneWidget);
  });
  
  testWidgets('Tapping the CallkitIncoming button is possible', (WidgetTester tester) async {    
    // Arrange: Widgetをレンダリング
    await tester.pumpWidget(
      const ProviderScope(
        child: MaterialApp(
          home: HomePage(),
        ),
      ),
    );
    await tester.pumpAndSettle();
    
    // Act: ボタンをタップ - 現在の実装では動作を確認するだけ
    await tester.tap(find.byType(OutlinedButton));
    await tester.pumpAndSettle();
    
    // Note: showCallkitIncomingがグローバル関数のため、直接の検証は難しいですが
    // タップ操作自体は可能であることを確認します
    // 将来的には、関数をクラスのメソッドに移すかモック可能な設計に変更することをお勧めします
  });
}