package com.example.ex03;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import java.util.ArrayList;

public class PermissionActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permission);
        getSupportActionBar().hide();

        if(permissionCheck()){
            Intent intent = new Intent(PermissionActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    //필요한 권한을 확인하고 부여되어 있지 않으면 권한을 요청한다.
    public boolean permissionCheck() {
        String[] permissions= {
                android.Manifest.permission.READ_MEDIA_IMAGES,
                android.Manifest.permission.READ_MEDIA_AUDIO,
                android.Manifest.permission.READ_MEDIA_VIDEO
        };
        ArrayList<String> checkPermission = new ArrayList<>();
        for(String permission:permissions){
            if(ActivityCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                checkPermission.add(permission);
            }
        }
        if(!checkPermission.isEmpty()){
            String[] requestPermission=checkPermission.toArray(new String[checkPermission.size()]);
            ActivityCompat.requestPermissions(this, requestPermission, 100);
            return false;
        }else{
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        boolean isAll = true;
        for(int i=0; i < permissions.length; i++) {
            if(grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                isAll = false;
            }
        }
        if(isAll) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }else {
            finish();
        }
    }
}