package han.anthony.anthonydemo.activity_permission.view;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import han.anthony.anthonydemo.R;
import han.anthony.anthonydemo.activity_main.myLogger.L;
import han.anthony.anthonydemo.activity_permission.presenter.LocationPresenter;

/**
 * Created by senior on 2016/11/25.
 */

public class RequestPermissionActivity extends AppCompatActivity implements ILocationView {
    public final int REQUEST_CODE_LOCATION=1;
    private LocationPresenter mLocationPresenter;
    @BindView(R.id.btn_location)
    Button mBtnLocation;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permission);
        mLocationPresenter=new LocationPresenter(this);
        ButterKnife.bind(this);
        setTitle("动态获得权限");
    }
    
    public void doClick(View v){
        switch (v.getId()){
            //定位相关
            case R.id.btn_location:
                if(!checkPermission(Manifest.permission.ACCESS_FINE_LOCATION)){
                    //没有定位权限,申请权限
                    requestPermissionWith(new String[]{Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_CODE_LOCATION);
                }else {
                    mLocationPresenter.startLocation(this);
                }
                break;
            case R.id.btn_group_calendar:
                if(!checkPermission(Manifest.permission.WRITE_CALENDAR)){
                    requestPermissionWith(new String[]{Manifest.permission.READ_CALENDAR},2);
                }
                break;
            case R.id.btn_group_storage:
                if(!checkPermission(Manifest.permission.READ_EXTERNAL_STORAGE)){
                    requestPermissionWith(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},3);
                }
                break;
            case R.id.btn_camera_sensors:
                if(!checkPermission(Manifest.permission.CAMERA)&!checkPermission(Manifest.permission.BODY_SENSORS)){
                    requestPermissionWith(new String[]{Manifest.permission.CAMERA,Manifest.permission.BODY_SENSORS},4);
                }

                break;

        }
    }

    /**
     * 接收用户操作的结果
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        for(int i=0;i<grantResults.length;i++){
            if(grantResults[i]==PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this, "获得"+permissions[i]+"权限成功", Toast.LENGTH_SHORT).show();
                if(requestCode==REQUEST_CODE_LOCATION){
                    mLocationPresenter.startLocation(this);
                }
            }else {
                Toast.makeText(this, "获得"+permissions[i]+"权限失败", Toast.LENGTH_SHORT).show();
            }
        }
    }

    //请求获得相关的权限
    private void requestPermissionWith(String[] accessFineLocations,int requestCode) {
        if(Build.VERSION.SDK_INT<23){
            return;
        }
        requestPermissions(accessFineLocations,requestCode);
    }

    //检测是否拥有权限
    @TargetApi(23)  //checkSelfPermission(permission)
    private boolean checkPermission(String permission) {
        if(Build.VERSION.SDK_INT<23||checkSelfPermission(permission)== PackageManager.PERMISSION_GRANTED){
            Toast.makeText(this, "已经拥有"+permission+"的权限", Toast.LENGTH_SHORT).show();
            return true;
        }

       return false;
    }

    /**
     * 在UI线程中更新
     */
    @Override
    public void showLocation(final String locationMessage) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mBtnLocation.setText(locationMessage);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLocationPresenter.onDestroy();
    }
}
