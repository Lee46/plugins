// Copyright 2013 The Flutter Authors. All rights reserved.
// Use of this source code is governed by a BSD-style license that can be
// found in the LICENSE file.

import 'package:flutter/services.dart' show BinaryMessenger;

import 'android_camera_camerax_flutter_api_impls.dart';
import 'camerax_library.pigeon.dart';
import 'instance_manager.dart';
import 'java_object.dart';

class Camera extends JavaObject {
  /// Constructs a [Camera] that is not automatically attached to a native object.
  Camera.detached(
      {BinaryMessenger? binaryMessenger, InstanceManager? instanceManager})
      : super.detached(
            binaryMessenger: binaryMessenger,
            instanceManager: instanceManager) {
    AndroidCameraXCameraFlutterApis.instance.ensureSetUp();
  }
}

/// Flutter API implementation of [Camera].
class CameraFlutterApiImpl implements CameraFlutterApi {
  /// Constructs a [CameraSelectorFlutterApiImpl].
  CameraFlutterApiImpl({
    this.binaryMessenger,
    InstanceManager? instanceManager,
  }) : instanceManager = instanceManager ?? JavaObject.globalInstanceManager;

  /// Receives binary data across the Flutter platform barrier.
  ///
  /// If it is null, the default BinaryMessenger will be used which routes to
  /// the host platform.
  final BinaryMessenger? binaryMessenger;

  /// Maintains instances stored to communicate with native language objects.
  final InstanceManager instanceManager;

  @override
  void create(int identifier) {
    instanceManager.addHostCreatedInstance(
      Camera.detached(
          binaryMessenger: binaryMessenger, instanceManager: instanceManager),
      identifier,
      onCopy: (Camera original) {
        return Camera.detached(
            binaryMessenger: binaryMessenger, instanceManager: instanceManager);
      },
    );
  }
}
