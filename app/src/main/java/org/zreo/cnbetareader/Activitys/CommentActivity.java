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
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.zreo.cnbetareader.Adapters.CommentAdapter;
import org.zreo.cnbetareader.Entitys.CnComment;
import org.zreo.cnbetareader.Model.QuickReturnListView;
import org.zreo.cnbetareader.Net.BaseHttpClient;
import org.zreo.cnbetareader.R;

import java.util.ArrayList;
import java.util.List;


public class CommentActivity extends ListActivity   {

    private List<CnComment> cnCommentList = new ArrayList<CnComment>();
    private QuickReturnListView mListView;
    private ImageButton mQuickReturnButton;
    private int mQuickReturnHeight;
    CommentAdapter myAdapter;

    //private static final String TAG = "LOADMORE";
    private View loadMoreView;
    private Button loadMoreButton;
    private int visibleLastIndex = 0;   //最后的可视项索引
    //private int visibleItemCount;       // 当前窗口可见项总数
    private Handler handler = new Handler();
    private boolean isLoading = false;

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

        initButton();

        loadMoreView = getLayoutInflater().inflate(R.layout.comment_load_more, null);
        loadMoreButton = (Button) loadMoreView.findViewById(R.id.load_more_button);
        mListView.addFooterView(loadMoreView);

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

/*
    	private void initAdapter() {
		ArrayList<String> items = new ArrayList<String>();
		for (int i = 0; i < 10; i++) {
			items.add(String.valueOf(i + 1));
		}
		adapter = new ListViewAdapter(this, items);
	}*/

	/*@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		int itemsLastIndex = myAdapter.getCount() - 1;    //数据集最后一项的索引
		int lastIndex = itemsLastIndex + 1;             //加上底部的loadMoreView项
		if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE && visibleLastIndex == lastIndex) {
			//如果是自动加载,可以在这里放置异步加载数据的代码
			loadMore(null);
		}
	}*/

	/*public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
		this.visibleItemCount = visibleItemCount;
		visibleLastIndex = firstVisibleItem + visibleItemCount - 1;
	}*/

	/*public void loadMore(View view) {
		if (isLoading)
			return;
		loadMoreButton.setText("loading...");   //设置按钮文字loading
		isLoading = true;
		handler.postDelayed(new Runnable() {
			@Override
			public void run() {
				loadData();
                myAdapter.notifyDataSetChanged(); //数据集变化后,通知adapter
				loadMoreButton.setText("click to load more");    //恢复按钮文字
				isLoading = false;
			}
		}, 2000);
	}

	private void loadData() {
        String userName = "匿名用户";
        String textComment = "100块都不给我";
        String sText = "支持:1 ";
        String aText = "反对:0";
        for (int i = 0; i < 30; i++) {

            CnComment cnComments = new CnComment();
            cnComments.setImageId(R.drawable.circle_image);
            cnComments.setUserName("jiazai "+userName);
            cnComments.setTestComment(textComment);
            cnComments.setCommentMenu(R.drawable.more_grey);
            cnComments.setSupport(sText);
            cnComments.setAgainst(aText);
            cnCommentList.add(0, cnComments);
        }
	}*/

    /**
     *
     *
     * */



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

               // this.visibleItemCount = visibleItemCount;
               // visibleLastIndex = firstVisibleItem + visibleItemCount - 1;

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

//                int itemsLastIndex = myAdapter.getCount() - 1;    //数据集最后一项的索引
//                int lastIndex = itemsLastIndex + 1;             //加上底部的loadMoreView项
//                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE && visibleLastIndex == lastIndex) {
//                    //如果是自动加载,可以在这里放置异步加载数据的代码
//                    loadMore(null);
               // }
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
            cnComments.setUserName(userName);
            cnComments.setTestComment(textComment);
            cnComments.setCommentMenu(R.drawable.more_grey);
            cnComments.setSupport(sText);
            cnComments.setAgainst(aText);
            cnCommentList.add(0, cnComments);
        }
    }
}
