package org.zreo.cnbetareader.Fragments;



import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.zreo.cnbetareader.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ArrayFragment extends Fragment {
    private ViewPager viewpager;
    private PagerAdapter mAdapter;
    private ArrayList<View> views;
    private View view1;
    private View view2;


    private TextView itv_Tab1;
    private TextView itv_Tab2;


    private int currentIndex = 0;


    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        View views=inflater.inflate(R.layout.information_theme,container,false);
        init();
        viewpager = (ViewPager)views. findViewById(R.id.viewpager);
        viewpager.setCurrentItem(0);
        itv_Tab1.setTextColor(Color.BLUE);
        viewpager.setAdapter(mAdapter);
        viewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                initColor();
                switch (position) {
                    case 0:
                        itv_Tab1.setTextColor(Color.BLUE);
                        break;
                    case 1:
                        itv_Tab2.setTextColor(Color.BLUE);
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
        return views;

    }





    public void initColor() {
        itv_Tab1.setTextColor(Color.BLACK);
        itv_Tab2.setTextColor(Color.BLACK);

    }


    public void init(){
        // textView1 = (TextView)view. findViewById(R.id.tv1);
        // textView2 = (TextView) findViewById(R.id.tv2);
        itv_Tab1.setOnClickListener((View.OnClickListener) this);
        itv_Tab2.setOnClickListener((View.OnClickListener) this);



        views = new ArrayList<View>();
        LayoutInflater f = getLayoutInflater();
        view1 = f.inflate(R.layout.itv_tab1, null);
        view2 = f.inflate(R.layout.itv_tab2, null);

        views.add(view1);
        views.add(view2);


        mAdapter = new PagerAdapter()
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

    private LayoutInflater getLayoutInflater() {
        return null;
    }

    public void onClick(View v) {

        int FOCUS_LEFT = 17;
        int FOCUS_RIGHT = 66;
        switch (v.getId()) {
            case R.id.itv_Tab1:
                if (currentIndex == 0) {
                } else if (currentIndex == 1) {
                    viewpager.arrowScroll(FOCUS_LEFT);
                } else if (currentIndex == 2) {
                    viewpager.arrowScroll(FOCUS_LEFT);
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
            default:
                break;
        }

    }



}


