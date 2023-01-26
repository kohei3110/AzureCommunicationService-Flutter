import 'package:flutter/material.dart';
import 'package:azurecommunicationserviceflutter/views/widgets/abstract/root_tab_abstract.dart';
// ignore: depend_on_referenced_packages, implementation_imports
import 'package:flutter_riverpod/src/consumer.dart';

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
        child: Text("ホーム"),
      ),
    );
  }
}
