package han.anthony.anthonydemo.activity_main.presenter;

import han.anthony.anthonydemo.activity_main.model.IModel.IDemoDataLoader;
import han.anthony.anthonydemo.activity_main.view.iView.IShowDemosView;

/**
 * Created by senior on 2016/11/2.
 */

public class MainPresenter {
    private IDemoDataLoader mLoader;
    private IShowDemosView mShowView;

    public MainPresenter(IShowDemosView view, IDemoDataLoader loader) {
        mShowView=view;
        mLoader=loader;
    }


    //由View调用
    public void showDemo() {
        //通过Mode获得数据,传给View
        mShowView.showDemos(mLoader.loadDemoData());
    }
}
