import 'package:firebase_core/firebase_core.dart';
import 'package:firebase_core_platform_interface/firebase_core_platform_interface.dart';
import 'package:flutter/material.dart';
import 'package:flutter_test/flutter_test.dart';

class TestHelper {
  static Future<void> setupFirebaseTest() async {
    TestWidgetsFlutterBinding.ensureInitialized();
    
    setupFirebaseMocks();
    
    await Firebase.initializeApp();
  }
}

// Firebaseモックのセットアップ
void setupFirebaseMocks() {
  // セットアップ済みのフラグ
  final isSetup = TestFirebaseCoreHostApi.setup;
  if (isSetup) return;
  
  // Firebase CoreのモッキングAPI
  TestFirebaseCoreHostApi.setup = true;
}

// TestFirebaseCoreHostApi実装
class TestFirebaseCoreHostApi implements FirebaseCoreHostApi {
  static bool setup = false;
  static Map<String, dynamic> appConfig = {
    'name': '[DEFAULT]',
    'options': {
      'apiKey': 'test-api-key',
      'appId': 'test-app-id',
      'messagingSenderId': 'test-messaging-sender-id',
      'projectId': 'test-project-id',
    },
  };

  @override
  Future<List<Map<Object?, Object?>>> initializeApp(
      String? appName, Map<String, dynamic> options) async {
    return [appConfig];
  }

  @override
  Future<List<Map<Object?, Object?>>> initializeCore() async {
    return [appConfig];
  }

  @override
  Future<Map<Object?, Object?>?> optionsFromResource() async {
    return appConfig['options'];
  }
}

// ナビゲーター監視用モック
class MockNavigatorObserver extends NavigatorObserver {
  final List<Route<dynamic>?> pushedRoutes = [];
  final List<Route<dynamic>?> poppedRoutes = [];
  
  @override
  void didPush(Route<dynamic> route, Route<dynamic>? previousRoute) {
    pushedRoutes.add(route);
  }
  
  @override
  void didPop(Route<dynamic> route, Route<dynamic>? previousRoute) {
    poppedRoutes.add(route);
  }
}