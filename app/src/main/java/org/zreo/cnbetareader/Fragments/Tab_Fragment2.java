package org.zreo.cnbetareader.Fragments;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.zreo.cnbetareader.Model.CnInformation_Theme;
import org.zreo.cnbetareader.Model.CollectNews;
import org.zreo.cnbetareader.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class Tab_Fragment2 extends Fragment {
    private View view;
    private ListView theme_mayattention_listview;
    private TextView type2;
    private TextView firstword2;
    private TextView title2;
    private ImageView image2;
    private Button btn;
    private CollectNews  collectnews;
    private List<CollectNews> CollectNewsList=new ArrayList<CollectNews>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.itv_tab2, container, false); //获取布局
        theme_mayattention_listview=(ListView)view.findViewById(R.id.theme_mayattention_listview);
        title2= (TextView) view.findViewById(R.id.tv_type2);
        type2=(TextView)view.findViewById(R.id.tv_type2);
        firstword2=(TextView)view.findViewById(R.id.firstword_text2);
        image2=(ImageView)view.findViewById(R.id.image2);
        btn=(Button)view.findViewById(R.id.ibtn_theme);
        initCollectNewsList();
        return view;
    }
    //
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
