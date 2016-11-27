package han.anthony.anthonydemo.util;

import okhttp3.OkHttpClient;

/**
 * Created by senior on 2016/11/26.
 */

public class OkHttpHelper {
    private static volatile OkHttpHelper mInstance;
    private OkHttpClient mOkHttpClient;
    private OkHttpHelper(){
        mOkHttpClient=new OkHttpClient();
    }

    public static OkHttpHelper getInstance(){
        if(mInstance==null){
            synchronized (OkHttpHelper.class){
                if (mInstance==null){
                    mInstance=new OkHttpHelper();
                }
            }
        }
        return mInstance;
    }

    public OkHttpClient getOkHttpClient(){
        return mOkHttpClient;
    }
}
