package com.example.permissionslibrary.classes;

import android.Manifest;

public final class All_Permissions {
    public static final String LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    public static final String LOCATION_BACKGROUND = Manifest.permission.ACCESS_BACKGROUND_LOCATION;
    public static final String CAMERA = Manifest.permission.CAMERA;
    public static final String READ_CONTACTS = Manifest.permission.READ_CONTACTS;
    public static final String MICROPHONE = Manifest.permission.RECORD_AUDIO;
    public static final String READ_CALENDAR = Manifest.permission.READ_CALENDAR;
    public static final String READ_SMS = Manifest.permission.READ_SMS;
    public static final String[] ALL_PERMISSIONS = new String[]{LOCATION,LOCATION_BACKGROUND,CAMERA, READ_CONTACTS, MICROPHONE,READ_CALENDAR,READ_SMS};

    private All_Permissions() {
    }
}
