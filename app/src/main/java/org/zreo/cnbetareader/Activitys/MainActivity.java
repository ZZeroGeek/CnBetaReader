package org.zreo.cnbetareader.Activitys;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.DrawerLayout.DrawerListener;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import org.zreo.cnbetareader.R;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();  //初始化布局
        initEvents();
    }

    /*
     *  初始化布局
     */
    private void initView() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.id_drawerLayout);


    }

    /*
     *
     */
    private void initEvents(){
        //DrawerListener默认只能从边界划出菜单
        mDrawerLayout.setDrawerListener(new DrawerListener(){
            @Override
            public void onDrawerStateChanged(int newState){
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

    //手指按下的点为(startX, startY)手指离开屏幕的点为(endX, endY)
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

                //float sensitivity = 5;

                // 右滑打开左边菜单
                if (endX - startX > endY - startY) {
                    if (!mDrawerLayout.isDrawerOpen(Gravity.LEFT)) {
                        mDrawerLayout.openDrawer(Gravity.LEFT);
                    }
                }

	            //左滑关闭左边菜单
	            if (startX - endX > startY - endY) {
	                if (mDrawerLayout.isDrawerOpen(Gravity.LEFT)) {
	                    mDrawerLayout.closeDrawer(Gravity.LEFT);
	                }
	            }
                break;
        }
        return super.dispatchTouchEvent(ev); //return false时会覆盖DrawerListener监听器
    }

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
