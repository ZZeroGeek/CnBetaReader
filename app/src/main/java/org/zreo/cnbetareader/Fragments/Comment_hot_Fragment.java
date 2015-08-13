package org.zreo.cnbetareader.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
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

import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.ResponseHandlerInterface;

import org.zreo.cnbetareader.Adapters.HotComment_Adapter;
import org.zreo.cnbetareader.AppConfig;
import org.zreo.cnbetareader.Entitys.HotCommentItemEntity;
import org.zreo.cnbetareader.Entitys.ResponseEntity;
import org.zreo.cnbetareader.Model.Net.HttpDateModel;
import org.zreo.cnbetareader.Net.BaseHttpClient;
import org.zreo.cnbetareader.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;

/**
 * Created by guo on 2015/7/29.
 */
public class Comment_hot_Fragment extends Fragment implements AbsListView.OnScrollListener, SwipeRefreshLayout.OnRefreshListener {
    Toast toast;
    TextView toastTextView;

    View view;  //当前布局
    private ListView hot_listView;
    private List<HotCommentItemEntity> cnComment_hotList = new ArrayList<HotCommentItemEntity>();
    HotComment_Adapter mAdapter;

    private int visibleLastIndex = 0;   //最后的可视项索引
    private int visibleItemCount;       // 当前窗口可见项总数
    private View loadMoreView;     //加载更多布局

    SwipeRefreshLayout swipeLayout;  //下拉刷新控件

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_c_hot_listview, container, false);

        customToast();

        BaseHttpClient.getInsence().getNewsListByPage("jhcomment", "1", initResponse);

        if(cnComment_hotList.size() > 0){
            initView();  //初始化布局
        }

        return view;
    }

    private ResponseHandlerInterface initResponse = new HttpDateModel<List<HotCommentItemEntity>>
                            (new TypeToken<ResponseEntity<List<HotCommentItemEntity>>>() {}) {
        @Override
        protected void onSuccess(List<HotCommentItemEntity> result) {
            /**
             * 补充实体类中的信息
             */
            for (HotCommentItemEntity item:result){
                Matcher hotMatcher = AppConfig.HOT_COMMENT_PATTERN.matcher(item.getDescription());
                if (hotMatcher.find()) {
                    item.setFrom(hotMatcher.group(1));
                    item.setDescription(hotMatcher.group(2));
                    item.setSid(Integer.parseInt(hotMatcher.group(3)));
                    item.setNewstitle(hotMatcher.group(4));
                }
            }

            Map<Integer, HotCommentItemEntity> tempMap = new HashMap<Integer, HotCommentItemEntity>();


            for(int i = 0; i < result.size(); i++){
                tempMap.put(result.get(i).getSid(), result.get(i));
            }
            cnComment_hotList = new ArrayList<HotCommentItemEntity>(tempMap.values());
            if(cnComment_hotList.size() > 0){
                initView();  //初始化布局
            }

        }

        @Override
        protected void onError() {
            toastTextView.setText("刷新错误");
            toast.show();

        }

        @Override
        protected void onFailure() {
            toastTextView.setText("刷新失败，请检查网络");
            toast.show();

        }
    };

    /**
     * 初始化布局
     */
    private void initView() {
        /**显示hot的ListView*/
        hot_listView = (ListView) view.findViewById(R.id.hot_listView);
        /**为ListView创建自定义适配器*/
        mAdapter = new HotComment_Adapter(getActivity(), R.layout.fragment_c_hot_textview, cnComment_hotList);
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

        }
    }

    private int totalNumber = 0;  //总列表数
    private int addNumber;  //每次新增的资讯数量

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        this.visibleItemCount = visibleItemCount;
        visibleLastIndex = firstVisibleItem + visibleItemCount - 1;
    }

    @Override
    public void onRefresh() {

        toastTextView.setText("新增" + addNumber + "条资讯");
        toast.show();
        mAdapter.notifyDataSetChanged();
        swipeLayout.setRefreshing(false);
    }


    /**
     * 自定义Toast，用于数据更新的提示
     */
    private void customToast() {
        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);  //获取屏幕分辨率
        ViewGroup.LayoutParams params = new LinearLayout.LayoutParams(dm.widthPixels, dm.heightPixels / 15);
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




    /*public void initComment_hotList() {
        String title = "有些人会说没有什么是这城里人不会的。就是比你会，信不信由你！";
        String newstitle = "城里人真会玩";
        String description ="nJohn";
        //String first = "有";
        for (int i = 1; i < 20; i++) {

            HotCommentItemEntity hotCommentItemEntitys = new HotCommentItemEntity();
            hotCommentItemEntitys.setNewstitle("评论于  " + newstitle);
            hotCommentItemEntitys.setTitle(title);
            hotCommentItemEntitys.setDescription(description + " ");
           // cnComment_hots.setFirstWord(firstWord);
            cnComment_hotList.add(hotCommentItemEntitys);
        }
    }*/
}


