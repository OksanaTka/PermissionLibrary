package com.example.permissionslibrary.permissions;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.util.Log;

import androidx.activity.result.ActivityResultLauncher;
import androidx.core.content.ContextCompat;

import com.example.permissionslibrary.classes.All_Permissions;

public class Contacts_Permission {

    private ActivityResultLauncher<String[]> requestContactsPermissionLauncher;
    private Context context;
    private final String[] CONTACTS__PERMISSION;

    public Contacts_Permission() {
        CONTACTS__PERMISSION = new String[]{All_Permissions.READ_CONTACTS};
    }

    public Contacts_Permission(Context context) {
        this.context = context;
        CONTACTS__PERMISSION = new String[]{All_Permissions.READ_CONTACTS};
    }

    public void getContacts() {
        Log.d("CONTACTS", "getContacts: SUCCESS !");
    }

    public void getPermission() {
        requestContactsPermission();
    }


    public void setRequestContactsPermissionLauncher(ActivityResultLauncher<String[]> requestContactsPermissionLauncher) {
        this.requestContactsPermissionLauncher = requestContactsPermissionLauncher;
    }

    public void requestContactsPermission() {
        if (!(ContextCompat.checkSelfPermission(context, Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED)) {
            requestContactsPermissionLauncher.launch(CONTACTS__PERMISSION);
        } else {
            getContacts();
        }

    }
}
