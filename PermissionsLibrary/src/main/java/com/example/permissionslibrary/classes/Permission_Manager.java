package com.example.permissionslibrary.classes;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;

import androidx.activity.result.ActivityResultLauncher;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.permissionslibrary.permissions.Calendar_Permission;
import com.example.permissionslibrary.permissions.Camera_Permission;
import com.example.permissionslibrary.permissions.Contacts_Permission;
import com.example.permissionslibrary.permissions.Location_Permission;
import com.example.permissionslibrary.permissions.Microphone_Permission;
import com.example.permissionslibrary.permissions.SMS_Permission;
import com.example.permissionslibrary.utils.Dialog;

import java.util.ArrayList;
import java.util.Map;

public class Permission_Manager {


    private Camera_Permission camera_permission;
    private Contacts_Permission contacts_permission;
    private Location_Permission location_permission;
    private Microphone_Permission microphone_permission;
    private Calendar_Permission calendar_permission;
    private SMS_Permission sms_permission;

    private Context context;
    private Activity activity;

    private Dialog manuallyPermissionMessage;

    private ArrayList<String> selectedStrings;
    private ArrayList<String> grantedPermissionList;
    private String[] permissionsList;

    private ActivityResultLauncher<String[]> requestPermissionLauncher;
    private ActivityResultLauncher<Intent> manuallyPermissionResultLauncher;
    public static final String IS_GRANTED = "IS GRANTED";

    public Permission_Manager() {
    }

    public Permission_Manager(Activity activity, Context context) {
        this.context = context;
        this.activity = activity;
        manuallyPermissionMessage = new Dialog(this.context);
        selectedStrings = new ArrayList<>();
        grantedPermissionList = new ArrayList<>();
        initClass();
    }

    public void setRequestPermissionLauncher(ActivityResultLauncher<String[]> requestPermissionLauncher) {
        this.requestPermissionLauncher = requestPermissionLauncher;
    }

    public void setManuallyPermissionResultLauncher(ActivityResultLauncher<Intent> manuallyPermissionResultLauncher) {
        this.manuallyPermissionResultLauncher = manuallyPermissionResultLauncher;
    }


    public void clearArrayList() {
        selectedStrings.clear();
    }

    public void clearGrantedPermissionList() {
        grantedPermissionList.clear();
    }


    public ArrayList<String> getGrantedPermissionList() {
        return grantedPermissionList;
    }

    public void addToArrayList(String per) {
        selectedStrings.add(per);
    }

    public void convertArrayListToStringArray() {
        permissionsList = selectedStrings.toArray(new String[0]);
    }

    private void initClass() {
        microphone_permission = new Microphone_Permission(context);
        camera_permission = new Camera_Permission(context);
        contacts_permission = new Contacts_Permission(context);
        location_permission = new Location_Permission(context);
        calendar_permission = new Calendar_Permission(context);
        sms_permission = new SMS_Permission(context);

    }

    public void getPermissions() {
        requestPermissionLauncher.launch(permissionsList);
    }

    public void requestPermission(String permissionType) {
        switch (permissionType) {
            case All_Permissions.MICROPHONE:
                microphone_permission.getPermission();
                break;

            case All_Permissions.LOCATION_BACKGROUND:
            case All_Permissions.LOCATION:
                location_permission.getPermission();
                break;

            case All_Permissions.CAMERA:
                camera_permission.getPermission();
                break;

            case All_Permissions.READ_CONTACTS:
                contacts_permission.getPermission();
                break;

            case All_Permissions.READ_CALENDAR:
                calendar_permission.getPermission();
                break;

            case All_Permissions.READ_SMS:
                sms_permission.getPermission();
                break;

            default:
                break;
        }
    }

    public void initLaunchers() {
        location_permission.setRequestCameraPermissionLauncher(requestPermissionLauncher);
        contacts_permission.setRequestContactsPermissionLauncher(requestPermissionLauncher);
        camera_permission.setRequestCameraPermissionLauncher(requestPermissionLauncher);
        microphone_permission.setRequestMicrophonePermissionLauncher(requestPermissionLauncher);
        calendar_permission.setRequestCalendarPermissionLauncher(requestPermissionLauncher);
        sms_permission.setRequestSMSPermissionLauncher(requestPermissionLauncher);
    }

    private void getPermissionManually(String permissionType) {
        manuallyPermissionMessage.getPermissionManually(permissionType, manuallyPermissionResultLauncher);
    }

    public void checkPermission(Map.Entry<String, Boolean> x) {
        if (x.getValue()) {
            grantedPermissionList.add(x.getKey());
            requestPermission(x.getKey());
        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, x.getKey())) {
                requestPermission(x.getKey());
            } else {
                getPermissionManually(x.getKey());
            }
        }
    }


    public ArrayList<String> checkGrantedPermissionsOnStart() {
        ArrayList<String> grantedPermissionArray = new ArrayList<>();
        for (String permissionType : All_Permissions.ALL_PERMISSIONS
        ) {
            if (ContextCompat.checkSelfPermission(context, permissionType)
                    == PackageManager.PERMISSION_GRANTED) {
                grantedPermissionArray.add(permissionType);
            }
        }
        return grantedPermissionArray;
    }
}
