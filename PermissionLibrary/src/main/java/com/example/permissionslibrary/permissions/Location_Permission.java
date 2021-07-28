package com.example.permissionslibrary.permissions;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.util.Log;

import androidx.activity.result.ActivityResultLauncher;
import androidx.core.content.ContextCompat;

import com.example.permissionslibrary.classes.All_Permissions;

public class Location_Permission {

    private Context context;
    private ActivityResultLauncher<String[]> requestLocationPermissionLauncher;
    private String[] location_permission;

    public Location_Permission() {
        location_permission = new String[1];
    }

    public Location_Permission(Context context) {
        this.context = context;
        location_permission = new String[1];
    }

    public void getPermission() {
        requestLocationPermission();
    }

    public void setRequestCameraPermissionLauncher(ActivityResultLauncher<String[]> requestCameraPermissionLauncher) {
        this.requestLocationPermissionLauncher = requestCameraPermissionLauncher;
    }

    public void requestFirstLocationPermission() {
        location_permission[0] = All_Permissions.LOCATION;
        requestLocationPermissionLauncher.launch(location_permission);
    }

    public void requestSecondLocationPermission() {
        location_permission[0] = All_Permissions.LOCATION_BACKGROUND;
        requestLocationPermissionLauncher.launch(location_permission);

    }

    /**
     * request location permission
     */
    public void requestLocationPermission() {
        boolean per1 = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED;
        boolean per2 = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
        boolean p1 = android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.Q;
        boolean p2 = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_BACKGROUND_LOCATION) == PackageManager.PERMISSION_GRANTED;
        boolean per3 = p1 || p2;

        if (!per1 && !per2) {
            requestFirstLocationPermission();

        } else if (!per3) {
            requestSecondLocationPermission();
        }
    }


}


