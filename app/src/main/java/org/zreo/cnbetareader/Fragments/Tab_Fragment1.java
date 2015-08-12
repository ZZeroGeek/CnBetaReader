package org.zreo.cnbetareader.Fragments;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.zreo.cnbetareader.Adapters.Fragment1_Adapter;
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
    Fragment1_Adapter fragment1_adapter;
    private CnInformation_Theme  cnInformation_theme;
    private View loadMoreView;     //加载更多布局
    private TextView loadMoreText;    //加载提示文本
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_theme_listview, container, false); //获取布局
        theme_listview=(ListView)view.findViewById(R.id.theme_listview);
        fragment1_adapter=new Fragment1_Adapter(getActivity(),R.layout.itv_tab1,CnInformation_ThemeList);
        theme_listview.setAdapter(fragment1_adapter);
        theme_listview.addFooterView(loadMoreView);
        initCollectNewsList();//初始化
        return view;
    }
    private void  initCollectNewsList(){
        String[] type=new String[]{"A","B","C","D","F","G","V",};
        String[] title=new String[]{"ADsee","AMD","AloAng","Adobe","ADC","ABC","AE"};
        String firstWord="A";
        for(int i=0;i<7;i++){
            cnInformation_theme=new CnInformation_Theme();
            cnInformation_theme.setThemetype(type[0]);
            cnInformation_theme.setContent(title[i]);
            cnInformation_theme.setFirstWord(firstWord);
            CnInformation_ThemeList.add(cnInformation_theme);
        }
        loadMoreView = getActivity().getLayoutInflater().inflate(R.layout.load_more, null);
        loadMoreText = (TextView) loadMoreView.findViewById(R.id.load_more);
        loadMoreText.setBackgroundColor(getResources().getColor(R.color.gray));
        loadMoreText.setText("-- The End --");
        }
}
