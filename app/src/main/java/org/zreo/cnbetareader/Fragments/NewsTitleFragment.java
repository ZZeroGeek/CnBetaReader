package org.zreo.cnbetareader.Fragments;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.DisplayMetrics;
import android.util.Log;
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
import org.zreo.cnbetareader.databases.NewsTitleDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by guang on 2015/7/23.
 *显示新闻列表 *
 */
public class NewsTitleFragment extends Fragment implements AbsListView.OnScrollListener, SwipeRefreshLayout.OnRefreshListener{


    View view;  //当前布局
    private ListView lv;
    private List<NewsEntity> listItems;   /**保存新闻信息*/
    NewsTitleAdapter mAdapter;

    Toast toast;  //数据更新提示的Toast
    TextView toastTextView; //Toast显示的文本

    private View loadMoreView;     //加载更多布局
    private TextView loadMoreText;    //加载提示文本


    private NewsTitleDatabase newsTitleDatabase;  //数据库
    private Handler handler;

    SwipeRefreshLayout swipeLayout;  //下拉刷新控件

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.fragment_news_title, container, false);
        setHasOptionsMenu(true); //在fragment中使用menu菜单

        newsTitleDatabase = NewsTitleDatabase.getInstance(getActivity());  //初始化数据库实例


        initListItem(); //初始化新闻列表

        customToast();//定义Toast，用于数据更新的提示
        return  view;
    }

    /**
     * 初始化新闻列表，第一次启动时先初始化数据库
     */
    public void initListItem(){
        listItems = newsTitleDatabase.loadNewsEntity(); //从数据库读取新闻列表
        if (listItems.size() > 0) {
            initView();  //初始化布局
        }
        else {
            //加载最新的新闻
            BaseHttpClient.getInsence(getActivity()).getNewsListByPage("all", "1", initResponse);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(2000);  //等待网络更新数据
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }
                    if (listItems.size() > 0) {
                        Message message = handler.obtainMessage();
                        message.what = 0x101;
                        handler.sendMessage(message);   //告诉主线程执行任务
                    }
                }
            }).start();
        }

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 0x101) {
                    initView();  //初始化布局
                }
                super.handleMessage(msg);
            }
        };
    }

    private ResponseHandlerInterface initResponse = new NewsListHttpModel<NewsListEntity>
            (new TypeToken<ResponseEntity<NewsListEntity>>(){}) {
        @Override
        protected void onFailure() {


        }

        @Override
        protected void onSuccess(NewsListEntity result) {
            List<NewsEntity> list = result.getList();
            for (int i = 0 ; i < list.size(); i++){
                newsTitleDatabase.saveNewsEntity(list.get(i));
            }

        }

        @Override
        protected void onError() {

        }
    };


    /**初始化布局 */
    private void initView() {

        /**显示新闻标题的ListView*/
        lv = (ListView) view.findViewById(R.id.news_title_list_view);
        /**为ListView创建自定义适配器*/
        mAdapter = new NewsTitleAdapter(getActivity(), R.layout.news_title_item, listItems);
        lv.setVerticalScrollBarEnabled(false);//隐藏ListView滑动进度条
        loadMoreView = getActivity().getLayoutInflater().inflate(R.layout.load_more, null);
        loadMoreText = (TextView) loadMoreView.findViewById(R.id.load_more);
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

    private int page = 1; //传入页数，第一页为最新的新闻资讯，依次类推
    /**滑到底部自动加载*/
    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        int itemsLastIndex = listItems.size() - 1;    //数据集最后一项的索引
        int lastIndex = itemsLastIndex + 1;             //加上底部的loadMoreView项
        if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE && visibleLastIndex == lastIndex) {
            page++;
            BaseHttpClient.getInsence(getActivity()).getNewsListByPage("all", String.valueOf(page), autoLoadResponse);
        }
    }

    private int visibleLastIndex = 0;   //最后的可视项索引
    private int visibleItemCount;       // 当前窗口可见项总数
    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        this.visibleItemCount = visibleItemCount;
        visibleLastIndex = firstVisibleItem + visibleItemCount - 1;
    }

/*          loadMoreText.setText("加载中...");
            loadMoreText.setText("加载更多");

            toastTextView.setText("新增" + addNumber + "条资讯");
            toastTextView.setText("没有更多内容了");
            toastTextView.getText().toString().equals("没有更多内容了")
            toastTextView.setText("刚刚刷新过，等下再试吧");
            toast.show();
            mAdapter.notifyDataSetChanged(); //数据集变化后,通知adapter
            */


    private int lastSize = 0; //更新前的列表数
    @Override
    public void onRefresh() {

        BaseHttpClient.getInsence(getActivity()).getNewsListByPage("all", "1", refreshResponse);
        swipeLayout.setRefreshing(false);   //加载完数据后，隐藏刷新进度条

    }

    /**刷新更新数据*/
    private ResponseHandlerInterface refreshResponse = new NewsListHttpModel<NewsListEntity>
            (new TypeToken<ResponseEntity<NewsListEntity>>(){}) {
        @Override
        protected void onFailure() {

        }

        @Override
        protected void onSuccess(NewsListEntity result) {

        }

        @Override
        protected void onError() {

        }
    };

    /**自动加载更新数据*/
    private ResponseHandlerInterface autoLoadResponse = new NewsListHttpModel<NewsListEntity>
            (new TypeToken<ResponseEntity<NewsListEntity>>(){}) {
        @Override
        protected void onFailure() {

        }

        @Override
        protected void onSuccess(NewsListEntity result) {

           /* List<NewsEntity> list = result.getList();
            for (int i = 0 ; i < list.size(); i++){
                newsTitleDatabase.saveNewsEntity(list.get(i));
            }*/

            List<NewsEntity> list = result.getList();
            lastSize = listItems.size();
            for (int i = 0 ; i < 40; i++){
                listItems.add(0, list.get(i));
                Log.d("NewsEntity", list.get(i).toString());
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
