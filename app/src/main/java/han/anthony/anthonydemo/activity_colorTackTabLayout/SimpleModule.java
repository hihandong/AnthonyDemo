package han.anthony.anthonydemo.activity_colorTackTabLayout;

import dagger.Module;
import dagger.Provides;
import han.anthony.anthonydemo.activity_colorTackTabLayout.mode.FragmentLoader;
import han.anthony.anthonydemo.activity_colorTackTabLayout.presenter.ColorTrackPresenter;
import han.anthony.anthonydemo.activity_colorTackTabLayout.view.ColorTrackActivity;
import han.anthony.anthonydemo.activity_colorTackTabLayout.view.IShowFragmentView;
import han.anthony.anthonydemo.activity_colorTackTabLayout.mode.IFragmentLoader;
import han.anthony.anthonydemo.daggerAnnotation.PerActivity;

/**
 * Created by senior on 2016/11/16.
 */

@Module
public class SimpleModule {
    ColorTrackActivity mActivity;
    public SimpleModule(ColorTrackActivity activity){
        mActivity=activity;
    }

    @Provides
    IShowFragmentView provideColorTrackActivity(){
        return mActivity;
    }

    @Provides
    IFragmentLoader provideFragmentLoader(){
        return new FragmentLoader();
    }

    @Provides @PerActivity
    ColorTrackPresenter provideColorTrackPresenter(IShowFragmentView view,IFragmentLoader loader){
        return new ColorTrackPresenter(view,loader);
    }


}
