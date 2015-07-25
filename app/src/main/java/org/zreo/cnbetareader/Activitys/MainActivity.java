package org.zreo.cnbetareader.Activitys;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.DrawerLayout.DrawerListener;
import android.support.v7.app.AppCompatActivity;
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

public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private ListView lv;
<<<<<<< HEAD
=======
    /*定义一个动态数组
      */
    // private List<Map<String, Object>> listItems;
>>>>>>> 495dab8a172421b3f4585734ff1458a1e8954d47
    private List<News> listItem = new ArrayList<News>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
<<<<<<< HEAD
        initListItem(); //初始化新闻列表
=======
        initListItem();
>>>>>>> 495dab8a172421b3f4585734ff1458a1e8954d47
        initView();  //初始化布局
        initEvents();  //滑动事件
    }

    /*
     *  初始化布局
     */
    private void initView() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.id_drawerLayout);
        lv = (ListView) findViewById(R.id.news_title_list_view);
        //得到一个NewsTitleAdapter对象
        //NewsTitleAdapter mAdapter = new NewsTitleAdapter(this, R.layout.news_title_item, listItem);
        NewsTitleAdapter mAdapter = new NewsTitleAdapter(this, R.layout.news_title_item, listItem);
        lv.setAdapter(mAdapter);//为ListView绑定Adapter
        /**为ListView添加点击事件*/
       lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
<<<<<<< HEAD
                Toast.makeText(MainActivity.this, "你点击了 " + position, Toast.LENGTH_SHORT).show();
=======
                Toast.makeText(MainActivity.this, "你点击了" + position, Toast.LENGTH_SHORT).show();
>>>>>>> 495dab8a172421b3f4585734ff1458a1e8954d47
            }
        });
    }
    /*
    初始化新闻列表
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
            news.setCommentNumber(i);
            news.setReaderNumber(100 + i);
            listItem.add(news);
        }
    }

    /*
     *滑动事件
     */
    private void initEvents(){
        //DrawerListener默认只能从边界划出菜单
        mDrawerLayout.setDrawerListener(new DrawerListener() {
            @Override
            public void onDrawerStateChanged(int newState) {
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(View drawerView) {
            }

            @Override
            public void onDrawerClosed(View drawerView) {
            }
        });
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


