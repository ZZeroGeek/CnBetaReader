package org.zreo.cnbetareader.Fragments;
import org.zreo.cnbetareader.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
/**
 * Created by guang on 2015/7/23.
 */
public class DrawerLayoutFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        return inflater.inflate(R.layout.fragment_drawer_layout, container, false);
    }

}

