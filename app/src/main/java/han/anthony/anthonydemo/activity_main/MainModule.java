package han.anthony.anthonydemo.activity_main;

import dagger.Module;
import dagger.Provides;
import han.anthony.anthonydemo.daggerAnnotation.PerActivity;
import han.anthony.anthonydemo.activity_main.model.DemoDataLoader;
import han.anthony.anthonydemo.activity_main.model.IModel.IDemoDataLoader;
import han.anthony.anthonydemo.activity_main.presenter.MainPresenter;
import han.anthony.anthonydemo.activity_main.view.MainActivity;
import han.anthony.anthonydemo.activity_main.view.iView.IShowDemosView;

/**
 * Created by senior on 2016/11/16.
 */

@Module
public class MainModule {

    private final MainActivity mMainActivity;

    public MainModule(MainActivity activity){
        mMainActivity=activity;
    }

    @Provides
    IShowDemosView provideMainActivity(){
        return mMainActivity;
    }
    @Provides
    IDemoDataLoader provideDemoDataLoader(){
        return new DemoDataLoader();
    }

    @Provides @PerActivity
    MainPresenter provideMainPresenter(IShowDemosView view,IDemoDataLoader loader){
        return new MainPresenter(view,loader);
    }
}
