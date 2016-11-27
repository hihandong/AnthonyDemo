package han.anthony.anthonydemo.activity_permission.mode;

import android.text.TextUtils;

import com.google.gson.Gson;


import java.io.IOException;

import han.anthony.anthonydemo.activity_main.myLogger.L;
import han.anthony.anthonydemo.beans.LocationJsonBean;
import han.anthony.anthonydemo.util.OkHttpHelper;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by senior on 2016/11/26.
 * 根据传来的经纬度,调用百度API获得街道信息
 */

public class LocationApiHelper implements Callback{
    private ILocationHelper.OnMessageResponseListener mListener;
    private String apiUrl= "http://api.map.baidu.com/geocoder/v2/?ak=nMKkv8g9uwK33UiR5TpsEWZ1hsxeZ1Nj&output=json&pois=0&";
    private OkHttpClient mOkHttpClient;
    private Double mLongitude=0d;
    private Double mLatitude=0d;
    public LocationApiHelper(){
        mOkHttpClient= OkHttpHelper.getInstance().getOkHttpClient();
    }

    public void requestMessageFromNet(Double longitude, Double latitude) {
        mLongitude=longitude;
        mLatitude=latitude;
        String fullUrl = apiUrl + "location=" + latitude + "," + longitude;
        Request request = new Request.Builder().url(fullUrl).build();
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(this);
    }

    @Override
    public void onFailure(Call call, IOException e) {
        L.e(e.getMessage());
    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        if(mListener!=null){
            Gson gson=new Gson();
            String json=response.body().string();
            L.i(json);
            LocationJsonBean locationJson=gson.fromJson(json,LocationJsonBean.class);
            if(locationJson!=null&&locationJson.result!=null){
                String cityMsg=locationJson.result.sematic_description;
                if(!TextUtils.isEmpty(cityMsg)){
                    cityMsg="\n"+cityMsg;
                }
                /**
                 * 把从网络得到的城市信息发送到View
                 */
                mListener.onMessageResponse("经度:"+mLongitude+"\n维度:"+mLatitude+cityMsg);
            }


        }
    }

    public void setOnMessageResponseListener(ILocationHelper.OnMessageResponseListener listener) {
        mListener=listener;
    }
}
