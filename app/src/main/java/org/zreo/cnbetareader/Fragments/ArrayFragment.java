package org.zreo.cnbetareader.Fragments;



import android.app.FragmentManager;
import android.os.Bundle;


import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

;
import org.zreo.cnbetareader.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ArrayFragment extends Fragment {

    int iNum;
    public static ArrayFragment newInstance(int num){
        ArrayFragment array=new ArrayFragment();
        Bundle args=new Bundle(2);
        args.putInt("num", num);
        array.setArguments(args);
        return array;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragmentView=inflater.inflate(R.layout.information_theme,container,false);
        ViewPager pager=(ViewPager)fragmentView.findViewById(R.id.viewpager);
        final int parent_position=getArguments().getInt("position");

        pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        pager.setAdapter(new FragmentStatePagerAdapter(getChildFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return new Fragment(){
                    @Nullable
                    @Override
                    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
                        TextView fragmentView=new TextView(getActivity());
                        fragmentView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                        fragmentView.setGravity(Gravity.CENTER);
                        fragmentView.setText("Pagexxxx");
                        return fragmentView;
                    }
                };
            }

            @Override
            public int getCount() {
                return 2;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return "Page"+parent_position+" - "+position;
            }
        });
        return fragmentView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
