package doapps.marcogreen.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.DecimalFormat;

import doapps.marcogreen.R;
import doapps.marcogreen.session.SessionManager;


/**
 * Created by Bryam Soto on 10/08/2016.
 */
public class FragmentHow extends Fragment {

    private View root;
    private TextView tvHow;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (root == null) {
            root = inflater.inflate(R.layout.fragment_how, container, false);
        }
        ViewGroup parent = (ViewGroup) root.getParent();
        if (parent != null) {
            parent.removeView(root);
        }
        tvHow = (TextView) root.findViewById(R.id.tv_how);
        tvHow.setText(getResources().getString(R.string.how));

        return root;
    }

}
