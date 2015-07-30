package org.zreo.cnbetareader.Activitys;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.os.Bundle;
import android.app.Activity;

import android.graphics.Color;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;


import android.view.LayoutInflater;

import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;


import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import org.zreo.cnbetareader.R;

/**
 * Created by Admin on 2015/7/25.
 */
public class Information_ThemeActivity extends Activity  implements OnClickListener{
  //  ViewPager viewPager;
   // int position;
   // ViewPager ipager = null;
   // PagerTabStrip itabStrip = null;
   // ArrayList<View> viewContainter = new ArrayList<View>();
   // ArrayList<String> titleContainer = new ArrayList<String>();
   // public String TAG = "tag";
    private ViewPager viewpager;
    private PagerAdapter iAdapter;  //ViewPager的适配器
    private ArrayList<View> views;
    private View view1;
    private View view2;


    private TextView itv_Tab1;
    private TextView itv_Tab2;
    private int currentIndex = 0;  //当前标签页
    String summary = "7月24日消息，昨日有网友在国内某知名论坛发布疑似Win10应用商店中国定制版的系统界面图片，" +
            "一时间引发诸多热议。这名网友发帖称是从内部人士手里拿到了Win10特别版的系统映像，安装后发现这竟然" +
            "是Win10针对中国地区的定制版本。系统中除内置了很多微软旗下的服务外，还有一些本地化的功能。" +
            "据此他猜测，这极有可能就是专门提供给中国盗版用户免费使用的定制版本。";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.information_theme);
        init();
        viewpager = (ViewPager) findViewById(R.id.viewpager);
        viewpager.setCurrentItem(0);  //默认页面
        itv_Tab1.setTextColor(Color.BLUE);
        itv_Tab2.setTextColor(Color.BLUE);
        viewpager.setAdapter(iAdapter);

        List<Map<String,Object>>ilistItems=new ArrayList<Map<String,Object>>();


            Map<String,Object>ilistItem=new HashMap<String,Object>();
            ilistItem.put("i_imgView",R.drawable.rating_good);

            ilistItem.put("isummary","7月24日消息，昨日有网友在国内某知名论坛发布疑似Win10应用商店中国定制版的系统界面图片，\" +\n" +
                    "            \"一时间引发诸多热议。这名网友发帖称是从内部人士手里拿到了Win10特别版的系统映像，安装后发现这竟然\""
                            );
            ilistItems.add(ilistItem);
        SimpleAdapter isimpleAdapter=new SimpleAdapter(this,ilistItems,R.layout.i_image_btn,
                new String[]{"i_imgView","isummary","i_btn"},
                new int[]{R.id.i_imgView,R.id.isummary,R.id.i_btn});
        ListView ilv2_content=(ListView)findViewById(R.id.ilv2_content);
        ilv2_content.setAdapter(isimpleAdapter);

       // ListView ilv2_content=(ListView)findViewById(R.id.ilv2_content);

        viewpager.setOnPageChangeListener(new OnPageChangeListener()
        {
            @Override
            public void onPageSelected(int position)
            {
                initColor();
                switch (position)
                {
                    case 0:
                        itv_Tab1.setTextColor(Color.WHITE);
                        break;
                    case 1:
                        itv_Tab2.setTextColor(Color.WHITE);
                        break;
                }
                currentIndex = position;
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2)
            {
            }

            @Override
            public void onPageScrollStateChanged(int arg0)
            {
            }
        });
    }

    //初始化TestView文本颜色

    public void initColor() {
        itv_Tab1.setTextColor(Color.BLUE);
        itv_Tab2.setTextColor(Color.BLUE);

    }


    //初始主要的显示内容。并且加入组件中
    public void init(){
        itv_Tab1 = (TextView) findViewById(R.id. itv_Tab1);
        itv_Tab2 = (TextView) findViewById(R.id. itv_Tab2);

        itv_Tab1.setOnClickListener((OnClickListener) this);
        itv_Tab2.setOnClickListener((OnClickListener) this);


        views = new ArrayList<View>();
        LayoutInflater f = getLayoutInflater();
        view1 = f.inflate(R.layout. itv_tab1, null);
        view2 = f.inflate(R.layout.itv_tab2, null);

        views.add(view1);
        views.add(view2);



        iAdapter = new PagerAdapter()
        {
            @Override
            public void destroyItem(ViewGroup container, int position, Object object)
            {
                container.removeView(views.get(position));
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position)
            {
                View view = views.get(position);
                container.addView(view);
                return view;
            }

            @Override
            public boolean isViewFromObject(View arg0, Object arg1)
            {
                return arg0 == arg1;
            }

            @Override
            public int getCount()
            {
                return views.size();
            }
        };



    }

    public void onClick(View v) {
        //调用arrowScroll方法用参数1或者17就可以实现向左翻页；参数2或66就可以实现向右翻页。
        //当你的UI中有EditText这种获得focus的widget时，则必须用17和66，否则要报错。
        int FOCUS_LEFT = 17;
        int FOCUS_RIGHT = 66;
        switch (v.getId()) {
            case R.id. itv_Tab1:
                if (currentIndex == 0) {	//不跳转
                } else if (currentIndex == 1) {
                    viewpager.arrowScroll(FOCUS_LEFT);
                } else if (currentIndex == 2) {
                    viewpager.arrowScroll(FOCUS_LEFT);
                    viewpager.arrowScroll(FOCUS_LEFT);
                }
                break;
            case R.id. itv_Tab2:
                if (currentIndex == 0) {
                    viewpager.arrowScroll(FOCUS_RIGHT);
                } else if (currentIndex == 1) {		//不跳转
                } else if (currentIndex == 2) {
                    viewpager.arrowScroll(FOCUS_LEFT);
                }
                break;
            default:
                break;
        }

    }



}
