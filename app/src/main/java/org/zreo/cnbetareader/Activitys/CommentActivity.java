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
import org.zreo.cnbetareader.Model.CnComment;
import org.zreo.cnbetareader.Model.QuickReturnListView;
import org.zreo.cnbetareader.R;

import java.util.ArrayList;
import java.util.List;


public class CommentActivity extends ListActivity implements AbsListView.OnScrollListener{

    private List<CnComment> cnCommentList = new ArrayList<CnComment>();
    private QuickReturnListView mListView;
    private ImageButton mQuickReturnButton;
    private int mQuickReturnHeight;
    CommentAdapter myAdapter;

    Toast toast;  //数据更新提示的Toast
    TextView toastTextView; //Toast显示的文本

    private int visibleLastIndex = 0;   //最后的可视项索引
    private int visibleItemCount;       // 当前窗口可见项总数
    private View loadMoreView;     //加载更多布局

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
        initCommentList();
        initView();
        initButton();
        customToast();
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

        mListView.setVerticalScrollBarEnabled(false);//隐藏ListView滑动进度条
        loadMoreView = this.getLayoutInflater().inflate(R.layout.load_more, null);
        mListView.addFooterView(loadMoreView);   //设置列表底部视图
        mListView.setOnScrollListener(this);     //添加滑动监听

        mListView.setAdapter(myAdapter);
        mQuickReturnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CommentActivity.this, PostCommentActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        int itemsLastIndex = cnCommentList.size() - 1;    //数据集最后一项的索引
        int lastIndex = itemsLastIndex + 1;             //加上底部的loadMoreView项
        if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE && visibleLastIndex == lastIndex) {
            //如果是自动加载,可以在这里放置异步加载数据的代码
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    addComment();
                    myAdapter.notifyDataSetChanged(); //数据集变化后,通知adapter
                    ((TextView) loadMoreView.findViewById(R.id.load_more)).setText("加载中...");
                    toastTextView.setText("新增" + addNumber + "条评论");
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

    /**
     * 自定义Toast，用于数据更新的提示
     */
    private void customToast() {
        DisplayMetrics dm = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(dm);  //获取屏幕分辨率
        ViewGroup.LayoutParams params = new LinearLayout.LayoutParams(dm.widthPixels, dm.heightPixels/15);
        LayoutInflater inflater = this.getLayoutInflater();
        View toastView = inflater.inflate(R.layout.toast, null);  // 取得xml里定义的view
        toastTextView = (TextView) toastView.findViewById(R.id.toast_text); // 取得xml里定义的TextView
        toastTextView.setLayoutParams(params);  //设置Toast的宽度和高度
        toast = new Toast(CommentActivity.this);
        toast.setGravity(Gravity.BOTTOM, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(toastView);
        //toast.show();
    }

    private int totalNumber = 0;  //总列表数
    private int addNumber;  //每次新增的评论数量
    public void addComment(){
        totalNumber = cnCommentList.size();
        addNumber = (int)(Math.random() * 10 + 1); //产生从1 - 10的随机数
        String userName = "匿名用户";
        String textComment = "100块都不给我";
        String sText = "支持:1 ";
        String aText = "反对:0";
        for(int i = totalNumber; i < totalNumber + addNumber; i++){
            CnComment cnComments = new CnComment();
            cnComments.setImageId(R.drawable.ic_launcher);
            cnComments.setUserName(userName);
            cnComments.setTestComment(textComment);
            cnComments.setCommentMenu(R.drawable.more_grey);
            cnComments.setSupport(sText);
            cnComments.setAgainst(aText);
            cnCommentList.add(0,cnComments);
        }
        totalNumber = totalNumber + addNumber;
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
         mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
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

                         System.out.println(translationY);
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

                 /** this can be used if the build is below honeycomb **/
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
     * */
    private void initCommentList(){
        String userName = "匿名用户";
        String textComment = "100块都不给我";
        String sText = "支持:1 ";
        String aText = "反对:0";
        for(int i = 0; i < 30; i++){

            CnComment cnComments = new CnComment();
            cnComments.setImageId(R.drawable.ic_launcher);
            cnComments.setUserName(userName);
            cnComments.setTestComment(textComment);
            cnComments.setCommentMenu(R.drawable.more_grey);
            cnComments.setSupport(sText);
            cnComments.setAgainst(aText);
            cnCommentList.add(0,cnComments);
        }
    }
}
