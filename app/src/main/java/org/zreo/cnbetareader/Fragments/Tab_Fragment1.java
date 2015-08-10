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
    private View iview;
    private ListView theme_listview;
    private List<CnInformation_Theme>CnInformation_ThemeList=new ArrayList <CnInformation_Theme>();
    private TextView type;
    private TextView firstword1;
    private TextView title1;
    private ImageView image1;
    private CollectNews  collectnews;
    private List<CollectNews> CollectNewsList=new ArrayList<CollectNews>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        iview = inflater.inflate(R.layout.itv_tab1, container, false); //获取布局
        theme_listview=(ListView)iview.findViewById(R.id.theme_listview);
        type=(TextView)iview.findViewById(R.id.itv_type1);
        firstword1= (TextView) iview.findViewById(R.id.firstword_text1);
        title1= (TextView) iview.findViewById(R.id.itv_context1);
        image1=(ImageView)iview.findViewById(R.id.image1);
        initCollectNewsList();//初始化
        return iview;
    }

    private void  initCollectNewsList(){
        collectnews = new CollectNews();
        String firstword="德";
        String cnewscontent="德玛西亚！德玛西亚万岁··";
        for(int i=0;i<15;i++){
            collectnews.setNewscontent(cnewscontent);
            collectnews.setNewsfirstWord(firstword);
            CollectNewsList.add(collectnews);
        }
    }
}
