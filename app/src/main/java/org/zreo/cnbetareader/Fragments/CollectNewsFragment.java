package org.zreo.cnbetareader.Fragments;



import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import org.zreo.cnbetareader.Adapters.CollectNews_Adapter;
import org.zreo.cnbetareader.Model.CnComment_hot;
import org.zreo.cnbetareader.Model.CollectNews;
import org.zreo.cnbetareader.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class CollectNewsFragment extends Fragment {

    View cview;
    private ListView collectnews_list;
    private List<CollectNews> CollectNewsList=new ArrayList<CollectNews>();
    CollectNews_Adapter cnsAdapter;


   // private int visibleLastIndex = 0;   //最后的可视项索引
    //private int visibleItemCount;       // 当前窗口可见项总数
   // private View loadMoreView;     //加载更多布局

    SwipeRefreshLayout swipeLayout;  //下拉刷新控件

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        cview=inflater.inflate(R.layout.fragment_collectnews_listview,container,false);
        initCollectNewsList();//初始化收藏新闻列表
        initView();//初始化布局
        return cview;
    }
    /*
     *初始化布局
     */
   private void  initCollectNewsList(){
       String  newscontent= "asjdofenfaihg";
       String newstypes = "haonangaedfe";
       String newsfirstWord ="h";
       for(int i = 1; i < 20; i++){

          CollectNews collectnews = new CollectNews();
           collectnews.setNewscontent( newscontent);
           collectnews.setNewsfirstWord(newsfirstWord);
           collectnews.setNewstypes(newstypes);
           CollectNewsList.add(collectnews);
       }
   }
    private void initView(){
        /*显示collectnews的ListView*/
        collectnews_list=(ListView)cview.findViewById(R.id.collectnews_list);
        /*为ListView创建自定义适配器*/
        cnsAdapter=new CollectNews_Adapter(getActivity(),R.layout.collect_news,CollectNewsList);
        collectnews_list.setVerticalScrollBarEnabled(false);//隐藏ListView滑动进度条
    //    collectnews_list = getActivity().getLayoutInflater().inflate(R.layout.load_more, null);
      //  collectnews_list.addFooterView(cnsAdapter);   //设置列表底部视图
    //   collectnews_list.setOnScrollListener(this);     //添加滑动监听
        collectnews_list.setAdapter(cnsAdapter);  //为ListView绑定Adapter

        swipeLayout = (SwipeRefreshLayout) cview.findViewById(R.id.swipe_hot);
       // swipeLayout.setOnRefreshListener(this);
        //设置刷新时动画的颜色，可以设置4个
        swipeLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
    }
}



