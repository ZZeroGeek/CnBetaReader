package org.zreo.cnbetareader.Activitys;

import android.app.TabActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TabHost;

import org.zreo.cnbetareader.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Admin on 2015/7/25.
 */
public class Information_ThemeActivity extends TabActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.information_theme);
        //设置标签的名称
        TabHost itabHost=(TabHost)findViewById(android.R.id.tabhost);
        TabHost.TabSpec itab1=itabHost.newTabSpec("itab1");
        itab1.setIndicator("已关注");
        itab1.setContent(R.id.tab_1);
        itabHost.addTab(itab1);

        TabHost.TabSpec itab2=itabHost.newTabSpec("itab2");
        itab2.setIndicator("可关注");
         itab2.setContent(R.id.tab_2);
        itabHost.addTab(itab2);

        ListView information_lv1=(ListView)findViewById(R.id.information_lv1);
        //ListView information_lv2=(ListView)findViewById(R.id.information_lv2);

        List<Map<String,Object>> idata=new ArrayList<Map<String,Object>>();
        Map<String,Object>map1=new HashMap<String,Object>();
        Map<String,Object>map2=new HashMap<String,Object>();

        map2.put("photo",R.drawable.menubtn);
        map2.put("name","测试ListView");
        idata.add(map2);

        information_lv1.setAdapter(new SimpleAdapter(this,idata,R.layout.information_theme,new String[]{"photo","name"}
                ,new int[]{R.id.information_imageview,R.id.information_tv}));





    }
}
