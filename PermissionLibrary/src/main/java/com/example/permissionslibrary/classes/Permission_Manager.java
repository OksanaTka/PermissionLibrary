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

/**
 * Class to simplify the management of Android runtime permissions
 */

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


    public Permission_Manager(Activity activity, Context context,ActivityResultLauncher<String[]> requestPermissionLauncher,ActivityResultLauncher<Intent> manuallyPermissionResultLauncher) {
        this.context = context;
        this.activity = activity;
        this.requestPermissionLauncher = requestPermissionLauncher;
        this.manuallyPermissionResultLauncher = manuallyPermissionResultLauncher;
        manuallyPermissionMessage = new Dialog(this.context);
        selectedStrings = new ArrayList<>();
        grantedPermissionList = new ArrayList<>();
        initClass();
        initLaunchers();
    }

    /**
     * Clear all selected permissions from list
     */
    public void clearArrayList() {
        selectedStrings.clear();
    }

    /**
     * Clear all granted permissions from list
     */
    public void clearGrantedPermissionList() {
        grantedPermissionList.clear();
    }


    public ArrayList<String> getGrantedPermissionList() {
        return grantedPermissionList;
    }

    /**
     * Add permission to list
     * @param permissionType an android permission
     */
    public void addPermissionToList(String permissionType) {
        selectedStrings.add(permissionType);
    }

    /**
     * Create String array of permissions
     * @param permissionsList an android String array of permissions
     */
    public void setPermissionsList(String[] permissionsList) {
        this.permissionsList = permissionsList;
    }

    /**
     * Convert from ArrayList to String array
     */
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

    /**
     * Get permissions
     */
    public void getPermissions() {
        convertArrayListToStringArray();
        requestPermissionLauncher.launch(permissionsList);
    }


    /**
     * Check type of requested permission and get permission
     * @param permissionType an android permission
     */
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

    /**
     * Get permission manually
     * @param permissionType an android permission
     */
    private void getPermissionManually(String permissionType) {
        manuallyPermissionMessage.getPermissionManually(permissionType, manuallyPermissionResultLauncher);
    }

    /**
     * Check if permissions is granted
     * Get permission
     * @param x permission type and its boolean status
     */

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

    /**
     *Check which permissions from all the permissions is granted.
     * @return ArrayList of granted permissions
     */
    public ArrayList<String> checkGrantedPermissions() {
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

    /**
     *Check if single permission is granted
     * @param permissionType an android permission
     * @return true/false
     */
    public boolean checkIfPermissionIsGranted(String permissionType) {
            if (ContextCompat.checkSelfPermission(context, permissionType)
                    == PackageManager.PERMISSION_GRANTED) {
                return true;
            }
        return false;
    }
}
