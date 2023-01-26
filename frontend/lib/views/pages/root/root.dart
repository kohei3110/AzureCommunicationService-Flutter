import 'package:flutter/material.dart';
import 'package:hooks_riverpod/hooks_riverpod.dart';

import '../../widgets/abstract/root_tab_abstract.dart';
import '../home/home_page.dart';

class RootPage extends HookConsumerWidget {
  const RootPage({super.key, required this.pageList});
  final List<RootPageWidget> pageList;
  static List<RootPageWidget> defaultPageList = [const HomePage()];

  @override
  Widget build(BuildContext context, WidgetRef ref) {
    return Scaffold(
      body: pageList[0],
    );
  }
}
