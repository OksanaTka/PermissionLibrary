package com.example.permissionslibrary.permissions;

import android.content.Context;
import android.content.pm.PackageManager;
import android.util.Log;

import androidx.activity.result.ActivityResultLauncher;
import androidx.core.content.ContextCompat;

import com.example.permissionslibrary.classes.All_Permissions;

public class SMS_Permission {
    private ActivityResultLauncher<String[]> requestSMSPermissionLauncher;
    private Context context;
    private final String[] SMS_PERMISSION;

    public SMS_Permission() {
        SMS_PERMISSION = new String[]{All_Permissions.READ_SMS};
    }

    public SMS_Permission(Context context) {
        this.context = context;
        SMS_PERMISSION = new String[]{All_Permissions.READ_SMS};
    }

    public void getPermission() {
        requestSMSPermission();
    }

    public void setRequestSMSPermissionLauncher(ActivityResultLauncher<String[]> requestSMSPermissionLauncher) {
        this.requestSMSPermissionLauncher = requestSMSPermissionLauncher;
    }

    /**
     * request sms permission
     */
    public void requestSMSPermission() {
        if (!(ContextCompat.checkSelfPermission(context, All_Permissions.READ_SMS) == PackageManager.PERMISSION_GRANTED)) {
            requestSMSPermissionLauncher.launch(SMS_PERMISSION);
        }
    }
}
