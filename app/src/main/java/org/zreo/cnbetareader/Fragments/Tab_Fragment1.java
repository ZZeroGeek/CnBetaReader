package org.zreo.cnbetareader.Fragments;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import org.zreo.cnbetareader.Adapters.Information_theme_Adapter;
import org.zreo.cnbetareader.Model.CnInformation_Theme;
import org.zreo.cnbetareader.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class Tab_Fragment1 extends Fragment {
    private View iview;
    private ListView theme_listview;
    //private Information_theme_Adapter itAdapter;
    private List<CnInformation_Theme>CnInformation_ThemeList=new ArrayList <CnInformation_Theme>();
    public Tab_Fragment1() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        iview = inflater.inflate(R.layout.information_theme, container, false); //获取布局
        theme_listview=(ListView)iview.findViewById(R.id.theme_listview);
        iniTab_Fragment1List();//初始化
        Information_theme_Adapter itAdapter=new Information_theme_Adapter(
                getActivity(), R.layout.itv_tab1,CnInformation_ThemeList);
        theme_listview.setAdapter(itAdapter);
        return iview;
    }

private void iniTab_Fragment1List(){
    String type="T";
    String content = "That's just life,不能后退的时候";
    String firstword ="T";
    for(int i=0;i<20;i++){
       CnInformation_Theme cnInformation_theme=new CnInformation_Theme();
        cnInformation_theme.setContent(content);
        cnInformation_theme.setFirstWord(firstword);
        cnInformation_theme.setThemetype(type);
        CnInformation_ThemeList.add(cnInformation_theme);
    }
}
}
