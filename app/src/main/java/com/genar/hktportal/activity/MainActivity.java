package com.genar.hktportal.activity;

import android.Manifest;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.genar.hktportal.R;
import com.genar.hktportal.helper.Utils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class MainActivity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks {

    private static final int CAMERA_REQUEST = 1457;
    @BindView(R.id.main_btnaddknn)
    Button btnAddKnn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
    }

    @OnClick({R.id.main_btnaddknn})
    public void onItemClick(View v) {
        switch(v.getId()){
            case R.id.main_btnaddknn:
                cameraPermissionRequest();
                break;
        }
    }

    @AfterPermissionGranted(CAMERA_REQUEST)
    private void cameraPermissionRequest() {
        if (EasyPermissions.hasPermissions(this, Manifest.permission.CAMERA)) {
            Utils.startActivityWithoutFinish(this, AddKnnActivity.class);
        } else {
            // Request one permission
            EasyPermissions.requestPermissions(this, getString(R.string.rationale_camera),
                    CAMERA_REQUEST, Manifest.permission.CAMERA);
        }
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        Utils.startActivityWithoutFinish(this, AddKnnActivity.class);
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
            Toast.makeText(this, "Bu özelliği kullanmak için kamera izni gerekiyor.", Toast.LENGTH_SHORT).show();
    }
}
