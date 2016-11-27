package han.anthony.anthonydemo.activity_colorTackTabLayout;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import han.anthony.anthonydemo.R;

/**
 * Created by senior on 2016/11/6.
 */

public class ViewFragment extends Fragment {
    public static final String TITLE="TITLE";
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(getArguments()!=null){
            String title=getArguments().getString(TITLE);
            ((TextView)view.findViewById(R.id.text_view)).setText("左右滑动查看\n"+title);
        }


    }


}
