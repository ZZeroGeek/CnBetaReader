package org.zreo.cnbetareader.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import org.zreo.cnbetareader.Adapters.Comment_hot_Adapter;
import org.zreo.cnbetareader.Model.CnComment_hot;
import org.zreo.cnbetareader.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/7/29.
 */
public class Comment_hot_Fragment extends Fragment{
    private ListView hot_listView;
    private Comment_hot_Adapter mAdapter;
    private List<CnComment_hot> cnComment_hotList = new ArrayList<CnComment_hot>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_c_hot_listview,container,false);
        initComment_hotList();
        hot_listView = (ListView)rootview.findViewById(R.id.hot_listView);
        Comment_hot_Adapter myAdapter = new Comment_hot_Adapter(getActivity(),R.layout.fragment_c_hot_textview,cnComment_hotList);
        hot_listView.setAdapter(myAdapter);
        return  rootview;
    }

    private void initComment_hotList(){
        String content = "有些人会说没有什么是这城里人不会的。就是比你会，信不信由你！";
        String comment = "城里人真会玩";
        String firstWord ="有";
        for(int i = 1; i < 20; i++){

            CnComment_hot cnComment_hots = new CnComment_hot();
            cnComment_hots.setComment("匿名网友 评论于 "+comment);
            cnComment_hots.setContent(content);
            cnComment_hots.setFirstWord(firstWord);
            cnComment_hotList.add(cnComment_hots);
        }
    }
}
