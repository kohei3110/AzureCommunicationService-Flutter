import 'package:flutter_test/flutter_test.dart';
import 'package:hooks_riverpod/hooks_riverpod.dart';
import 'package:flutter/material.dart';
import 'package:azurecommunicationserviceflutter/main.dart';
import 'helpers/test_helper.dart';

void main() {
  TestWidgetsFlutterBinding.ensureInitialized();

  setUp(() async {
    await TestHelper.setupFirebaseTest();
  });

  testWidgets('App initializes with correct title and theme', (WidgetTester tester) async {
    await tester.pumpWidget(
      const ProviderScope(
        child: MyApp(),
      ),
    );
    await tester.pumpAndSettle();

    // Test home page title
    expect(find.text('ホーム'), findsOneWidget);
    
    // Test theme (using app bar color as an indicator)
    final AppBar appBar = tester.widget<AppBar>(find.byType(AppBar));
    expect(appBar.backgroundColor, Colors.white);
  });
}
