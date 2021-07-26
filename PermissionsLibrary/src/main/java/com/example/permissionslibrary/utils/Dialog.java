package com.example.permissionslibrary.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;

import androidx.activity.result.ActivityResultLauncher;

import com.example.permissionslibrary.classes.All_Permissions;
import com.example.permissionslibrary.enums.Permissions_Types;


public class Dialog {

    private Context context;


    public Dialog() {
    }

    public Dialog(Context context) {
        this.context = context;
    }

    public void getPermissionManually(String permissionType, ActivityResultLauncher<Intent> manuallyPermissionResultLauncher) {
        String type = getPermissionType(permissionType);
        String message = "Setting screen if user have permanently disable the " + type + " permission by clicking Don't ask again checkbox.";
        AlertDialog alertDialog =
                new AlertDialog.Builder(context)
                        .setMessage(message)
                        .setPositiveButton(context.getString(android.R.string.ok),
                                (dialog, which) -> {
                                    Intent intent = new Intent();
                                    intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                    Uri uri = Uri.fromParts("package", context.getPackageName(), null);
                                    intent.setData(uri);
                                    manuallyPermissionResultLauncher.launch(intent);
                                    dialog.dismiss();
                                }).show();
        alertDialog.setCanceledOnTouchOutside(true);
    }


    private String getPermissionType(String permissionType) {

        switch (permissionType) {
            case All_Permissions.MICROPHONE:
                return Permissions_Types.MICROPHONE.name();

            case All_Permissions.LOCATION_BACKGROUND:
            case All_Permissions.LOCATION:
                return Permissions_Types.LOCATION.name();

            case All_Permissions.CAMERA:
                return Permissions_Types.CAMERA.name();

            case All_Permissions.READ_CONTACTS:
                return Permissions_Types.READ_CONTACTS.name();

            case All_Permissions.READ_CALENDAR:
                return Permissions_Types.READ_CALENDAR.name();

            case All_Permissions.READ_SMS:
                return Permissions_Types.SMS.name();

            default:
                return null;

        }
    }
}
