package han.anthony.anthonydemo.activity_colorTackTabLayout.mode;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import han.anthony.anthonydemo.activity_colorTackTabLayout.ViewFragment;
import han.anthony.anthonydemo.activity_colorTackTabLayout.mode.IFragmentLoader;

/**
 * Created by senior on 2016/11/16.
 */

public class FragmentLoader implements IFragmentLoader {
    public static String[] TITLES ={"昨天","今天","明天"};
    @Override
    public List<ViewFragment> loadFragment() {
        List<ViewFragment> fragments=new ArrayList<>();
        for (String title: TITLES){
            ViewFragment f=new ViewFragment();
            Bundle bundle=new Bundle();
            bundle.putString(ViewFragment.TITLE,title);
            f.setArguments(bundle);
            fragments.add(f);
        }
        return fragments;
    }
}
