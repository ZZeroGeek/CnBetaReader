package org.zreo.cnbetareader.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import org.zreo.cnbetareader.Adapters.CommentTop10Adapter;
import org.zreo.cnbetareader.Entitys.NewsEntity;
import org.zreo.cnbetareader.Model.CnCommentTop10;
import org.zreo.cnbetareader.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/7/29.
 */
public class Comment_Top10Fragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener  {
    View view;
    private ListView top10_listView;
    private CommentTop10Adapter mAdapter;
    private List<CnCommentTop10> cnCommentTop10List = new ArrayList<CnCommentTop10>();
    private View loadMoreView;     //加载更多布局
    private TextView loadMoreText;    //加载提示文本
    SwipeRefreshLayout swipeLayout;  //下拉刷新控件
    private List<NewsEntity> listItems;
    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState){
        view = inflater.inflate(R.layout.fragment_c_top10listview, container, false);
        initCommentTop10List();
        initView();
        //top10_listView = (ListView)rootview.findViewById(R.id.top10_listView);
        //CommentTop10Adapter myAdapter = new CommentTop10Adapter(getActivity(),R.layout.fragment_c_top10textview,cnCommentTop10List);
        // top10_listView.setAdapter(myAdapter);
        return view;
    }
    private void initCommentTop10List(){
        String title = "没有你会不会是一次最美好的时光？还是最悲哀的岁月？爱你，真的不简单！";
        String hot = "热度:25452";
        //String ranking ="1";
        for(int i = 1; i < 11; i++){

            CnCommentTop10 cnCommentTop10s = new CnCommentTop10();
            cnCommentTop10s.setNewsTitle(title);
            cnCommentTop10s.setRanking(i + "");
            cnCommentTop10s.setHot(hot);
            cnCommentTop10List.add(cnCommentTop10s);
        }
    }
    private void initView(){
        /**显示CnCommentTop10的ListView*/
        top10_listView = (ListView)view.findViewById(R.id.top10_listView);
        /**为ListView创建自定义适配器*/
        mAdapter = new CommentTop10Adapter(getActivity(),R.layout.fragment_c_top10textview,cnCommentTop10List);
        //top10_listView .setVerticalScrollBarEnabled(false);//隐藏ListView滑动进度条
        loadMoreView = getActivity().getLayoutInflater().inflate(R.layout.load_more, null);
        loadMoreText = (TextView) loadMoreView.findViewById(R.id.load_more);
        loadMoreText.setText("--The End--");
        top10_listView .addFooterView(loadMoreView);   //设置列表底部视图
        // top10_listView .setOnScrollListener(this);     //添加滑动监听
        top10_listView .setAdapter(mAdapter);  //为ListView绑定Adapter

        swipeLayout = (SwipeRefreshLayout)view.findViewById(R.id.swipe_top);
        view.setEnabled(false);
        swipeLayout.setOnRefreshListener(this);
        //设置刷新时动画的颜色，可以设置4个
        swipeLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        /**事件点击*/
        top10_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //to do something
            }
        });
    }

   // private int visibleLastIndex = 0;   //最后的可视项索引
   // private int visibleItemCount;       // 当前窗口可见项总数
   // @Override
   // public void onScrollStateChanged(AbsListView view, int scrollState) {
   //     int itemsLastIndex = cnCommentTop10List.size() - 1;    //数据集最后一项的索引
    //    int lastIndex = itemsLastIndex + 1;             //加上底部的loadMoreView项
    //    if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE && visibleLastIndex == lastIndex) {

    //   }
    // }
   // @Override
   // public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
   //     this.visibleItemCount = visibleItemCount;
   //     visibleLastIndex = firstVisibleItem + visibleItemCount - 1;
   // }
    private void addData(){
        String title = "囧事一摞摞，最美丽的就是追好的！";
        String hot = "热度:25452";
       // String ranking ="i";
        for(int i = 10; i >0; i--){

            CnCommentTop10 cnCommentTop10s = new CnCommentTop10();
            cnCommentTop10s.setNewsTitle(title);
            cnCommentTop10s.setRanking(i+"");
            cnCommentTop10s.setHot(hot);
            cnCommentTop10List.add(0,cnCommentTop10s);
        }
    }
    @Override
    public void onRefresh() {
        addData();
        mAdapter.notifyDataSetChanged();
        swipeLayout.setRefreshing(false);   //加载完数据后，隐藏刷新进度条
    }
//    private ResponseHandlerInterface response=new NewsListHttpModel<NewsListEntity>(new TypeToken<ResponseEntity<NewsListEntity>>(){}) {
//        @Override
//        protected void onFailure() {
//        }
//
//        @Override
//        protected void onSuccess(NewsListEntity result) {
//            List<NewsEntity> list = result.getList();
//            Toast.makeText(getActivity(), list.size() + "", Toast.LENGTH_LONG).show();
//            swipeLayout.setRefreshing(false);
//        }
//
//        @Override
//        protected void onError() {
//        }
    };
