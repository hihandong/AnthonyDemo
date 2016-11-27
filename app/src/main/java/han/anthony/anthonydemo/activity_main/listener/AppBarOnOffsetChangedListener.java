package han.anthony.anthonydemo.activity_main.listener;

import android.annotation.TargetApi;
import android.os.Build;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.Toolbar;

/**
 * Created by senior on 2016/11/16.
 */

public class AppBarOnOffsetChangedListener implements AppBarLayout.OnOffsetChangedListener {
  //  private AppBarLayout mAppBarLayout;
    private CollapsingToolbarLayout mCollaps;
    private Toolbar mToolbar;
    public AppBarOnOffsetChangedListener
            (CollapsingToolbarLayout collaps,Toolbar toolbar){
       // mAppBarLayout=appBarLayout;
        mCollaps=collaps;
        mToolbar=toolbar;
    }

    /**
     * 随着滑动,Toolbar透明度,标题内容,跟随改变
     * @param appBarLayout
     * @param verticalOffset
     */
    @TargetApi(11)// mToolbar.setAlpha(alpha);
    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        int scrollRange = appBarLayout.getTotalScrollRange() ;

        int mOffset = Math.abs(verticalOffset);
        float alpha=(float) mOffset/scrollRange;
        if (Build.VERSION.SDK_INT < 11) {
            return;
        }
        if(alpha>0.9){
            mCollaps.setTitle("Thank you for coming!");
        }else {
            mCollaps.setTitle("Anthony's Demo");
        }
        mToolbar.setAlpha(alpha);
    }
}