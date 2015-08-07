package org.zreo.cnbetareader.Adapters;
import android.app.Fragment;
import android.app.FragmentManager;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import java.util.List;

/**
 * Created by Admin on 2015/7/30.
 */
public class Information_theme_Adapter extends PagerAdapter {
    private List<Fragment>fragments;
    private List<View>views;
    public Information_theme_Adapter(FragmentManager fm,List<Fragment>fragments) {
        super();
        this.fragments=fragments;
    }
    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(views.get(position));}

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
      container.addView(views.get(position));
        return  views.get(position);
    }
}
