package han.anthony.anthonydemo.activity_colorTackTabLayout;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

import han.anthony.anthonydemo.activity_colorTackTabLayout.mode.FragmentLoader;

/**
 * Created by senior on 2016/11/16.
 */
public class MyFragmentPagerAdapter extends FragmentPagerAdapter {

    private final List<ViewFragment> mDatas;

    public MyFragmentPagerAdapter(FragmentManager fm, List<ViewFragment> datas) {
        super(fm);
        mDatas=datas;
    }

    @Override
    public Fragment getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return FragmentLoader.TITLES[position];
    }
}
