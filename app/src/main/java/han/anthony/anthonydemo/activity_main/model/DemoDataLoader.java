package han.anthony.anthonydemo.activity_main.model;

import java.util.ArrayList;
import java.util.List;

import han.anthony.anthonydemo.activity_permission.view.RequestPermissionActivity;
import han.anthony.anthonydemo.activity_quick24.Quick24Activity;
import han.anthony.anthonydemo.beans.DemoBean;
import han.anthony.anthonydemo.activity_colorTackTabLayout.view.ColorTrackActivity;
import han.anthony.anthonydemo.activity_drawTable.ShowTableActivity;
import han.anthony.anthonydemo.activity_main.model.IModel.IDemoDataLoader;
import han.anthony.anthonydemo.activity_main.view.MainActivity;

/**
 * Created by senior on 2016/11/16.
 */

public class DemoDataLoader implements IDemoDataLoader {
    @Override
    public List<DemoBean> loadDemoData() {
        List<DemoBean> demos=new ArrayList<>();
        DemoBean d=new DemoBean("模仿今日头条的指示器","通过自定义TabLayout", ColorTrackActivity.class.getName());
        demos.add(d);
        d=new DemoBean("绘制数据走势图","通过自定义View实现", ShowTableActivity.class.getName());
        demos.add(d);
        d=new DemoBean("动态获得权限","通过调用百度API获得街道信息", RequestPermissionActivity.class.getName());
        demos.add(d);
        d = new DemoBean("快算24", "实现高中时的想法",Quick24Activity.class.getName());
        demos.add(d);

        return demos;
    }
}
