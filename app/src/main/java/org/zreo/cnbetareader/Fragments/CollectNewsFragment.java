package org.zreo.cnbetareader.Fragments;



import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import org.zreo.cnbetareader.Adapters.CollectNews_Adapter;
import org.zreo.cnbetareader.Database.CollectionDatabase;
import org.zreo.cnbetareader.Database.NewsTitleDatabase;
import org.zreo.cnbetareader.Entitys.NewsEntity;
import org.zreo.cnbetareader.Model.CollectNews;
import org.zreo.cnbetareader.Net.BaseHttpClient;
import org.zreo.cnbetareader.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class CollectNewsFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    View view;
    private ListView collectnew_listview;
    private List<NewsEntity> CollectNewsList; //= new ArrayList<NewsEntity>();
    CollectNews_Adapter cnsAdapter;

    private View loadMoreView;     //加载更多布局
    private TextView loadMoreText;    //加载提示文本
    SwipeRefreshLayout swipeLayout;  //下拉刷新控件

    private CollectionDatabase collectionDatabase;
    private NewsTitleDatabase newsTitleDatabase;  //数据库

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_collectnews_listview, container, false);

        collectionDatabase = CollectionDatabase.getInstance(getActivity());  //初始化数据库实例
        newsTitleDatabase = NewsTitleDatabase.getInstance(getActivity());  //初始化数据库实例

        initCollectNewsList();  //初始化收藏新闻列表
        return view;
    }

    /**
     *初始化列表
     */
   private void  initCollectNewsList(){
       //CollectNewsList = collectionDatabase.loadCollection();
       CollectNewsList = newsTitleDatabase.loadNewsEntity();   //从数据库读取新闻列表
       if (CollectNewsList.size() > 0) {     //数据库有数据，直接显示数据库中的数据
           initView();   //初始化布局
       } else {    //如果数据库没数据

       }
       //initView();


   }
    /**
     *初始化布局
     */
    private void initView(){
        /*显示collectnews的ListView*/
        collectnew_listview = (ListView) view.findViewById(R.id.collectnews_listview);
        cnsAdapter = new CollectNews_Adapter(getActivity(), R.layout.collect_news, CollectNewsList);
        //collectnew_listview.setVerticalScrollBarEnabled(false);//隐藏ListView滑动进度条
        loadMoreView = getActivity().getLayoutInflater().inflate(R.layout.load_more, null);
        loadMoreText = (TextView) loadMoreView.findViewById(R.id.load_more);
        loadMoreText.setBackgroundColor(getResources().getColor(R.color.gray));
        loadMoreText.setTextColor(getResources().getColor(R.color.gray));
        loadMoreText.setText("-- The End --");
        collectnew_listview.addFooterView(loadMoreView);   //设置列表底部视图
        collectnew_listview.setAdapter(cnsAdapter);   //为ListView绑定Adapter

       swipeLayout = (SwipeRefreshLayout) view.findViewById(R.id. collectnews_list);
       swipeLayout.setOnRefreshListener(this);
        //设置刷新时动画的颜色，可以设置4个
        swipeLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
    }


    @Override
       public void onRefresh() {
        swipeLayout.setRefreshing(false);   //加载完数据后，隐藏刷新进度条
    }


}



