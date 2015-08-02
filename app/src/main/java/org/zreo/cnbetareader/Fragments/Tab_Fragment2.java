package org.zreo.cnbetareader.Fragments;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import org.zreo.cnbetareader.Adapters.Theme_mayattention_Adapter;
import org.zreo.cnbetareader.Model.CnInformation_theme_mayattention;
import org.zreo.cnbetareader.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class Tab_Fragment2 extends Fragment {
    private View view;
    private ListView theme_mayattention_listview;
    //private Theme_mayattention_Adapter tmAdapter;
    private List<CnInformation_theme_mayattention>CnInformation_theme_mayattentionList=new ArrayList
            <CnInformation_theme_mayattention>();



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.information_theme, container, false); //获取布局
        theme_mayattention_listview=(ListView)view.findViewById(R.id.theme_mayattention_listview);
        initTab_Fragment2List();//初始化
        Theme_mayattention_Adapter tmAdapter=new Theme_mayattention_Adapter(
                getActivity(), R.layout.itv_tab1,CnInformation_theme_mayattentionList);
        theme_mayattention_listview.setAdapter(tmAdapter);
        return view;
    }

public void initTab_Fragment2List() {
    String type = " Y";
    String content = "You are my pretty sunshine！";
    String firstword = "Y";
    for (int i = 0; i < 20; i++) {
        CnInformation_theme_mayattention cnInformation_theme_mayattention = new CnInformation_theme_mayattention();
        cnInformation_theme_mayattention.setContent(content);
        cnInformation_theme_mayattention.setFirstword(firstword);
        cnInformation_theme_mayattention.setThemetype(type);
        CnInformation_theme_mayattentionList.add(cnInformation_theme_mayattention);

    }
}
}
