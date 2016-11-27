package han.anthony.anthonydemo.activity_colorTackTabLayout;

import dagger.Component;
import han.anthony.anthonydemo.activity_colorTackTabLayout.view.ColorTrackActivity;
import han.anthony.anthonydemo.daggerAnnotation.PerActivity;

/**
 * Created by senior on 2016/11/16.
 */

@Component(modules = SimpleModule.class) @PerActivity
public interface SimpleComponent {
    void inject(ColorTrackActivity activity);
}
