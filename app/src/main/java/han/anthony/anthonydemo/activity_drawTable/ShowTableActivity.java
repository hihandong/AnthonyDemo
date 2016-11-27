package han.anthony.anthonydemo.activity_drawTable;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;

import han.anthony.anthonydemo.R;
import han.anthony.anthonydemo.activity_main.myLogger.L;

/**
 * Created by senior on 2016/11/24.
 */

public class ShowTableActivity extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_view);
        setTitle("自定义走势图");
    }


}
