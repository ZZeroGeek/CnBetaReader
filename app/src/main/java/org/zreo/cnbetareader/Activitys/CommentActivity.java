package org.zreo.cnbetareader.Activitys;

import android.annotation.SuppressLint;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.TranslateAnimation;
import android.widget.AbsListView;
import android.widget.ImageButton;

import org.zreo.cnbetareader.Adapters.CommentAdapter;
import org.zreo.cnbetareader.Model.CnComment;
import org.zreo.cnbetareader.Model.QuickReturnListView;
import org.zreo.cnbetareader.R;

import java.util.ArrayList;
import java.util.List;


public class CommentActivity extends ListActivity  {

    private List<CnComment> cnCommentList = new ArrayList<CnComment>();
    private QuickReturnListView mListView;
    private ImageButton mQuickReturnButton;
    private int mQuickReturnHeight;

    private static final int STATE_ONSCREEN = 0;
    private static final int STATE_OFFSCREEN = 1;
    private static final int STATE_RETURNING = 2;
    private int mState = STATE_ONSCREEN;
    private int mScrollY;
    private int mMinRawY = 0;

    private TranslateAnimation anim;

    public CommentActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.comment_listview);
        initCommentList();
        mQuickReturnButton = (ImageButton)findViewById(R.id.footer);
        mListView = (QuickReturnListView) getListView();
        CommentAdapter myAdapter = new CommentAdapter(this, R.layout.comment_textview, cnCommentList);
        mListView.setAdapter(myAdapter);

        mQuickReturnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CommentActivity.this, PostCommentActivity.class);
                startActivity(intent);
            }
        });


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

    private void initCommentList(){
        String userName = "匿名用户";
        String textComment = "100块都不给我";
        String SaText = "支持:1  反对:0";
        for(int i = 0; i < 30; i++){

            CnComment cnComments = new CnComment();
            cnComments.setImageId(R.drawable.ic_launcher);
            cnComments.setUserName(userName);
            cnComments.setTestComment(textComment);
            cnComments.setCommentMenu(R.drawable.more_grey);
            cnComments.setSupportAgainst(SaText);
            cnCommentList.add(cnComments);
        }
    }
}
