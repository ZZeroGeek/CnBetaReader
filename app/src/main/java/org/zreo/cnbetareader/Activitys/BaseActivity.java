package org.zreo.cnbetareader.Activitys;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ViewConfiguration;
import org.zreo.cnbetareader.R;
import java.lang.reflect.Field;

/**
 * Created by Admin on 2015/7/28.
 * 功能：实现右滑菜单和Toolbar与右滑菜单的关联
 */
public class BaseActivity extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;
    private Toolbar mToolbar;
    private ActionBarDrawerToggle mDrawerToggle;
    private String activityName;

    protected void onCreate(Bundle savedInstanceState, int layoutId, String activityName) {
        super.onCreate(savedInstanceState);
        setContentView(layoutId);
        this.activityName = activityName;
        initView();  //初始化布局
    }

    /**
     *  初始化右滑菜单布局和Toolbar
     */
    private void initView(){
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);  //右滑菜单布局
        mToolbar = (Toolbar) findViewById(R.id.toolbar);   //ToolBar布局
        mToolbar.setTitle(activityName);   // 标题的文字需在setSupportActionBar之前，不然会无效
        mToolbar.setTitleTextColor(Color.WHITE);  //设置ToolBar字体颜色为白色
        setSupportActionBar(mToolbar);  //将ToolBar设置为ActionBAr
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);  //在ToolBar左边，即当前标题前添加图标
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.drawer_open,
                R.string.drawer_close);
        mDrawerToggle.syncState();   //该方法会自动和actionBar关联, 将开关的图片显示在了action上
        mDrawerLayout.setDrawerListener(mDrawerToggle);  //设置drawer的开关监听
    }
}
