package com.example.permissionslibrary.permissions;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.util.Log;

import androidx.activity.result.ActivityResultLauncher;
import androidx.core.content.ContextCompat;

import com.example.permissionslibrary.classes.All_Permissions;

public class Microphone_Permission {

    private Context context;
    private ActivityResultLauncher<String[]> requestMicrophonePermissionLauncher;
    private final String[] MICROPHONE_PERMISSION;

    public Microphone_Permission() {
        MICROPHONE_PERMISSION = new String[]{All_Permissions.MICROPHONE};
    }

    public Microphone_Permission(Context context) {
        this.context = context;
        MICROPHONE_PERMISSION = new String[]{All_Permissions.MICROPHONE};
    }

    public void openMicrophone() {
        Log.d("Microphone", "openMicrophone: SUCCESS !");
    }

    public void getPermission() {
        requestRecordPermission();
    }


    public void setRequestMicrophonePermissionLauncher(ActivityResultLauncher<String[]> requestMicrophonePermissionLauncher) {
        this.requestMicrophonePermissionLauncher = requestMicrophonePermissionLauncher;
    }

    private void requestRecordPermission() {
        if (!(ContextCompat.checkSelfPermission(context, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED)) {
            requestMicrophonePermissionLauncher.launch(MICROPHONE_PERMISSION);
        } else {
            openMicrophone();
        }


    }

}
