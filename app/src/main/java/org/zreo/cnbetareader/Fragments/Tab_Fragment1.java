package org.zreo.cnbetareader.Fragments;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.zreo.cnbetareader.Adapters.Information_theme_Adapter;
import org.zreo.cnbetareader.Model.CnInformation_Theme;
import org.zreo.cnbetareader.Model.CollectNews;
import org.zreo.cnbetareader.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class Tab_Fragment1 extends Fragment {
    private View view;
    private ListView theme_listview;
    private List<CnInformation_Theme>CnInformation_ThemeList=new ArrayList <CnInformation_Theme>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_theme_listview, container, false); //获取布局
        theme_listview=(ListView)view.findViewById(R.id.theme_listview);
        initCollectNewsList();//初始化
        return view;
    }
    private void  initCollectNewsList(){

        }
}
