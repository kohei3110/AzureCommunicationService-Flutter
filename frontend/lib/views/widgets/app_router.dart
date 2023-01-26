import 'package:flutter/material.dart';
import 'package:hooks_riverpod/hooks_riverpod.dart';

import '../../providers/routing/routing_provider.dart';
import '../pages/root/root.dart';

final _navigatorKey = GlobalKey<NavigatorState>();

class AppRouterDelegate extends RouterDelegate<Empty>
    with ChangeNotifier, PopNavigatorRouterDelegateMixin<Empty> {
  AppRouterDelegate(this.ref);

  final WidgetRef ref;

  @override
  final GlobalKey<NavigatorState>? navigatorKey = _navigatorKey;

  RoutingPage get page => ref.watch(routingPageProvider);
  StateController<RoutingPage> get provider {
    return ref.read(routingPageProvider.notifier);
  }

  // ウィジェットツリーを組み立てる
  // ここに Navigator が含まれる。
  @override
  Widget build(BuildContext context) {
    return Navigator(
      key: navigatorKey,
      pages: [
        MaterialPage<dynamic>(
          child: RootPage(
            pageList: RootPage.defaultPageList,
          ),
        )
      ],
      onPopPage: (Route<dynamic> route, dynamic result) {
        return route.didPop(result);
      },
    );
  }

  @override
  Future<void> setNewRoutePath(Empty configuration) async {}
}

class Empty {}
