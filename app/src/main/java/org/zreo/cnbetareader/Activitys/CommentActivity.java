package org.zreo.cnbetareader.Activitys;

import android.annotation.SuppressLint;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.TranslateAnimation;
import android.widget.AbsListView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.zreo.cnbetareader.Adapters.CommentAdapter;
import org.zreo.cnbetareader.Entitys.CnComment;
import org.zreo.cnbetareader.Model.QuickReturnListView;
import org.zreo.cnbetareader.Net.BaseHttpClient;
import org.zreo.cnbetareader.R;

import java.util.ArrayList;
import java.util.List;


public class CommentActivity extends ListActivity implements AbsListView.OnScrollListener {

    private List<CnComment> cnCommentList = new ArrayList<CnComment>();
    private QuickReturnListView mListView;
    private ImageButton mQuickReturnButton;
    private int mQuickReturnHeight;
    CommentAdapter myAdapter;

    private static final int STATE_ONSCREEN = 0;
    private static final int STATE_OFFSCREEN = 1;
    private static final int STATE_RETURNING = 2;
    private int mState = STATE_ONSCREEN;
    private int mScrollY;
    private int mMinRawY = 0;
    private View loadMoreView;     //加载更多布局
   // private Toolbar mToolbar;

    private TranslateAnimation anim;

    public CommentActivity() {
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.comment_listview);
        initCommentList();
        initView();
        initButton();
       /* mToolbar = (Toolbar) findViewById(R.id.toolbar);   //ToolBar布局
        mToolbar.setTitle("发表评论");   // 标题的文字需在setSupportActionBar之前，不然会无效
        mToolbar.setTitleTextColor(Color.WHITE);  //设置ToolBar字体颜色为白色
        setSupportActionBar(mToolbar);  //将ToolBar设置为ActionBAr
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);  //在ToolBar左边，即当前标题前添加图标
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });*/
    }


    /**
     * 初始化布局
     * */
    private void initView(){
        mQuickReturnButton = (ImageButton)findViewById(R.id.footer);
        mListView = (QuickReturnListView)getListView();
        myAdapter = new CommentAdapter(this, R.layout.comment_textview, cnCommentList);
        loadMoreView = getLayoutInflater().inflate(R.layout.load_more, null);
        mListView.addFooterView(loadMoreView);   //设置列表底部视图
        mListView.setOnScrollListener(this);
        mListView.setAdapter(myAdapter);
        mQuickReturnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CommentActivity.this, PostCommentActivity.class);
                startActivity(intent);
            }
        });

        LinearLayout mLinearLayout = (LinearLayout) findViewById(R.id.load_more_LinearLayout);
        mLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = "匿名用户";
                String textComment = "100块都不给我";
                String sText = "支持:1 ";
                String aText = "反对:0";
                    CnComment cnComments = new CnComment();
                    cnComments.setImageId(R.drawable.circle_image);
                    cnComments.setUserName("加载" + userName);
                    cnComments.setTestComment(textComment);
                    cnComments.setCommentMenu(R.drawable.more_grey);
                    cnComments.setSupport(sText);
                    cnComments.setAgainst(aText);
                    cnCommentList.add(cnComments);

                // myAdapter.notifyDataSetChanged(); //数据集变化后,通知adapter


                Toast.makeText(CommentActivity.this, "点击", Toast.LENGTH_SHORT).show();
            }
        });



    }
    /**
     * 实现按钮下拉浮现，上滑隐藏
     * */
     private void initButton(){
         mListView.getViewTreeObserver().addOnGlobalLayoutListener(
                 new ViewTreeObserver.OnGlobalLayoutListener() {
                     @Override
                     public void onGlobalLayout() {
                         mQuickReturnHeight = mQuickReturnButton.getHeight();
                         mListView.computeScrollY();
                     }
                 });
         /*mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
             @SuppressLint("NewApi")
             @Override
             public void onScroll(AbsListView view, int firstVisibleItem,
                                  int visibleItemCount, int totalItemCount) {
                 mScrollY = 0;
                 int translationY = 0;
                 if (mListView.scrollYIsComputed()) {
                     mScrollY = mListView.getComputedScrollY();
                 }
                 int rawY = mScrollY;
                 switch (mState) {
                     case STATE_OFFSCREEN:
                         if (rawY >= mMinRawY) {
                             mMinRawY = rawY;
                         } else {
                             mState = STATE_RETURNING;
                         }
                         translationY = rawY;
                         break;
                     case STATE_ONSCREEN:
                         if (rawY > mQuickReturnHeight) {
                             mState = STATE_OFFSCREEN;
                             mMinRawY = rawY;
                         }
                         translationY = rawY;
                         break;

                     case STATE_RETURNING:
                         translationY = (rawY - mMinRawY) + mQuickReturnHeight;
                         if (translationY < 0) {
                             translationY = 0;
                             mMinRawY = rawY + mQuickReturnHeight;
                         }
                         if (rawY == 0) {
                             mState = STATE_ONSCREEN;
                             translationY = 0;
                         }
                         if (translationY > mQuickReturnHeight) {
                             mState = STATE_OFFSCREEN;
                             mMinRawY = rawY;
                         }
                         break;
                 }
                 *//** this can be used if the build is below honeycomb **//*
                 if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.HONEYCOMB) {
                     anim = new TranslateAnimation(0, 0, translationY,
                             translationY);
                     anim.setFillAfter(true);
                     anim.setDuration(0);
                     mQuickReturnButton.startAnimation(anim);
                 } else {
                     mQuickReturnButton.setTranslationY(translationY);
                 }
             }
             public void onScrollStateChanged(AbsListView view, int scrollState) {
             }
         });*/
     }

    /**
     * 初始化评论列表
     * */
    private void initCommentList(){
        String userName = "匿名用户";
        String textComment = "100块都不给我";
        String sText = "支持:1 ";
        String aText = "反对:0";
        for(int i = 0; i < 30; i++){

            CnComment cnComments = new CnComment();
            cnComments.setImageId(R.drawable.circle_image);
            cnComments.setUserName(userName);
            cnComments.setTestComment(textComment);
            cnComments.setCommentMenu(R.drawable.more_grey);
            cnComments.setSupport(sText);
            cnComments.setAgainst(aText);
            cnCommentList.add(0,cnComments);
        }
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

        mScrollY = 0;
        int translationY = 0;
        if (mListView.scrollYIsComputed()) {
            mScrollY = mListView.getComputedScrollY();
        }
        int rawY = mScrollY;
        switch (mState) {
            case STATE_OFFSCREEN:
                if (rawY >= mMinRawY) {
                    mMinRawY = rawY;
                } else {
                    mState = STATE_RETURNING;
                }
                translationY = rawY;
                break;
            case STATE_ONSCREEN:
                if (rawY > mQuickReturnHeight) {
                    mState = STATE_OFFSCREEN;
                    mMinRawY = rawY;
                }
                translationY = rawY;
                break;

            case STATE_RETURNING:
                translationY = (rawY - mMinRawY) + mQuickReturnHeight;
                if (translationY < 0) {
                    translationY = 0;
                    mMinRawY = rawY + mQuickReturnHeight;
                }
                if (rawY == 0) {
                    mState = STATE_ONSCREEN;
                    translationY = 0;
                }
                if (translationY > mQuickReturnHeight) {
                    mState = STATE_OFFSCREEN;
                    mMinRawY = rawY;
                }
                break;
        }
        //** this can be used if the build is below honeycomb **//*
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.HONEYCOMB) {
            anim = new TranslateAnimation(0, 0, translationY,
                    translationY);
            anim.setFillAfter(true);
            anim.setDuration(0);
            mQuickReturnButton.startAnimation(anim);
        } else {
            mQuickReturnButton.setTranslationY(translationY);
        }

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
    }
}
