package com.example.permissionsapp;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.permissionslibrary.classes.All_Permissions;
import com.example.permissionslibrary.classes.Permission_Manager;
import com.example.permissionslibrary.utils.MyScreenUtils;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.Map;

public class MainActivity extends AppCompatActivity {


    private MaterialButton main_BTN_done;
    private CheckBox main_SBTN_contacts;
    private CheckBox main_SBTN_camera;
    private CheckBox main_SBTN_microphone;
    private CheckBox main_SBTN_location;
    private CheckBox main_SBTN_calendar;
    private CheckBox main_SBTN_sms;

    private TextView main_LBL_camera_granted;
    private TextView main_LBL_location_granted;
    private TextView main_LBL_microphone_granted;
    private TextView main_LBL_contacts_granted;
    private TextView main_LBL_calendar_granted;
    private TextView main_LBL_sms_granted;

    private boolean openApp = false;

    private Permission_Manager permission_manager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MyScreenUtils.hideSystemUI2(this);


        findViews();
        initPermissionManage();
        readCheckBox();

    }

    private void initPermissionManage() {
        permission_manager = new Permission_Manager(this, MainActivity.this,requestPermissionLauncher,manuallyPermissionResultLauncher);
    }


    private void readCheckBox() {
        main_BTN_done.setOnClickListener(v -> {
            permission_manager.clearArrayList();
            permission_manager.clearGrantedPermissionList();
            if (main_SBTN_contacts.isChecked()) {
                permission_manager.addPermissionToList(All_Permissions.READ_CONTACTS);
            }
            if (main_SBTN_camera.isChecked()) {
                permission_manager.addPermissionToList(All_Permissions.CAMERA);

            }
            if (main_SBTN_location.isChecked()) {
                permission_manager.addPermissionToList(All_Permissions.LOCATION);

            }
            if (main_SBTN_microphone.isChecked()) {
                permission_manager.addPermissionToList(All_Permissions.MICROPHONE);
            }
            if (main_SBTN_calendar.isChecked()) {
                permission_manager.addPermissionToList(All_Permissions.READ_CALENDAR);
            }
            if (main_SBTN_sms.isChecked()) {
                permission_manager.addPermissionToList(All_Permissions.READ_SMS);
            }
            permission_manager.getPermissions();

        });
    }


    private ActivityResultLauncher<String[]> requestPermissionLauncher = registerForActivityResult(
            new ActivityResultContracts.RequestMultiplePermissions(), (Map<String, Boolean> isGranted) -> {
                for (Map.Entry<String, Boolean> x : isGranted.entrySet()) {
                    permission_manager.checkPermission(x);
                    changeTextAndCheckBoxStatus(permission_manager.getGrantedPermissionList());
                }
            });

    private ActivityResultLauncher<Intent> manuallyPermissionResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    permission_manager.getPermissions();
                }

            });

    private void changeTextAndCheckBoxStatus(ArrayList<String> allGrantedPermissions) {
        for (String permissionType : allGrantedPermissions) {
            switch (permissionType) {
                case All_Permissions.MICROPHONE: {
                    textAndCheckBoxManage(main_LBL_microphone_granted, main_SBTN_microphone);
                    break;
                }

                case All_Permissions.LOCATION_BACKGROUND: {
                    textAndCheckBoxManage(main_LBL_location_granted, main_SBTN_location);
                }
                case All_Permissions.LOCATION: {
                    break;
                }

                case All_Permissions.CAMERA: {
                    textAndCheckBoxManage(main_LBL_camera_granted, main_SBTN_camera);
                    break;
                }

                case All_Permissions.READ_CONTACTS: {
                    textAndCheckBoxManage(main_LBL_contacts_granted, main_SBTN_contacts);
                    break;
                }
                case All_Permissions.READ_CALENDAR: {
                    textAndCheckBoxManage(main_LBL_calendar_granted, main_SBTN_calendar);
                    break;
                }
                case All_Permissions.READ_SMS: {
                    textAndCheckBoxManage(main_LBL_sms_granted, main_SBTN_sms);
                    break;
                }

                default:
                    break;
            }
        }
    }

    private void textAndCheckBoxManage(TextView tv, CheckBox cb) {
        tv.setText(Permission_Manager.IS_GRANTED);
        tv.setTextColor(getResources().getColor(R.color.blue));
        cb.setChecked(false);
        cb.setEnabled(false);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!openApp) {
            changeTextAndCheckBoxStatus(permission_manager.checkGrantedPermissionsOnStart());
            openApp = true;
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        MyScreenUtils.hideSystemUI2(this);
    }

    private void findViews() {
        main_LBL_camera_granted = findViewById(R.id.main_LBL_camera_granted);
        main_LBL_microphone_granted = findViewById(R.id.main_LBL_microphone_granted);
        main_LBL_contacts_granted = findViewById(R.id.main_LBL_contacts_granted);
        main_LBL_location_granted = findViewById(R.id.main_LBL_location_granted);
        main_LBL_calendar_granted = findViewById(R.id.main_LBL_calendar_granted);
        main_LBL_sms_granted = findViewById(R.id.main_LBL_sms_granted);

        main_BTN_done = findViewById(R.id.main_BTN_done);
        main_SBTN_contacts = findViewById(R.id.main_SBTN_contacts);
        main_SBTN_camera = findViewById(R.id.main_SBTN_camera);
        main_SBTN_microphone = findViewById(R.id.main_SBTN_microphone);
        main_SBTN_location = findViewById(R.id.main_SBTN_location);
        main_SBTN_calendar = findViewById(R.id.main_SBTN_calendar);
        main_SBTN_sms = findViewById(R.id.main_SBTN_sms);

    }
}