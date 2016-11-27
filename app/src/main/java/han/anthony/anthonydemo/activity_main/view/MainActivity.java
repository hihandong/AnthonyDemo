package han.anthony.anthonydemo.activity_main.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import han.anthony.anthonydemo.R;

import han.anthony.anthonydemo.beans.DemoBean;
import han.anthony.anthonydemo.activity_main.DaggerMainComponent;
import han.anthony.anthonydemo.activity_main.MainComponent;
import han.anthony.anthonydemo.activity_main.MainModule;
import han.anthony.anthonydemo.activity_main.adapters.RecyclerAdapter;
import han.anthony.anthonydemo.activity_main.listener.AppBarOnOffsetChangedListener;
import han.anthony.anthonydemo.activity_main.myLogger.L;
import han.anthony.anthonydemo.activity_main.presenter.MainPresenter;
import han.anthony.anthonydemo.activity_main.view.iView.IShowDemosView;

/**
 * 主界面
 */

public class MainActivity extends AppCompatActivity implements IShowDemosView,RecyclerAdapter.OnItemClickListener,RecyclerAdapter.OnItemLongClickListener {
    @Inject MainPresenter mMainPresenter;
    @BindView(R.id.appbar_layout) AppBarLayout mAppBarLayout;
    @BindView(R.id.toolbar) Toolbar mToolbar;
    @BindView(R.id.collapsing_toolbar_layout) CollapsingToolbarLayout mCollaps;
    @BindView(R.id.recycler_view)  RecyclerView mRecyclerView;
    MainComponent mMainComponent;
    private List<DemoBean> mDatas;
    private RecyclerAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Dagger 桥设置
        mMainComponent= DaggerMainComponent.builder().mainModule(new MainModule(this)).build();
        mMainComponent.inject(this);

        ButterKnife.bind(this);
        initView();
        mMainPresenter.showDemo();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    private void initView() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mAppBarLayout.addOnOffsetChangedListener(new AppBarOnOffsetChangedListener( mCollaps, mToolbar));
        mToolbar.setNavigationIcon(R.mipmap.anthony);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //由presenter 调用
    @Override
    public void showDemos(List<DemoBean> demoData) {
        mDatas=demoData;
        mAdapter = new RecyclerAdapter(this, demoData);
        mAdapter.setOnItemClickListener(this);
        mAdapter.setOnItemLongClickListener(this);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
//        MyItemAnimator a;

//        mRecyclerView.setItemAnimator(a=new MyItemAnimator());
//        a.setMoveDuration(2000);
//        a.setRemoveDuration(2000);
//        a.setAddDuration(2000);
//        a.setChangeDuration(2000);
    }

    /**
     * 设置item点击事件
     * @param view
     * @param position
     */
    @Override
    public void onItemClick(View view, int position) {
        String className=mDatas.get(position).getClassName();
        if(className!=null){
            try {
                Class clazz = Class.forName(className);
                Intent i=new Intent(this,clazz);
                startActivity(i);
            } catch (ClassNotFoundException e) {
                L.e(e.getMessage());
            }

        }
    }

    @Override
    public void onItemLongClick(View view, int position) {
        mDatas.remove(position);
        mAdapter.notifyItemRemoved(position);
        Snackbar.make(view,"You Just Long Clicked!",Snackbar.LENGTH_SHORT).setAction("I know it.", null).show();
    }
}
