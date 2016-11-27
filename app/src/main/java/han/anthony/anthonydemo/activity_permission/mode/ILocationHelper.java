package han.anthony.anthonydemo.activity_permission.mode;

import android.content.Context;

/**
 * Created by senior on 2016/11/26.
 */
public interface ILocationHelper {
    /**
     * 开始获得定位信息
     * @param context
     */
    void startLocationMessage(Context context);

    /**
     * 设置网络返回信息的监听器
     * @param listener
     */
    void setOnMessageResponseListener(OnMessageResponseListener listener);

    interface OnMessageResponseListener {
       void onMessageResponse(String message);
    }
}
