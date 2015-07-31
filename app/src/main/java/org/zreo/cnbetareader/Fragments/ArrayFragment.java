package org.zreo.cnbetareader.Fragments;


import android.app.Fragment;
import android.os.Bundle;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import org.zreo.cnbetareader.Model.CnInformation_Theme;
import org.zreo.cnbetareader.Model.CnInformation_theme_mayattention;
import org.zreo.cnbetareader.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ArrayFragment extends Fragment {

  int iNum;
    public static ArrayFragment newInstance(int num){
        ArrayFragment array=new ArrayFragment();
        Bundle args=new Bundle();
        args.putInt("num", num);
        array.setArguments(args);
        return array;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        iNum=getArguments()!=null?getArguments().getInt("num"):1;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //加载每个fragment的显示View
        CnInformation_Theme cnInformation_theme=new CnInformation_Theme();
        CnInformation_theme_mayattention cnInformation_theme_mayattention=
                new CnInformation_theme_mayattention();
       View view=null;
        if(iNum==0){
            view=inflater.inflate(R.layout.itv_tab1,container,false);
            TextView itv_type=(TextView)view.findViewById(R.id.itv_type);
            itv_type.setText(cnInformation_theme.getFirstWord() );
            TextView itv_context1=(TextView)view.findViewById(R.id.itv_context1);
            itv_context1.setText(cnInformation_theme.getContent());
            TextView firstword_text1=(TextView)view.findViewById(R.id.firstword_text1);
            firstword_text1.setText(cnInformation_theme.getFirstWord());



        }
       else if(iNum==1){
            view=inflater.inflate(R.layout.itv_tab2,container,false);
            TextView tv_type1=(TextView)view.findViewById(R.id.tv_type1);
            tv_type1.setText(cnInformation_theme_mayattention.getThemetype());
            TextView itv_context2=(TextView)view.findViewById(R.id.itv_context2);
            itv_context2.setText(cnInformation_theme_mayattention.getContent());
            TextView firstword_text2=(TextView)view.findViewById(R.id.firstword_text2);
            firstword_text2.setText(cnInformation_theme_mayattention.getFirstword());



        }
        return view;
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
