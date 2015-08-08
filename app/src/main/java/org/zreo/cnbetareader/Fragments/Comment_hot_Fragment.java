package org.zreo.cnbetareader.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.zreo.cnbetareader.Adapters.Comment_hot_Adapter;
import org.zreo.cnbetareader.Model.CnComment_hot;
import org.zreo.cnbetareader.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by guo on 2015/7/29.
 */
public class Comment_hot_Fragment extends Fragment implements AbsListView.OnScrollListener, SwipeRefreshLayout.OnRefreshListener{
    Toast toast;
    TextView toastTextView;

    View view;  //当前布局
    private ListView hot_listView;
    private List<CnComment_hot> cnComment_hotList = new ArrayList<CnComment_hot>();
    Comment_hot_Adapter mAdapter;

    private int visibleLastIndex = 0;   //最后的可视项索引
    private int visibleItemCount;       // 当前窗口可见项总数
    private View loadMoreView;     //加载更多布局

    SwipeRefreshLayout swipeLayout;  //下拉刷新控件

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.fragment_c_hot_listview, container, false);

        initComment_hotList(); //初始化新闻列表
        initView();  //初始化布局
        customToast();
        return  view;
    }

    /**
     *  初始化布局
     */
    private void initView() {
        /**显示hot的ListView*/
        hot_listView = (ListView) view.findViewById(R.id.hot_listView);
        /**为ListView创建自定义适配器*/
        mAdapter = new Comment_hot_Adapter(getActivity(), R.layout.fragment_c_hot_textview,cnComment_hotList);
        hot_listView.setVerticalScrollBarEnabled(false);//隐藏ListView滑动进度条
        loadMoreView = getActivity().getLayoutInflater().inflate(R.layout.load_more, null);
        hot_listView.addFooterView(loadMoreView);   //设置列表底部视图
        hot_listView.setOnScrollListener(this);     //添加滑动监听
        hot_listView.setAdapter(mAdapter);  //为ListView绑定Adapter

        swipeLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_hot);
        swipeLayout.setOnRefreshListener(this);
        //设置刷新时动画的颜色，可以设置4个
        swipeLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        hot_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               // to do something
            }
        });
    }



    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        int itemsLastIndex = cnComment_hotList.size() - 1;    //数据集最后一项的索引
        int lastIndex = itemsLastIndex + 1;             //加上底部的loadMoreView项
        if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE && visibleLastIndex == lastIndex) {
            //如果是自动加载,可以在这里放置异步加载数据的代码
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    addData();
                    mAdapter.notifyDataSetChanged(); //数据集变化后,通知adapter
                    ((TextView) loadMoreView.findViewById(R.id.load_more)).setText("加载中...");
                    toastTextView.setText("新增" + addNumber + "条资讯");
                    toast.show();
                }
            }, 2000); //模拟加载自动加载太快，所以模拟加载延时执行

            new Handler().postDelayed(new Runnable() {
                public void run() {
                    ((TextView) loadMoreView.findViewById(R.id.load_more)).setText("加载更多");
                }
            }, 1800); //模拟加载自动加载太快，所以模拟加载延时执行
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        this.visibleItemCount = visibleItemCount;
        visibleLastIndex = firstVisibleItem + visibleItemCount - 1;
    }

    @Override
    public void onRefresh() {
        addData1();
        toastTextView.setText("新增" + addNumber + "条资讯");
        toast.show();
        mAdapter.notifyDataSetChanged();
        swipeLayout.setRefreshing(false);
        //BaseHttpClient.getInsence().getNewsListByPage("all", "1", response);
    }
    // private ResponseHandlerInterface response=new NewsListHttpModel<NewsListEntity>(new TypeToken<ResponseEntity<NewsListEntity>>(){}) {
       // @Override
       // protected void onFailure() {

       // }

       // @Override
       // protected void onSuccess(NewsListEntity result) {
        //    List<NewsEntity> list = result.getList();
         //   Toast.makeText(getActivity(), list.size() + "", Toast.LENGTH_LONG).show();
        //    swipeLayout.setRefreshing(false);
      //  }

      //  @Override
       // protected void onError() {
//
   //     }
   // };
    /**
     * 自定义Toast，用于数据更新的提示
     */
    private void customToast() {
        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);  //获取屏幕分辨率
        ViewGroup.LayoutParams params = new LinearLayout.LayoutParams(dm.widthPixels, dm.heightPixels/15);
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View toastView = inflater.inflate(R.layout.toast, null);  // 取得xml里定义的view
        toastTextView = (TextView) toastView.findViewById(R.id.toast_text); // 取得xml里定义的TextView
        toastTextView.setLayoutParams(params);  //设置Toast的宽度和高度
        toast = new Toast(getActivity());
        toast.setGravity(Gravity.BOTTOM, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(toastView);
        //toast.show();
    }

    private int totalNumber = 0;  //总列表数
    private int addNumber;  //每次新增的资讯数量

    public void addData(){
        totalNumber = cnComment_hotList.size();
        addNumber = (int)(Math.random() * 10 + 1); //产生从1 - 10的随机数
        String content = "有些人会说没有什么是这城里人不会的。就是比你会，信不信由你！就是你会我那，信不信我吧你发吧。干掉。呵呵你是就是这样子的。没有办发和你在意跑的都快打开！";
        String comment = "城里人真会玩";
        String firstWord ="有";
        for(int i = 1; i < 20; i++){

            CnComment_hot cnComment_hots = new CnComment_hot();
            cnComment_hots.setComment("匿名网友 评论于 "+comment);
            cnComment_hots.setContent(content);
            cnComment_hots.setFirstWord(firstWord);
            cnComment_hotList.add(cnComment_hots);
        }


        totalNumber = totalNumber + addNumber;
        addNumber =cnComment_hotList.size()-totalNumber ;
    }
    public void addData1(){
        totalNumber = cnComment_hotList.size();
        addNumber = (int)(Math.random() * 10+ 1); //产生从1 - 10的随机数
        String content = "有些人会说没有什么是这城里人不会的。就是比你会，信不信由你！jiosudjisufai hods和速度000000000000恢复拉萨的那份洛克时0000000000000代拉开距离就是的离00000000000开房间爱死了快递费就分开了激发了卡拉科技阿萨德可立即阿萨德啊落实到";
        String comment = "城里人真会玩";
        String firstWord ="有";
        for(int i = 1; i < 20; i++){

            CnComment_hot cnComment_hots = new CnComment_hot();
            cnComment_hots.setComment("匿名网友 评论于 "+comment);
            cnComment_hots.setContent(content);
            cnComment_hots.setFirstWord(firstWord);
            cnComment_hotList.add(0,cnComment_hots);
        }


        totalNumber = totalNumber + addNumber;
        addNumber =cnComment_hotList.size()-totalNumber ;
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


