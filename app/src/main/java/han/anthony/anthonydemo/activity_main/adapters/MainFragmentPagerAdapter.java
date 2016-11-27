package han.anthony.anthonydemo.activity_main.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by senior on 2016/11/6.
 */

public class MainFragmentPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> mDatas;
    private String[] mTitles;
    public MainFragmentPagerAdapter(FragmentManager fm, List<Fragment> datas, String[] titles) {
        super(fm);
        mDatas=datas;
        mTitles=titles;
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
        return mTitles[position];
    }
}
