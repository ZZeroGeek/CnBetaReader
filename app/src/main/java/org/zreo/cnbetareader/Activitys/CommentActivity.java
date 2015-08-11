package org.zreo.cnbetareader.Activitys;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.ResponseHandlerInterface;

import org.zreo.cnbetareader.Adapters.CommentAdapter;
import org.zreo.cnbetareader.Database.CommentDatabase;
import org.zreo.cnbetareader.Entitys.CommentItemEntity;
import org.zreo.cnbetareader.Entitys.CommentListEntity;
import org.zreo.cnbetareader.Entitys.NewsEntity;
import org.zreo.cnbetareader.Entitys.ResponseEntity;
import org.zreo.cnbetareader.Model.Net.HttpDateModel;
import org.zreo.cnbetareader.Net.BaseHttpClient;
import org.zreo.cnbetareader.R;
import org.zreo.cnbetareader.Views.XListView;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;


public class CommentActivity extends AppCompatActivity implements XListView.IXListViewListener, View.OnClickListener {
    //    /**listItems跟map同步*/
    private List<NewsEntity> listItems;
   // private List<CommentItemEntity> listItems; // = new ArrayList<CommentItemEntity>();  // ListView item项，以添加顺序排序
    // ListView item项，以降序排序的新闻列表
    Map<Integer, CommentItemEntity> map = new TreeMap<Integer, CommentItemEntity>(new Comparator<Integer>() {  //将获取到的评论列表排序
        @Override
        public int compare(Integer lhs, Integer rhs) {
            return rhs.compareTo(lhs);   // 降序排序, 默认为升序
        }
    });
    private CommentDatabase commentDatabase;  //数据库
    private List<CommentItemEntity> cnCommentList = new ArrayList<CommentItemEntity>();
    CommentAdapter myAdapter;
    private NewsEntity newsEntity;
    private Toolbar mToolbar;
    SharedPreferences pref;
    private XListView myListView;
    private ImageView mQuickReturnView; // 下拉快速显示item功能

    public CommentActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.comment_listview);
        Bundle bundle= new Bundle();
        bundle=getIntent().getExtras();
        newsEntity= (NewsEntity) bundle.getSerializable("NewsItem");
      //  commentDatabase = CommentDatabase.getInstance(CommentActivity.this);  //初始化数据库实例
        initView();
        BaseHttpClient.getInsence().getCommentBySnAndSid(newsEntity.getSN(), newsEntity.getSid() + "", responseHandlerInterface);
       // initCommentList();
        //读取设置文件的值
        pref = getSharedPreferences("org.zreo.cnbetareader_preferences", Context.MODE_PRIVATE);
        setThemeColor(pref.getInt("theme", 0));    //设置文件里主题的值
    }
    /**
     * 更改主题颜色
     */
    public void setThemeColor(int index) {
        switch (index) {
            case 0:  //蓝色（默认）
                mToolbar.setBackgroundColor(getResources().getColor(R.color.mainColor));  //ActionBar颜色
                break;
            case 1:  //棕色
                mToolbar.setBackgroundColor(getResources().getColor(R.color.brown));
                break;
            case 2:  //橙色
                mToolbar.setBackgroundColor(getResources().getColor(R.color.orange));
                break;
            case 3:  //紫色
                mToolbar.setBackgroundColor(getResources().getColor(R.color.purple));
                break;
            case 4:  //绿色
                mToolbar.setBackgroundColor(getResources().getColor(R.color.green));
                break;
            default:  //默认
                mToolbar.setBackgroundColor(getResources().getColor(R.color.mainColor));
                break;
        }
    }
    private void loadMoreData() {
        String[] FName = {"东"};
        String userName = "东方用户";
        String textComment = "200块都不给我";
        String sText = "支持:";
        String aText = "反对:";
        int supportNum = 10;
        int againstNum = 50;
        ArrayList<CommentItemEntity> resultList = new ArrayList<CommentItemEntity>();
        for (int i = 0; i < 10; i++) {

            CommentItemEntity cnComments = new CommentItemEntity();
            cnComments.setHost_name(FName[0]);
            //cnComments.setResponseText("");
            // cnComments.setLayout("");
            cnComments.setScore(supportNum);
            cnComments.setReason(againstNum);
            cnComments.setIcon(R.drawable.circle_btn);
            cnComments.setName(i + userName);
            cnComments.setComment(textComment);
            cnComments.setCommentMenu(R.drawable.more_grey);
            cnComments.setSupport(sText);
            cnComments.setAgainst(aText);
            resultList.add(cnComments);
        }
        myAdapter.AddData(resultList);
    }
    /**
     * 初始化布局
     */
    private void initView() {
        myListView = (XListView) findViewById(R.id.xListView);
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
     * 初始化评论列表
     */
    private void initCommentList() {
//        String FName = commentListEntity.getCmntlist();
//        String userName = commentItemEntity.getName();
//        String textComment = commentItemEntity.getComment();
//        String sText = "支持:";
//        String aText = "反对:";
//        int supportNum = commentItemEntity.getScore();
//        int againstNum = commentItemEntity.getReason();
//        ArrayList<CommentItemEntity> resultList = new ArrayList<CommentItemEntity>();
//        for (int i = 0; i < 10; i++) {
//
//            CommentItemEntity cnComments = new CommentItemEntity();
//            cnComments.setHost_name(FName);
//            cnComments.setScore(supportNum);
//            cnComments.setReason(againstNum);
//            cnComments.setIcon(R.drawable.circle_btn);
//            cnComments.setName(userName);
//            cnComments.setComment(textComment);
//            cnComments.setCommentMenu(R.drawable.more_grey);
//            cnComments.setSupport(sText);
//            cnComments.setAgainst(aText);
//            resultList.add(cnComments);
//        }
//        myAdapter.AddData(resultList);
    }

    @Override
    public void onRefresh() {
        BaseHttpClient.getInsence().getCommentBySnAndSid(newsEntity.getSN(), newsEntity.getSid() + "", responseHandlerInterface);
           }

       private ResponseHandlerInterface responseHandlerInterface = new HttpDateModel<CommentListEntity>(new TypeToken<ResponseEntity<CommentListEntity>>() {
          }) {

              @Override
               protected void onSuccess(CommentListEntity result) {
//                       ArrayList<CommentItemEntity> cmntlist = result.getCmntlist();
                  ArrayList<CommentItemEntity> cmntlist = result.getCmntlist();
                  HashMap<String, CommentItemEntity> cmntstore = result.getCmntstore();
                  for (CommentItemEntity item : cmntlist) {
                      StringBuilder sb = new StringBuilder();
                      item.copy(cmntstore.get(item.getTid()));
                      CommentItemEntity parent = cmntstore.get(item.getPid());
                      while (parent != null) {
                          sb.append("//@");
                          sb.append(parent.getName());
                          sb.append(": [");
                          sb.append(parent.getHost_name());
                          sb.append("] ");
                          sb.append(parent.getComment());
                          parent = cmntstore.get(parent.getPid());
                      }
                      item.setRefContent(sb.toString());
                  }
                  ArrayList<CommentItemEntity> hotcmntlist = result.getHotlist();
                  for (CommentItemEntity item : hotcmntlist) {
                      StringBuilder sb = new StringBuilder();
                      item.copy(cmntstore.get(item.getTid()));
                      CommentItemEntity parent = cmntstore.get(item.getPid());
                      while (parent != null) {
                          sb.append("//@");
                          sb.append(parent.getName());
                          sb.append(": [");
                          sb.append(parent.getHost_name());
                          sb.append("] ");
                          sb.append(parent.getComment());
                          parent = cmntstore.get(parent.getPid());
                      }
                      item.setRefContent(sb.toString());
                  }
//                           String FName = cmntlist.get(0).getHost_name();
//                           String userName = cmntlist.getName();
//                           String textComment = cmntlist.getComment();
//                           String sText = "支持:";
//                           String aText = "反对:";
//                           int supportNum = cmntlist.get();
//                           int againstNum = cmntlist.getReason();
                      Toast.makeText(CommentActivity.this,cmntlist.size()+"", Toast.LENGTH_LONG).show();
                          //System.out.println("cmntlist"+cmntlist.size());
//                           for (int i = 0 ; i < cmntlist.size(); i++){
//                             //  map.put(cmntlist.get(i).getSid(), cmntlist.get(i));
//                               CommentItemEntity cnComments = new CommentItemEntity();
//                               cnComments.setHost_name(FName);
//                               cnComments.setScore(supportNum);
//                               cnComments.setReason(againstNum);
//                               cnComments.setIcon(R.drawable.circle_btn);
//                               cnComments.setName(userName);
//                               cnComments.setComment(textComment);
//                               cnComments.setCommentMenu(R.drawable.more_grey);
//                               cnComments.setSupport(sText);
//                               cnComments.setAgainst(aText);
//                               cmntlist.add(cnComments);
//                           }
//                           myAdapter.AddData(cmntlist);
//                       HashMap<String, CommentItemEntity> cmntstore = result.getCmntstore();
                          // System.out.println("cmntstore"+cmntstore.size());
                           Toast.makeText(CommentActivity.this,cmntstore.size()+"", Toast.LENGTH_LONG).show();
                  }

                       @Override
               protected void onError() {
                           Toast.makeText(CommentActivity.this,"error", Toast.LENGTH_LONG).show();

                           }

                       @Override
               protected void onFailure() {
                           Toast.makeText(CommentActivity.this,"failure", Toast.LENGTH_LONG).show();
                           }

                    };
    @Override
    public void onLoadMore() {
         loadMoreData();
        // initCommentList();
        myListView.stopRefresh();
        myListView.stopLoadMore();
    }
    /**
     * 悬浮按钮点击响应
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        Intent intent = new Intent(CommentActivity.this, PostCommentActivity.class);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    String contentData = data.getStringExtra("content");
                    String userName = "南方用户";
                    //String textComment = "100块都不给我";
                    String[] FName = {"南"};
                    String sText = "支持:";
                    String aText = "反对:";
                    int supportNum = 100;
                    int againstNum = 50;
                    ArrayList<CommentItemEntity> resultList = new ArrayList<CommentItemEntity>();
                    CommentItemEntity cnComments = new CommentItemEntity();
                    cnComments.setHost_name(FName[0]);
                    //cnComments.setResponseText("");
                    //  cnComments.setLayout("");
                    cnComments.setScore(supportNum);
                    cnComments.setReason(againstNum);
                    cnComments.setIcon(R.drawable.circle_btn);
                    cnComments.setName(userName);
                    cnComments.setComment(contentData);
                    cnComments.setCommentMenu(R.drawable.more_grey);
                    cnComments.setSupport(sText);
                    cnComments.setAgainst(aText);
                    resultList.add(cnComments);
                    myAdapter.AddData(resultList);
                    myListView.setSelection(myAdapter.getCount());// 将myListView定位到刚刚评论的一行
                }
                break;
            default:
                break;
        }
    }
}
