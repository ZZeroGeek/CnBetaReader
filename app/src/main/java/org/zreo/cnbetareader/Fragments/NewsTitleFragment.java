package org.zreo.cnbetareader.Fragments;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.ResponseHandlerInterface;
import com.nostra13.universalimageloader.core.listener.PauseOnScrollListener;

import org.zreo.cnbetareader.Activitys.NewsActivity;
import org.zreo.cnbetareader.Adapters.NewsTitleAdapter;
import org.zreo.cnbetareader.Entitys.NewsEntity;
import org.zreo.cnbetareader.Entitys.NewsListEntity;
import org.zreo.cnbetareader.Entitys.ResponseEntity;
import org.zreo.cnbetareader.Model.Net.NewsListHttpModel;
import org.zreo.cnbetareader.Net.BaseHttpClient;
import org.zreo.cnbetareader.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by guang on 2015/7/23.
 *显示新闻列表 *
 */
public class NewsTitleFragment extends Fragment implements AbsListView.OnScrollListener, SwipeRefreshLayout.OnRefreshListener{

    Toast toast;  //数据更新提示的Toast
    TextView toastTextView; //Toast显示的文本

    View view;  //当前布局
    private ListView lv;
    /**定义一个动态数组，保存新闻信息*/
    //private List<News> listItem = new ArrayList<News>();
    private List<NewsEntity> listItems = new ArrayList<NewsEntity>();
    NewsTitleAdapter mAdapter;

    private int visibleLastIndex = 0;   //最后的可视项索引
    private int visibleItemCount;       // 当前窗口可见项总数
    private View loadMoreView;     //加载更多布局

    SwipeRefreshLayout swipeLayout;  //下拉刷新控件

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.fragment_news_title, container, false);
        setHasOptionsMenu(true); //在fragment中使用menu菜单
        initListItem(); //初始化新闻列表
        new Thread(new Runnable() {
            @Override
            public void run() {
                BaseHttpClient.getInsence(getActivity()).getNewsListByPage("all","1",response);
                }
        }).start();
        initView();  //初始化布局
        customToast();//定义Toast，用于数据更新的提示
        return  view;
    }

    /**初始化布局 */
    private void initView() {
        /**显示新闻标题的ListView*/
        lv = (ListView) view.findViewById(R.id.news_title_list_view);
        /**为ListView创建自定义适配器*/
        mAdapter = new NewsTitleAdapter(getActivity(), R.layout.news_title_item, listItems);
        lv.setVerticalScrollBarEnabled(false);//隐藏ListView滑动进度条
        loadMoreView = getActivity().getLayoutInflater().inflate(R.layout.load_more, null);
        lv.addFooterView(loadMoreView);   //设置列表底部视图
        lv.setOnScrollListener(this);     //添加滑动监听
        lv.setAdapter(mAdapter);  //为ListView绑定Adapter
        /**为ListView添加点击事件*/
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), NewsActivity.class);
                startActivity(intent);
            }
        });

        swipeLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_container);
        swipeLayout.setOnRefreshListener(this);
        //设置刷新时动画的颜色，可以设置4个
        swipeLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
    }

    /**
     * 初始化新闻列表
     */
    public void initListItem(){
        String title = "Windows 10应用商店中国定制版现身 系统界面曝光";
        String summary = "7月24日消息，昨日有网友在国内某知名论坛发布疑似Win10应用商店中国定制版的系统界面图片，" +
                "一时间引发诸多热议。这名网友发帖称是从内部人士手里拿到了Win10特别版的系统映像，安装后发现这竟然" +
                "是Win10针对中国地区的定制版本。系统中除内置了很多微软旗下的服务外，还有一些本地化的功能。" +
                "据此他猜测，这极有可能就是专门提供给中国盗版用户免费使用的定制版本。";
        /**为动态数组添加数据*/
            NewsEntity news = new NewsEntity();
            news.setTitle(title);
            news.setHometext(summary);
            news.setInputtime("2015-07-24 10:30:38");
            //news.setImageId(R.mipmap.news_picture);
            news.setComments( 20);
            news.setCounter(100);
            listItems.add(0,news);
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        int itemsLastIndex = listItems.size() - 1;    //数据集最后一项的索引
        int lastIndex = itemsLastIndex + 1;             //加上底部的loadMoreView项
        if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE && visibleLastIndex == lastIndex) {
            page++;
            BaseHttpClient.getInsence(getActivity()).getNewsListByPage("all", String.valueOf(page), response);
            //如果是自动加载,可以在这里放置异步加载数据的代码
  /*          new Handler().postDelayed(new Runnable() {
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
            }, 1800); //模拟加载自动加载太快，所以模拟加载延时执行*/
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        this.visibleItemCount = visibleItemCount;
        visibleLastIndex = firstVisibleItem + visibleItemCount - 1;
    }

    private int page = 1; //传入页数
    private int lastSize = 0; //更新前的列表数
    @Override
    public void onRefresh() {
        // 这里做联网请求，然后handler处理完成之后的事情
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                if (totalNumber < 30) {
//                    addData();  //加载刷新数据
//                    mAdapter.notifyDataSetChanged(); //数据集变化后,通知adapter
//                    toastTextView.setText("新增" + addNumber+ "条资讯");
//                } else {
//                    toastTextView.setText("没有更多内容了");
//                    if(toastTextView.getText().toString().equals("没有更多内容了")) {
//                        toastTextView.setText("刚刚刷新过，等下再试吧");
//                    }
//                }
//                swipeLayout.setRefreshing(false);   //加载完数据后，隐藏刷新进度条
//                toast.show();
//            }
//        }, 1000);
        page++;
        BaseHttpClient.getInsence(getActivity()).getNewsListByPage("all", String.valueOf(page), response);
    }
    private ResponseHandlerInterface response = new NewsListHttpModel<NewsListEntity>
            (new TypeToken<ResponseEntity<NewsListEntity>>(){}) {
    @Override
    protected void onFailure() {

    }

    @Override
    protected void onSuccess(NewsListEntity result) {
        List<NewsEntity> list = result.getList();
        lastSize = listItems.size();
        for (int i = 0 ; i < 40; i++){
            listItems.add(0, list.get(i));
        }
        mAdapter.notifyDataSetChanged(); //数据集变化后,通知adapter
        swipeLayout.setRefreshing(false); //加载完数据后，隐藏刷新进度条
        toastTextView.setText("新增" + (listItems.size() - lastSize) + "条资讯");
        toast.show();
    }

    @Override
    protected void onError() {

    }
};
    /**自定义Toast，用于数据更新的提示*/
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

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        getActivity().getMenuInflater().inflate(R.menu.menu_main, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.offline_download) {
            Toast.makeText(getActivity(), "离线下载", Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
