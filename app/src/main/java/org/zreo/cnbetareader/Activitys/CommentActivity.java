package org.zreo.cnbetareader.Activitys;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;

import org.zreo.cnbetareader.Adapters.CommentAdapter;
import org.zreo.cnbetareader.Entitys.CnComment;
import org.zreo.cnbetareader.R;
import org.zreo.cnbetareader.Views.XListView;

import java.util.ArrayList;
import java.util.List;


public class CommentActivity extends AppCompatActivity implements XListView.IXListViewListener, View.OnClickListener {

    // 最大数据条数
    private static final int MAX_DATA_NUM = 100;

    private View moreDataView;
    private Button loadButton;
    private ProgressBar progressBar;

    private int lastVisibleItemIndex;

    private Handler handler = new Handler();

    private List<CnComment> cnCommentList = new ArrayList<CnComment>();
//    private QuickReturnListView mListView;
    private ImageView mQuickReturnButton;
    private int mQuickReturnHeight;
    CommentAdapter myAdapter;

    private static final int STATE_ONSCREEN = 0;
    private static final int STATE_OFFSCREEN = 1;
    private static final int STATE_RETURNING = 2;
    private int mState = STATE_ONSCREEN;
    private int mScrollY;
    private int mMinRawY = 0;
     private Toolbar mToolbar;

    private TranslateAnimation anim;
    private XListView myListView;
    private ImageView mQuickReturnView; // 下拉快速显示item功能
    public CommentActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.comment_listview);
        initView();
        initCommentList();
    }




    private CnComment loadMoreData() {
        String userName = "匿名用户";
        String textComment = "100块都不给我";
        String sText = "支持:1 ";
        String aText = "反对:0";
        CnComment cnComments = new CnComment();
        cnComments.setImageId(R.drawable.circle_image);
        cnComments.setUserName("1111" + userName);
        cnComments.setTestComment(textComment);
        cnComments.setCommentMenu(R.drawable.more_grey);
        cnComments.setSupport(sText);
        cnComments.setAgainst(aText);
//                cnCommentList.add(cnComments);
        return cnComments;
    }

    /**
     * 初始化布局
     */
    private void initView() {
        myListView = (XListView)findViewById(R.id.xListView);
        myListView.setPullRefreshEnable(false);
        myListView.setPullLoadEnable(true);
        myAdapter = new CommentAdapter(this, R.layout.comment_textview, cnCommentList);
        myListView.setAdapter(myAdapter);
        myListView.setXListViewListener(this);
        // 下拉快速显示item功能
        mQuickReturnView = (ImageView) findViewById(R.id.forum_listview_linearfooter);
        mQuickReturnView.setOnClickListener(this); // 下拉快速显示item功能
        myListView.setQuickReturnView(mQuickReturnView);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);   //ToolBar布局
        mToolbar.setTitle("评论");   // 标题的文字需在setSupportActionBar之前，不然会无效
        mToolbar.setTitleTextColor(Color.WHITE);  //设置ToolBar字体颜色为白色
        setSupportActionBar(mToolbar);  //将ToolBar设置为ActionBAr
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);  //在ToolBar左边，即当前标题前添加图标
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
    /**
     * 实现按钮下拉浮现，上滑隐藏
     */
//    private void initButton() {
//
//        mListView.getViewTreeObserver().addOnGlobalLayoutListener(
//                new ViewTreeObserver.OnGlobalLayoutListener() {
//                    @Override
//                    public void onGlobalLayout() {
//                        mQuickReturnHeight = mQuickReturnButton.getHeight();
//                        mListView.computeScrollY();
//                    }
//                });
//        mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
//           // private int visibleItemCount;       // 当前窗口可见项总数
//            @SuppressLint("NewApi")
//            @Override
//
//            public void onScroll(AbsListView view, int firstVisibleItem,
//                                 int visibleItemCount, int totalItemCount) {
//                mScrollY = 0;
//                int translationY = 0;
//                if (mListView.scrollYIsComputed()) {
//                    mScrollY = mListView.getComputedScrollY();
//                }
//                int rawY = mScrollY;
//                switch (mState) {
//                    case STATE_OFFSCREEN:
//                        if (rawY >= mMinRawY) {
//                            mMinRawY = rawY;
//                        } else {
//                            mState = STATE_RETURNING;
//                        }
//                        translationY = rawY;
//                        break;
//                    case STATE_ONSCREEN:
//                        if (rawY > mQuickReturnHeight) {
//                            mState = STATE_OFFSCREEN;
//                            mMinRawY = rawY;
//                        }
//                        translationY = rawY;
//                        break;
//
//                    case STATE_RETURNING:
//                        translationY = (rawY - mMinRawY) + mQuickReturnHeight;
//                        if (translationY < 0) {
//                            translationY = 0;
//                            mMinRawY = rawY + mQuickReturnHeight;
//                        }
//                        if (rawY == 0) {
//                            mState = STATE_ONSCREEN;
//                            translationY = 0;
//                        }
//                        if (translationY > mQuickReturnHeight) {
//                            mState = STATE_OFFSCREEN;
//                            mMinRawY = rawY;
//                        }
//                        break;
//                }
//                //** this can be used if the build is below honeycomb **//*
//                if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.HONEYCOMB) {
//                    anim = new TranslateAnimation(0, 0, translationY,
//                            translationY);
//                    anim.setFillAfter(true);
//                    anim.setDuration(0);
//                    mQuickReturnButton.startAnimation(anim);
//                } else {
//                    mQuickReturnButton.setTranslationY(translationY);
//                }
//            }
//
//            public void onScrollStateChanged(AbsListView view, int scrollState) {
//            }
//        });
//    }

    /**
     * 初始化评论列表
     */
    private void initCommentList() {
        String userName = "匿名用户";
        String textComment = "100块都不给我";
        String sText = "支持:1 ";
        String aText = "反对:0";
        ArrayList<CnComment> resultList = new ArrayList<CnComment>();
        for (int i = 0; i < 30; i++) {

            CnComment cnComments = new CnComment();
            cnComments.setImageId(R.drawable.circle_image);
            cnComments.setUserName(i+userName);
            cnComments.setTestComment(textComment);
            cnComments.setCommentMenu(R.drawable.more_grey);
            cnComments.setSupport(sText);
            cnComments.setAgainst(aText);
            resultList.add(cnComments);
        }
        myAdapter.AddData(resultList);
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onLoadMore() {
        initCommentList();
        myListView.stopRefresh();
        myListView.stopLoadMore();
    }

    /**悬浮按钮点击响应
     * @param v
     */
    @Override
    public void onClick(View v) {
        Intent intent = new Intent(CommentActivity.this, PostCommentActivity.class);
        startActivity(intent);
    }
}
