package han.anthony.anthonydemo.activity_colorTackTabLayout.presenter;

import han.anthony.anthonydemo.activity_colorTackTabLayout.view.IShowFragmentView;
import han.anthony.anthonydemo.activity_colorTackTabLayout.mode.IFragmentLoader;

/**
 * Created by senior on 2016/11/16.
 */
public class ColorTrackPresenter {
    private final IFragmentLoader mLoader;
    private final IShowFragmentView mActivity;


    public ColorTrackPresenter(IShowFragmentView view, IFragmentLoader loader) {
        mActivity=view;
        mLoader=loader;
    }

    public void showFragment() {
        mActivity.showFragment(mLoader.loadFragment());
    }
}
