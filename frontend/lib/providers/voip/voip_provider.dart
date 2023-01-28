import 'package:hooks_riverpod/hooks_riverpod.dart';
import 'package:flutter_webrtc/flutter_webrtc.dart';

import '../../models/session.dart';

final StateProvider<Session> sessionProvider = StateProvider((ref) {
  return Session(sid: '', pid: '');
});

final StateProvider<CallState> callStateProvider = StateProvider((ref) {
  return CallState.CallStateBye;
});

final StateProvider<SignalingState> signalingStateProvider =
    StateProvider((ref) {
  return SignalingState.ConnectionClosed;
});

enum CallState {
  CallStateNew,
  CallStateRinging,
  CallStateInvite,
  CallStateConnected,
  CallStateBye,
}

enum SignalingState {
  ConnectionOpen,
  ConnectionClosed,
  ConnectionError,
}
