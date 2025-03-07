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
  
  testWidgets('HomePage renders correctly on small screen', (WidgetTester tester) async {
    // 小さい画面サイズを設定（Phone）
    tester.binding.window.physicalSizeTestValue = const Size(360, 640);
    tester.binding.window.devicePixelRatioTestValue = 1.0;
    
    // Arrange: ホーム画面をレンダリング
    await tester.pumpWidget(
      const ProviderScope(
        child: MaterialApp(
          home: HomePage(),
        ),
      ),
    );
    await tester.pumpAndSettle();
    
    // Assert: 基本的なUI要素が表示されることを確認
    expect(find.text('ホーム'), findsOneWidget);
    expect(find.text('CallkitIncoming'), findsOneWidget);
    
    // 画面サイズをリセット
    addTearDown(tester.binding.window.clearPhysicalSizeTestValue);
    addTearDown(tester.binding.window.clearDevicePixelRatioTestValue);
  });
  
  testWidgets('HomePage renders correctly on large screen', (WidgetTester tester) async {
    // 大きい画面サイズを設定（Tablet）
    tester.binding.window.physicalSizeTestValue = const Size(1024, 768);
    tester.binding.window.devicePixelRatioTestValue = 1.0;
    
    // Arrange: ホーム画面をレンダリング
    await tester.pumpWidget(
      const ProviderScope(
        child: MaterialApp(
          home: HomePage(),
        ),
      ),
    );
    await tester.pumpAndSettle();
    
    // Assert: 基本的なUI要素が表示されることを確認
    expect(find.text('ホーム'), findsOneWidget);
    expect(find.text('CallkitIncoming'), findsOneWidget);
    
    // 画面サイズをリセット
    addTearDown(tester.binding.window.clearPhysicalSizeTestValue);
    addTearDown(tester.binding.window.clearDevicePixelRatioTestValue);
  });
  
  testWidgets('HomePage handles orientation changes correctly', (WidgetTester tester) async {
    // 縦向き画面サイズを設定
    tester.binding.window.physicalSizeTestValue = const Size(480, 800);
    tester.binding.window.devicePixelRatioTestValue = 1.0;
    
    // Arrange: ホーム画面をレンダリング（縦向き）
    await tester.pumpWidget(
      const ProviderScope(
        child: MaterialApp(
          home: HomePage(),
        ),
      ),
    );
    await tester.pumpAndSettle();
    
    // Assert: 縦向きでUI要素が表示されることを確認
    expect(find.text('ホーム'), findsOneWidget);
    expect(find.text('CallkitIncoming'), findsOneWidget);
    
    // 画面の向きを横に変更
    tester.binding.window.physicalSizeTestValue = const Size(800, 480);
    await tester.pumpAndSettle();
    
    // Assert: 横向きでもUI要素が表示されることを確認
    expect(find.text('ホーム'), findsOneWidget);
    expect(find.text('CallkitIncoming'), findsOneWidget);
    
    // 画面サイズをリセット
    addTearDown(tester.binding.window.clearPhysicalSizeTestValue);
    addTearDown(tester.binding.window.clearDevicePixelRatioTestValue);
  });
}