package org.zreo.cnbetareader.Activitys;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import org.zreo.cnbetareader.Adapters.NewsTitleAdapter;
import org.zreo.cnbetareader.Model.News;
import org.zreo.cnbetareader.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends ActionBarActivity {

    private DrawerLayout mDrawerLayout;
    private ListView lv;
    private Toolbar mToolbar;
    private ActionBarDrawerToggle mDrawerToggle;
    /**定义一个动态数组，保存新闻信息*/
    private List<News> listItem = new ArrayList<News>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initListItem(); //初始化新闻列表
        initView();  //初始化布局
    }

     /**
      *  初始化布局
      */
    private void initView() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);  //右滑菜单布局
        mToolbar = (Toolbar) findViewById(R.id.toolbar);   //ToolBar布局
        mToolbar.setTitle("全部资讯");   // 标题的文字需在setSupportActionBar之前，不然会无效
        mToolbar.setTitleTextColor(Color.WHITE);  //设置ToolBar字体颜色为白色
        setSupportActionBar(mToolbar);  //将ToolBar设置为ActionBAr
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);  //在ToolBar左边，即当前标题前添加图标
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.drawer_open,
                R.string.drawer_close);
        mDrawerToggle.syncState();   //该方法会自动和actionBar关联, 将开关的图片显示在了action上
        mDrawerLayout.setDrawerListener(mDrawerToggle);  //设置drawer的开关监听

        /**显示新闻标题的ListView*/
        lv = (ListView) findViewById(R.id.news_title_list_view);
        /**为ListView创建自定义适配器*/
        NewsTitleAdapter mAdapter = new NewsTitleAdapter(this, R.layout.news_title_item, listItem);
        /**为ListView绑定Adapter*/
        lv.setAdapter(mAdapter);
        /**为ListView添加点击事件*/
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this, "你点击了 " + position, Toast.LENGTH_SHORT).show();

            }
        });
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
       for(int i = 0; i < 30; i++){
            News news = new News();
            news.setNewsTitle(i + "  " + title);
            news.setNewsContent(summary);
            news.setPublishTime("2015-07-24 10:30:38");
            news.setImageId(R.mipmap.news_picture);
            news.setCommentNumber(i * 20);
            news.setReaderNumber(i * 100);
            listItem.add(news);
        }
    }


    /* //手指按下的点为(startX, startY)手指离开屏幕的点为(endX, endY)
    float startX = 0;
    float endX = 0;
    float startY = 0;
    float endY = 0;
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                startX = ev.getX();
                startY = ev.getY();
                break;
            case MotionEvent.ACTION_UP:
                endX = ev.getX();
                endY = ev.getY();

                float sensitivity = 10;

                // 右滑打开左边菜单
                if (endX - startX > endY - startY && endX - startX > sensitivity) {
                    if (!mDrawerLayout.isDrawerOpen(Gravity.LEFT)) {
                        mDrawerLayout.openDrawer(Gravity.LEFT);
                    }
                }

	            //左滑关闭左边菜单
	            if (startX - endX > startY - endY && startX - endX > sensitivity) {
	                if (mDrawerLayout.isDrawerOpen(Gravity.LEFT)) {
	                    mDrawerLayout.closeDrawer(Gravity.LEFT);
	                }
	            }
                break;
        }
        return super.dispatchTouchEvent(ev); //return false时会覆盖DrawerListener监听器
    }*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.offline_download) {
            Toast.makeText(this, "离线下载", Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}


