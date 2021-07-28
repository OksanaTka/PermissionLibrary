package com.example.permissionslibrary.permissions;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.util.Log;

import androidx.activity.result.ActivityResultLauncher;
import androidx.core.content.ContextCompat;

import com.example.permissionslibrary.classes.All_Permissions;


public class Camera_Permission {

    private ActivityResultLauncher<String[]> requestCameraPermissionLauncher;
    private Context context;
    private final String[] CAMERA_PERMISSION;

    public Camera_Permission() {
        CAMERA_PERMISSION = new String[]{All_Permissions.CAMERA};
    }

    public Camera_Permission(Context context) {
        this.context = context;
        CAMERA_PERMISSION = new String[]{All_Permissions.CAMERA};
    }

    public void getPermission() {
        requestCameraPermission();
    }


    public void setRequestCameraPermissionLauncher(ActivityResultLauncher<String[]> requestCameraPermissionLauncher) {
        this.requestCameraPermissionLauncher = requestCameraPermissionLauncher;
    }

    /**
     * request camera permission
     */
    public void requestCameraPermission() {
        if (!(ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED)) {
            requestCameraPermissionLauncher.launch(CAMERA_PERMISSION);
        }
    }
}
