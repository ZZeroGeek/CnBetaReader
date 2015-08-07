package org.zreo.cnbetareader.Fragments;




import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.zreo.cnbetareader.Adapters.Information_theme_Adapter;
import org.zreo.cnbetareader.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ArrayFragment extends Fragment implements View.OnClickListener{
    private ViewPager viewpager;
    // private PagerAdapter iAdapter;
    private List<View> views;
    private View view1;//关注布局视图
    private View view2;//可关注的布局视图
    private Button ibtn_theme;
    private TextView itv_Tab1;
    private TextView itv_Tab2;
    private int currentIndex = 0;//当前标签页
    View view;
    private Information_theme_Adapter iAdapter1;
    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.information_theme,container,false);
        initView();//初始化
        initColor();
        iAdapter1=new Information_theme_Adapter(views);
        //找到viewpager控件
        viewpager = (ViewPager)view.findViewById(R.id.viewpager);
        viewpager.setCurrentItem(0);//默认页面
        viewpager.setAdapter(iAdapter1);//绑定适配器
        itv_Tab1.setTextColor(Color.WHITE);
        //设置页面改变事件
        viewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                initColor();
                switch (position) {
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
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }
            @Override
            public void onPageScrollStateChanged(int arg0) {
            }
        });
        return view;
    }
    //初始化文本颜色
    public void initColor() {
        itv_Tab1.setTextColor(Color.DKGRAY);
        itv_Tab2.setTextColor(Color.DKGRAY);
    }
    //初始主要显示的内容，并且加入组件中
    public void initView() {
        //初始化关注页和已关注页
        itv_Tab1 = (TextView) view.findViewById(R.id.itv_Tab1);
        itv_Tab2 = (TextView) view.findViewById(R.id.itv_Tab2);
        //设置监听事件
        itv_Tab1.setOnClickListener(this);
        itv_Tab2.setOnClickListener(this);

        views = new ArrayList<View>();
        LayoutInflater f = getActivity().getLayoutInflater();
        view1 = f.inflate(R.layout.itv_tab1, null);
        view2 = f.inflate(R.layout.itv_tab2, null);
        //添加两个视图
        views.add(view1);
        views.add(view2);
        //给关注按钮添加事件
        ibtn_theme=(Button)view2.findViewById(R.id. ibtn_theme);
        ibtn_theme.setOnClickListener(this);
    }
    public void onClick(View v) {
        int FOCUS_LEFT = 17;
        int FOCUS_RIGHT = 66;
        switch (v.getId()) {
            case R.id.itv_Tab1:
                viewpager.arrowScroll(FOCUS_RIGHT);
                if (currentIndex == 0) {
                } else if (currentIndex == 1) {
                    viewpager.arrowScroll(FOCUS_LEFT);
                } else if (currentIndex == 2) {
                    viewpager.arrowScroll(FOCUS_LEFT);
                }
                break;
            case R.id.itv_Tab2:
                if (currentIndex == 0) {
                    viewpager.arrowScroll(FOCUS_RIGHT);
                } else if (currentIndex == 1) {
                } else if (currentIndex == 2) {
                    viewpager.arrowScroll(FOCUS_LEFT);
                }
                break;
            case R.id.ibtn_theme:
                Toast.makeText(getActivity(),"添加新闻到关注页面！",Toast.LENGTH_SHORT).show();
            default:
                break;
        }
    }
}


