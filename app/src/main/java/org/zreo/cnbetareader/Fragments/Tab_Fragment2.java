package org.zreo.cnbetareader.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;;
import android.widget.ListView;
import android.widget.TextView;

import org.zreo.cnbetareader.Adapters.Tab_FragmentAdapter;
import org.zreo.cnbetareader.Model.CnInformation_Theme;
import org.zreo.cnbetareader.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class Tab_Fragment2 extends Fragment {
    private View view;
    private ListView theme_mayattention_listview;
    private CnInformation_Theme  cnInformation_theme;
    List<CnInformation_Theme> list = new ArrayList<CnInformation_Theme>();
    Tab_FragmentAdapter tab_fragmentAdapter;
    TextView newType;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_theme_mayattention, container, false); //获取布局
        jiaData();
        theme_mayattention_listview= (ListView) view.findViewById(R.id.theme_mayattention_listview);
        newType= (TextView) view.findViewById(R.id.tv_type2);
        tab_fragmentAdapter=new Tab_FragmentAdapter(getActivity(),R.layout.itv_tab2,list);
        theme_mayattention_listview.setAdapter(tab_fragmentAdapter);
        String newtype=cnInformation_theme.getThemetype();
        newType.setText(newtype);
        return view;
    }

public void jiaData(){
    String type="A";
    String title="ADsee";
    String firstWord="A";
    for(int i=0;i<15;i++){
        cnInformation_theme=new CnInformation_Theme();
        cnInformation_theme.setThemetype(type);
        cnInformation_theme.setContent(title);
        cnInformation_theme.setFirstWord(firstWord);
        list.add(cnInformation_theme);
    }
}
}
