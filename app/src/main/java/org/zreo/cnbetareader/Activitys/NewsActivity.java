package org.zreo.cnbetareader.Activitys;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.Toast;

import org.zreo.cnbetareader.R;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;


public class NewsActivity extends ActionBarActivity {
    private Toolbar mToolbar;
    ImageButton imageButton;
    WebView webView;
    WebSettings webSettings;
    ButtonOnclick buttonOnclick;
    private String[] textsize1 = new String[]{"大", "中", "小"};

    private class ButtonOnclick implements DialogInterface.OnClickListener {
        private int index;

        public ButtonOnclick(int index) {
            this.index = index;
        }


        public void onClick(DialogInterface dialogInterface, int whichButton) {
            //whichButton表示单击的按钮索引，所有列表项的索引都是大于等于0的，然而按钮的索引都是<0的！
            if (whichButton >= 0) {
                index = whichButton; //单击的是列表项
                // dialog.cancel();
            } else {
                if (whichButton == DialogInterface.BUTTON_POSITIVE) {
                    if (index == 0) {
                        webSettings.setTextSize(WebSettings.TextSize.LARGER);
                    } else if (index == 1) {
                        webSettings.setTextSize(WebSettings.TextSize.NORMAL);
                    } else {
                        webSettings.setTextSize(WebSettings.TextSize.SMALLER);
                    }
                } else if (whichButton == DialogInterface.BUTTON_NEGATIVE) {

                }

            }
        }

    }
    

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        ShareSDK.initSDK(this);
        buttonOnclick=new ButtonOnclick(1);
        webView = (WebView) findViewById(R.id.wv);
        webSettings = webView.getSettings();
        imageButton = (ImageButton) findViewById(R.id.imageBtn);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);   //ToolBar布局
        mToolbar.setTitle("新闻详细页");   // 标题的文字需在setSupportActionBar之前，不然会无效
        mToolbar.setTitleTextColor(Color.WHITE);  //设置ToolBar字体颜色为白色
        setSupportActionBar(mToolbar);  //将ToolBar设置为ActionBAr
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);  //在ToolBar左边，即当前标题前添加图标
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent5 = new Intent(NewsActivity.this, CommentActivity.class);
                startActivity(intent5);
            }
        });
        webView.loadUrl("http://m.cnbeta.com");
        webView.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlloading(WebView webView, String url) {
                webView.loadUrl(url);
                return false;
            }

            public boolean shouldOverrideKeyEvent(WebView view, KeyEvent event) {
                return super.shouldOverrideKeyEvent(view, event);
            }

        });
        webView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {  //表示按返回键时的操作


                        webView.goBack();   //后退

                        //webview.goForward();//前进
                        return true;    //已处理
                    }
                }
                return false;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_news, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.refresh:
                Toast.makeText(getApplicationContext(), "刷新中", Toast.LENGTH_SHORT).show();
                webView.reload();
                return true;
            case R.id.qxsc:
                Toast.makeText(getApplicationContext(), "已取消收藏", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.font:
                Toast.makeText(getApplicationContext(), "已改变字体", Toast.LENGTH_SHORT).show();
                new AlertDialog.Builder(this).setTitle("请选择字体的大小")
                        .setSingleChoiceItems(textsize1, 1,buttonOnclick).
                                setPositiveButton("确定",buttonOnclick).setNegativeButton("取消",buttonOnclick).create().show();
                return true;
            case R.id.browser:
                Toast.makeText(getApplicationContext(), "已打开浏览器", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                intent.setAction("android.intent.action.VIEW");
                Uri content_url = Uri.parse("http://m.cnbeta.com");
                intent.setData(content_url);
                startActivity(intent);
                return true;
            case R.id.action_share:
                showShare();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private void showShare() {
        ShareSDK.initSDK(this);
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

// 分享时Notification的图标和文字  2.5.9以后的版本不调用此方法
        //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle(getString(R.string.share));
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
        oks.setTitleUrl("http://sharesdk.cn");
        // text是分享文本，所有平台都需要这个字段
        oks.setText("我是分享文本");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("http://sharesdk.cn");
        // comment是我对这条分享的评论，仅在人人网和QQ空间++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++使用
        oks.setComment("我是测试评论文本");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(getString(R.string.app_name));
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("http://sharesdk.cn");

// 启动分享GUI
        oks.show(this);
    }


}
