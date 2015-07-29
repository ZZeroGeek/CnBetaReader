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
    public View onCreatView(LayoutInflater inflater,ViewGroup container,Bundle saveInstanceState){
        View rootview = inflater.inflate(R.layout.fragment_c_top10listview,container,false);
        initComment_hotList();
        hot_listView = (ListView)rootview.findViewById(R.id.hot_listView);
        //top10_listView = (ListView) findViewById(R.id.top10_listView);
        Comment_hot_Adapter myAdapter = new Comment_hot_Adapter(getActivity(),R.layout.fragment_c_hot_textview,cnComment_hotList);
        hot_listView.setAdapter(myAdapter);
        return  rootview;
    }
    private void initComment_hotList(){
        String content = "I love you not for who you are, but for who I am before you";
        String comment = "Windows I love you not for who you are, but for who I am before you";
        String firstword ="I";
        for(int i = 1; i < 20; i++){

            CnComment_hot cnComment_hots = new CnComment_hot();
            cnComment_hots.setComment( comment);
            cnComment_hots.setContent(content);
            cnComment_hots.setFirstWord(firstword);
            cnComment_hotList.add(cnComment_hots);
        }
    }
}
