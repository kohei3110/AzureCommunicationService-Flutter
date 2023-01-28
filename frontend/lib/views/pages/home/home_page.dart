import 'package:azurecommunicationserviceflutter/main.dart';
import 'package:flutter/material.dart';
import 'package:azurecommunicationserviceflutter/views/widgets/abstract/root_tab_abstract.dart';
// ignore: depend_on_referenced_packages, implementation_imports
import 'package:flutter_riverpod/src/consumer.dart';
import 'package:flutter_callkit_incoming/flutter_callkit_incoming.dart';

class HomePage extends RootPageWidget {
  const HomePage({super.key});

  @override
  Widget build(BuildContext context, WidgetRef ref) {
    return Scaffold(
        appBar: AppBar(
          shape: const Border(bottom: BorderSide(color: Colors.white)),
          backgroundColor: Colors.white,
          title: const Text(
            "ホーム",
            style: TextStyle(color: Colors.black),
          ),
        ),
        body: Center(
          child: OutlinedButton(
            onPressed: () {
              // FIXME: IDを固定しているので、実際には動的に変更する必要がある
              showCallkitIncoming("111");
            },
            child: const Text("通話を開始する"),
          ),
        ));
  }
}
