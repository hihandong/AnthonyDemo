package han.anthony.anthonydemo.activity_permission.presenter;

import android.content.Context;

import han.anthony.anthonydemo.activity_main.myLogger.L;
import han.anthony.anthonydemo.activity_permission.mode.ILocationHelper;
import han.anthony.anthonydemo.activity_permission.mode.LocationHelper;
import han.anthony.anthonydemo.activity_permission.view.ILocationView;

/**
 * Created by senior on 2016/11/26.
 */

public class LocationPresenter implements ILocationHelper.OnMessageResponseListener {
    private ILocationView mView;
    private ILocationHelper mHelper;

    public LocationPresenter(ILocationView view){
        mView=view;
        mHelper=new LocationHelper();
    }

    public void startLocation(Context context) {
       mHelper.startLocationMessage(context);
        mHelper.setOnMessageResponseListener(this);
    }


    @Override
    public void onMessageResponse(String message) {
        mView.showLocation(message);
    }

    public void onDestroy() {

    }
}
