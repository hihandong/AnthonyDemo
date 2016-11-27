package han.anthony.anthonydemo.activity_colorTackTabLayout.view;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import han.anthony.anthonydemo.R;
import han.anthony.anthonydemo.activity_colorTackTabLayout.presenter.ColorTrackPresenter;
import han.anthony.anthonydemo.activity_colorTackTabLayout.DaggerSimpleComponent;
import han.anthony.anthonydemo.activity_colorTackTabLayout.MyFragmentPagerAdapter;
import han.anthony.anthonydemo.activity_colorTackTabLayout.SimpleComponent;
import han.anthony.anthonydemo.activity_colorTackTabLayout.SimpleModule;
import han.anthony.anthonydemo.activity_colorTackTabLayout.ViewFragment;
import han.anthony.anthonydemo.customedView.ColorTrackTabLayout;

/**
 * Created by senior on 2016/11/16.
 */

public class ColorTrackActivity extends AppCompatActivity implements IShowFragmentView {
    @BindView(R.id.color_track_tab_layout)   ColorTrackTabLayout mColorTrackTabLayout;
    @BindView(R.id.view_pager)    ViewPager mViewPager;
    @Inject
    ColorTrackPresenter mColorTrackPresenter;
    SimpleComponent mComponent;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.color_track_activity);
        ButterKnife.bind(this);
        mComponent= DaggerSimpleComponent.builder().simpleModule(new SimpleModule(this)).build();
        mComponent.inject(this);
        setTitle("会变色的指示器");
        init();
    }

    private void init() {
        mColorTrackPresenter.showFragment();
    }

    @Override
    public void showFragment(List<ViewFragment> datas) {
        mViewPager.setAdapter(new MyFragmentPagerAdapter(getSupportFragmentManager(),datas));
        mColorTrackTabLayout.setupWithViewPager(mViewPager);
    }
}
