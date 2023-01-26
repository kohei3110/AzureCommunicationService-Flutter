import 'package:hooks_riverpod/hooks_riverpod.dart';

final routingPageProvider = StateProvider.autoDispose<RoutingPage>((ref) {
  return RoutingPage.root;
});

enum RoutingPage { root, home }
