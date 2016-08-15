package doapps.marcogreen.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import doapps.marcogreen.R;

/**
 * Created by Bryam Soto on 10/08/2016.
 */
public class FragmentAbout extends Fragment {


    private View root;
    private TextView tvAbout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (root == null){
            root = inflater.inflate(R.layout.fragment_about,container,false);
        }
        ViewGroup parent = (ViewGroup) root.getParent();
        if (parent != null){
            parent.removeView(root);
        }

        tvAbout = (TextView) root.findViewById(R.id.tv_about);
        tvAbout.setText(getResources().getString(R.string.about));
        return root;
    }
}
