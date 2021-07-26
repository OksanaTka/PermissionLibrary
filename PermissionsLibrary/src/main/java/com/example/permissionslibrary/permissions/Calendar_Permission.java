package com.example.permissionslibrary.permissions;

import android.content.Context;
import android.content.pm.PackageManager;
import android.util.Log;

import androidx.activity.result.ActivityResultLauncher;
import androidx.core.content.ContextCompat;

import com.example.permissionslibrary.classes.All_Permissions;


public class Calendar_Permission {

    private ActivityResultLauncher<String[]> requestCalendarPermissionLauncher;
    private Context context;
    private final String[] CALENDAR_PERMISSION;

    public Calendar_Permission() {
        CALENDAR_PERMISSION = new String[]{All_Permissions.READ_CALENDAR};
    }

    public Calendar_Permission(Context context) {
        this.context = context;
        CALENDAR_PERMISSION = new String[]{All_Permissions.READ_CALENDAR};
    }

    public void readCalendar() {
        Log.d("READ_CALENDAR", "readCalendar: SUCCESS !");
    }

    public void getPermission() {
        requestCalendarPermission();
    }

    public void setRequestCalendarPermissionLauncher(ActivityResultLauncher<String[]> requestCalendarPermissionLauncher) {
        this.requestCalendarPermissionLauncher = requestCalendarPermissionLauncher;
    }

    public void requestCalendarPermission() {
        if (!(ContextCompat.checkSelfPermission(context, All_Permissions.READ_CALENDAR) == PackageManager.PERMISSION_GRANTED)) {
            requestCalendarPermissionLauncher.launch(CALENDAR_PERMISSION);
        } else {
            readCalendar();
        }

    }
}
