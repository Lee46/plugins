// Copyright 2013 The Flutter Authors. All rights reserved.
// Use of this source code is governed by a BSD-style license that can be
// found in the LICENSE file.

package io.flutter.plugins.camerax;

import android.app.Activity;
import io.flutter.plugins.camerax.CameraPermissionsManager.PermissionsRegistry;
import io.flutter.plugins.camerax.GeneratedCameraXLibrary.SystemServicesHostApi;
import io.flutter.plugin.common.BinaryMessenger;

public class SystemServicesHostApiImpl implements SystemServicesHostApi {
    private final BinaryMessenger binaryMessenger;
    private final InstanceManager instanceManager;

    private Activity activity;
    private PermissionsRegistry permissionsRegistry;


    public SystemServicesHostApiImpl(
        BinaryMessenger binaryMessenger, InstanceManager instanceManager) {
      this.binaryMessenger = binaryMessenger;
      this.instanceManager = instanceManager;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public void setPermissionsRegistry(PermissionsRegistry permissionsRegistry) {
        this.permissionsRegistry = permissionsRegistry;
    }

    @Override
    public Boolean requestCameraPermissions(Boolean enableAudio) {
        CameraPermissionsManager cameraPermissionsManager = new CameraPermissionsManager();
        cameraPermissionsManager.requestPermissions(
            activity,
            permissionsRegistry,
            enableAudio,
            (String errCode, String errDesc) -> {
                //TODO(camsim99): Actually handle this.
                final SystemServicesFlutterApiImpl api = new SystemServicesFlutterApiImpl(binaryMessenger, instanceManager);
                api.onCameraPermissionsRequestResult("", "", reply -> {});
              if (errCode == null) {
                // return true;
              } else {
                // return false;
              }
            }
        );
        // // TODO(camsim99): Make this method void.
        return true;
    }

    @Override
    public void startListeningForDeviceOrientationChange(Boolean isFrontFacing, Long sensorOrientation) {
        DeviceOrientationManager deviceOrientationManager = new DeviceOrientationManager(activity, isFrontFacing, sensorOrientation.intValue(),
        (String newOrientation) -> {
          final SystemServicesFlutterApiImpl api = new SystemServicesFlutterApiImpl(binaryMessenger, instanceManager);
          api.onDeviceOrientationChanged(newOrientation, reply -> {});
        });
        deviceOrientationManager.start();
    }

}
