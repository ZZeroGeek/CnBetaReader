package org.zreo.cnbetareader.Activitys;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.KeyEvent;
import android.widget.Toast;

import org.zreo.cnbetareader.Fragments.Comment_Top10Fragment;
import org.zreo.cnbetareader.Fragments.Comment_hot_Fragment;
import org.zreo.cnbetareader.Fragments.DrawerLayoutFragment;
import org.zreo.cnbetareader.Fragments.NewsTitleFragment;
import org.zreo.cnbetareader.R;

/**
 * Created by Admin on 2015/7/28.
 * 功能：实现右滑菜单和Toolbar与右滑菜单的关联, 以及管理不同页面的显示
 */
public class MainActivity extends AppCompatActivity implements DrawerLayoutFragment.TabSelectionListener{

    private FragmentManager fragmentManager;  //用于对Fragment进行管理
    private NewsTitleFragment mNewsTitleFragment; //新闻标题界面
    private Comment_hot_Fragment mCommentHotFragment; //精彩评论界面
    private Comment_Top10Fragment mCommentTop10Fragment; //本月Top10界面
    private int currentIndex = 1;  //当前标签页, 默认显示全部资讯页面，即为1
    private DrawerLayout mDrawerLayout;
    private Toolbar mToolbar;
    private ActionBarDrawerToggle mDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragmentManager = getFragmentManager();
        initView();  //初始化右滑菜单布局和Toolbar
        setTabSelection(currentIndex);  //显示默认标签页
    }

    /**
     *  初始化右滑菜单布局和Toolbar
     */
    private void initView(){
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);  //右滑菜单布局
        mToolbar = (Toolbar) findViewById(R.id.toolbar);   //ToolBar布局
        setToolBarTitle(currentIndex);
        mToolbar.setTitleTextColor(Color.WHITE);  //设置ToolBar字体颜色为白色
        setSupportActionBar(mToolbar);  //将ToolBar设置为ActionBAr
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);  //在ToolBar左边，即当前标题前添加图标
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.drawer_open,
                R.string.drawer_close);
        mDrawerToggle.syncState();   //该方法会自动和actionBar关联, 将开关的图片显示在了action上
        mDrawerLayout.setDrawerListener(mDrawerToggle);  //设置drawer的开关监听
    }

    /**
     *  设置当前Fragment界面标题
     */

    private void setToolBarTitle(int index){
        if(index == 1){
            mToolbar.setTitle("全部资讯");
        }else if(index == 2){
            mToolbar.setTitle("精彩评论");
        }else if(index == 3){
            mToolbar.setTitle("本月Top10");
        }else if(index == 4){
            mToolbar.setTitle("收藏");
        }else if(index == 5){
            mToolbar.setTitle("资讯主题");
        }
    }

    //根据传入的index参数来设置选中的tab页
    private void setTabSelection(int index){
        FragmentTransaction transaction = fragmentManager.beginTransaction();  // 开启一个Fragment事务
        hideFragments(transaction);  // 先隐藏掉所有的Fragment，以防止有多个Fragment显示在界面上的情况
        switch (index) {
            case 1:    //全部资讯界面
                if (mNewsTitleFragment == null) {
                    // 如果mNewsTitleFragment为空，则创建一个并添加到界面上
                    mNewsTitleFragment = new NewsTitleFragment();
                    transaction.add(R.id.fragment_content, mNewsTitleFragment);
                } else{
                    // 如果mNewsTitleFragment不为空，则直接将它显示出来
                    transaction.show(mNewsTitleFragment);
                }
                break;

            case 2:   //精彩评论界面
                if (mCommentHotFragment == null) {
                    // 如果mCommentHotFragment为空，则创建一个并添加到界面上
                    mCommentHotFragment = new Comment_hot_Fragment();
                    transaction.add(R.id.fragment_content, mCommentHotFragment);
                } else{
                    // 如果mCommentHotFragment不为空，则直接将它显示出来
                    transaction.show(mCommentHotFragment);
                }
                break;

            case 3:  //本月Top10界面
                if (mCommentTop10Fragment == null) {
                    // 如果mCommentTop10Fragment为空，则创建一个并添加到界面上
                    mCommentTop10Fragment = new Comment_Top10Fragment();
                    transaction.add(R.id.fragment_content, mCommentTop10Fragment);
                } else{
                    // 如果mCommentTop10Fragment不为空，则直接将它显示出来
                    transaction.show(mCommentTop10Fragment);
                }
                break;
            case 4:   //收藏界面
                break;

            case 5:   //资讯主题界面
                break;
            default:
                break;
        }
        transaction.commit();
    }

    /**将所有的Fragment都置为隐藏状态*/
    private void hideFragments(FragmentTransaction transaction){
        if (mNewsTitleFragment != null){
            transaction.hide(mNewsTitleFragment);
        }
        if (mCommentTop10Fragment != null){
            transaction.hide(mCommentTop10Fragment);
        }
        if (mCommentHotFragment != null){
            transaction.hide(mCommentHotFragment);
        }
    }

    /**实现接口，接收DrawerLayoutFragment返回的数据*/
    @Override
    public void selection(int index) {
        setTabSelection(index);
        setToolBarTitle(index);
        if (mDrawerLayout.isDrawerOpen(Gravity.LEFT)) {
            mDrawerLayout.closeDrawer(Gravity.LEFT);  //点击后关闭后滑菜单
        }
    }

    private long exitTime = 0;
    /**实现再按一次后退键退出应用程序*/
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){
            if((System.currentTimeMillis()-exitTime) > 2000){
                Toast.makeText(this, "再按一次后退键退出程序", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}

