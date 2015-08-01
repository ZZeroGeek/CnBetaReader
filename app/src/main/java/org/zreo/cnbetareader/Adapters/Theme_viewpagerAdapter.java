package org.zreo.cnbetareader.Adapters;



import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.View;
import android.view.ViewGroup;


import org.zreo.cnbetareader.Fragments.ArrayFragment;

import java.util.List;


/**
 * Created by Admin on 2015/7/31.
 */
public  class Theme_viewpagerAdapter extends FragmentStatePagerAdapter {

  static final int NUN_ITEM=2;

    public Theme_viewpagerAdapter(FragmentManager fm) {
        super(fm);
    }


    @Override
    public android.support.v4.app.Fragment getItem(int i) {
        return null;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object instantiateItem(ViewGroup arg0, int arg1) {
        return super.instantiateItem(arg0,arg1);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return super.isViewFromObject(view, object);
    }
}
