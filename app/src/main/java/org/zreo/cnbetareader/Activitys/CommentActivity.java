package org.zreo.cnbetareader.Activitys;

import android.annotation.SuppressLint;
import android.app.ListActivity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.TranslateAnimation;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import org.zreo.cnbetareader.Adapters.CommentAdapter;
import org.zreo.cnbetareader.Entitys.CnComment;
import org.zreo.cnbetareader.Model.QuickReturnListView;
import org.zreo.cnbetareader.R;

import java.util.ArrayList;
import java.util.List;


public class CommentActivity extends ListActivity   {

    // 最大数据条数
    private static final int MAX_DATA_NUM = 100;

    private View moreDataView;
    private Button loadButton;
    private ProgressBar progressBar;

    private int lastVisibleItemIndex;

    private Handler handler = new Handler();

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
    // private Toolbar mToolbar;

    private TranslateAnimation anim;

    public CommentActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.comment_listview);
        initView();
        initCommentList();
        loadComment();
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
     * 加载评论
     * */
    private void loadComment(){
        moreDataView = getLayoutInflater().inflate(R.layout.comment_load_more, null);
       // loadButton = (Button) moreDataView.findViewById(R.id.load_more_button);
       // mListView.addFooterView(moreDataView);
        //moreDataView = getLayoutInflater().inflate(R.layout.comment_load_more, null);
        loadButton = (Button) moreDataView.findViewById(R.id.loadButton);
        progressBar = (ProgressBar) moreDataView.findViewById(R.id.progressBar);
       // initCommentList();
        //myAdapter = new CommentAdapter(this, R.layout.comment_textview, cnCommentList);
        mListView.addFooterView(moreDataView);
        mListView.setAdapter(myAdapter);
        loadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                loadButton.setVisibility(View.GONE);
              new AsyncTask<Void, Integer, Boolean>() {
                    @Override
                    protected Boolean doInBackground(Void... params) {


                             //   progressBar.setVisibility(View.VISIBLE);
                             //   loadButton.setVisibility(View.GONE);
                                loadMoreData();
                                myAdapter.notifyDataSetChanged();

//                          handler.postDelayed(new Runnable() {
//                        @Override
//                        public void run () {
//                        loadMoreData();
//                        loadButton.setVisibility(View.VISIBLE);
//                        progressBar.setVisibility(View.GONE);
//                        myAdapter.notifyDataSetChanged();
//                        }
//                          },2000);
                       return true;
                    }

              };
            }
        });
    }

    private void loadMoreData() {
        String userName = "匿名用户";
        String textComment = "100块都不给我";
        String sText = "支持:1 ";
        String aText = "反对:0";
       // initCommentList();
      // int count = myAdapter.getCount();
       // if (count + 5 < MAX_DATA_NUM) {
           // for (int i = count; i < count + 5; i++) {
                CnComment cnComments = new CnComment();
                cnComments.setImageId(R.drawable.circle_image);
                cnComments.setUserName("1111"+userName);
                cnComments.setTestComment(textComment);
                cnComments.setCommentMenu(R.drawable.more_grey);
                cnComments.setSupport(sText);
                cnComments.setAgainst(aText);
                cnCommentList.add(cnComments);
           // }
        }
//        // 剩余数据不足5条
//        else {
//            for (int i = count; i < MAX_DATA_NUM; i++) {
//                CnComment cnComments = new CnComment();
//                cnComments.setImageId(R.drawable.circle_image);
//                cnComments.setUserName(i+userName);
//                cnComments.setTestComment(textComment);
//                cnComments.setCommentMenu(R.drawable.more_grey);
//                cnComments.setSupport(sText);
//                cnComments.setAgainst(aText);
//                cnCommentList.add(cnComments);
//            }
//        }
   // }


    /**
     * 初始化布局
     */
    private void initView() {
        mQuickReturnButton = (ImageButton) findViewById(R.id.footer);
        mListView = (QuickReturnListView) getListView();
        myAdapter = new CommentAdapter(this, R.layout.comment_textview, cnCommentList);

        mListView.setAdapter(myAdapter);
        mQuickReturnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CommentActivity.this, PostCommentActivity.class);
                startActivity(intent);
            }
        });
    }
    /**
     * 实现按钮下拉浮现，上滑隐藏
     */
    private void initButton() {

        mListView.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        mQuickReturnHeight = mQuickReturnButton.getHeight();
                        mListView.computeScrollY();
                    }
                });
        mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
           // private int visibleItemCount;       // 当前窗口可见项总数
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

            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }
        });
    }

    /**
     * 初始化评论列表
     */
    private void initCommentList() {
        String userName = "匿名用户";
        String textComment = "100块都不给我";
        String sText = "支持:1 ";
        String aText = "反对:0";
        for (int i = 0; i < 30; i++) {

            CnComment cnComments = new CnComment();
            cnComments.setImageId(R.drawable.circle_image);
            cnComments.setUserName(i+userName);
            cnComments.setTestComment(textComment);
            cnComments.setCommentMenu(R.drawable.more_grey);
            cnComments.setSupport(sText);
            cnComments.setAgainst(aText);
            cnCommentList.add(cnComments);
        }
    }
}
