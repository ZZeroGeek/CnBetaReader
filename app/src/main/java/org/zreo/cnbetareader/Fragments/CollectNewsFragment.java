package org.zreo.cnbetareader.Fragments;



import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.ResponseHandlerInterface;

import org.zreo.cnbetareader.Activitys.MainActivity;
import org.zreo.cnbetareader.Adapters.CollectNews_Adapter;
import org.zreo.cnbetareader.Entitys.NewsEntity;
import org.zreo.cnbetareader.Entitys.NewsListEntity;
import org.zreo.cnbetareader.Entitys.ResponseEntity;
import org.zreo.cnbetareader.Model.CnComment_hot;
import org.zreo.cnbetareader.Model.CollectNews;
import org.zreo.cnbetareader.Model.Net.NewsListHttpModel;
import org.zreo.cnbetareader.Net.BaseHttpClient;
import org.zreo.cnbetareader.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class CollectNewsFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener ,AbsListView.OnScrollListener{

    View cview;
    private ListView collectnew_listview;
    private List<CollectNews> CollectNewsList=new ArrayList<CollectNews>();
    CollectNews_Adapter cnsAdapter;

    private View loadMoreView;     //加载更多布局
    private TextView loadMoreText;    //加载提示文本
    CollectNews collectnews;
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
           collectnews = new CollectNews();
           String firstword="德";
           String cnewscontent="德玛西亚！德玛西亚万岁··";
       for(int i=0;i<15;i++){
           collectnews.setNewscontent(cnewscontent);
           collectnews.setNewsfirstWord(firstword);
           CollectNewsList.add(collectnews);
       }

   }
    private void initView(){
        /*显示collectnews的ListView*/
        collectnew_listview=(ListView)cview.findViewById(R.id.collectnews_listview);
        /*为ListView创建自定义适配器*/
        cnsAdapter=new CollectNews_Adapter(getActivity(),R.layout.collect_news,CollectNewsList);
        collectnew_listview.setVerticalScrollBarEnabled(false);//隐藏ListView滑动进度条
        loadMoreView = getActivity().getLayoutInflater().inflate(R.layout.load_more, null);
        loadMoreText = (TextView) loadMoreView.findViewById(R.id.load_more);
        loadMoreText.setText("--The End--");
        collectnew_listview.addFooterView(loadMoreView);   //设置列表底部视图
        collectnew_listview.setOnScrollListener(this);     //添加滑动监听
        collectnew_listview.setAdapter(cnsAdapter);  //为ListView绑定Adapter

       swipeLayout = (SwipeRefreshLayout) cview.findViewById(R.id. collectnews_list);
       swipeLayout.setOnRefreshListener(this);
        //设置刷新时动画的颜色，可以设置4个
        swipeLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
    }

    private int visibleLastIndex = 0;   //最后的可视项索引
    private int visibleItemCount;       // 当前窗口可见项总数
    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        int itemsLastIndex =CollectNewsList.size() - 1;    //数据集最后一项的索引
        int lastIndex = itemsLastIndex + 1;             //加上底部的loadMoreView项
        if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE && visibleLastIndex == lastIndex) {

        }
    }
    @Override
       public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        this.visibleItemCount = visibleItemCount;
        visibleLastIndex = firstVisibleItem + visibleItemCount - 1;
    }
    @Override
       public void onRefresh() {
       // addData();
        cnsAdapter.notifyDataSetChanged();
        swipeLayout.setRefreshing(false);   //加载完数据后，隐藏刷新进度条
    }
    private ResponseHandlerInterface response=new NewsListHttpModel<NewsListEntity>(new TypeToken<ResponseEntity<NewsListEntity>>(){}) {
        @Override
        protected void onFailure() {}
        @Override
        protected void onSuccess(NewsListEntity result) {
            List<NewsEntity> list = result.getList();
            Toast.makeText(getActivity(), list.size() + "", Toast.LENGTH_LONG).show();
            swipeLayout.setRefreshing(false);
        }

        @Override
        protected void onError() {}
    };
//  private int totalNumber = 0;  //总列表数
//    private int addNumber;  //每次新增的资讯数量
//    public void addData() {
//        totalNumber = CollectNewsList.size();
//        addNumber = (int) (Math.random() * 10 + 1); //产生从1 - 10的随机数
//        String  newscontent=null;
//        String newsfirstWord ="A";
//        for (int i = 1; i < 20; i++) {
//
//            collectnews = new CollectNews();
//            collectnews.setNewscontent("android大家还哦啊阿斯顿和覅额" );
//            collectnews.setNewsfirstWord(newsfirstWord);
//            CollectNewsList.add( collectnews);
//        }
   // }

}



