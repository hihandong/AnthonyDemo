package han.anthony.anthonydemo.activity_permission.mode;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

import han.anthony.anthonydemo.activity_main.myLogger.L;

/**
 * Created by senior on 2016/11/26.
 * 获得用户的经度和维度
 */

public class LocationHelper implements ILocationHelper,LocationListener {
    private LocationApiHelper mMessageHelper;
    public LocationHelper(){
        mMessageHelper=new LocationApiHelper();
    }
    @Override
    public void startLocationMessage(Context context) {
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        LocationProvider gpsProvider = locationManager.getProvider(LocationManager.GPS_PROVIDER);
        LocationProvider netProvider = locationManager.getProvider(LocationManager.NETWORK_PROVIDER);
        //检测是否拥有权限
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            L.e("没有定位权限");
            return;
        }

        if (netProvider != null) {
           // locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 60_000, 10, this);
            locationManager.requestSingleUpdate(LocationManager.NETWORK_PROVIDER,this, Looper.myLooper());
            Location location=locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            requestMessage(location);
        }
         if (gpsProvider != null) {
           // locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 60_000, 10, this);
             locationManager.requestSingleUpdate(LocationManager.GPS_PROVIDER,this, Looper.getMainLooper());
             Location location=locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
             requestMessage(location);
        } else {
            //无法定位：1、提示用户打开定位服务；2、跳转到设置界面
            Toast.makeText(context, "无法定位，请打开定位服务", Toast.LENGTH_SHORT).show();
            Intent i = new Intent();
            i.setAction(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            context.startActivity(i);
        }
    }

    //请求获得街道信息
    private void requestMessage(Location location){
        if (location!=null){
            mMessageHelper.requestMessageFromNet(location.getLongitude(),location.getLatitude());
        }
    }

    //设置网络返回数据的监听器
    @Override
    public void setOnMessageResponseListener(OnMessageResponseListener listener) {
        mMessageHelper.setOnMessageResponseListener(listener);
    }


    /**
     * LocationListener 实现的方法
     */
    @Override
    public void onLocationChanged(Location location) {
        requestMessage(location);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
