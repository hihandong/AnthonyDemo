package han.anthony.anthonydemo.activity_main;

import dagger.Component;
import han.anthony.anthonydemo.daggerAnnotation.PerActivity;
import han.anthony.anthonydemo.activity_main.view.MainActivity;

/**
 * Created by senior on 2016/11/16.
 */

@PerActivity //@Subcomponent
@Component (modules = MainModule.class)
public interface MainComponent {
    void inject(MainActivity activity);
}
