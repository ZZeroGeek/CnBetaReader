package org.zreo.cnbetareader.Fragments;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import org.zreo.cnbetareader.Adapters.Fragment1_Adapter;
import org.zreo.cnbetareader.Model.CnInformation_Theme;
import org.zreo.cnbetareader.R;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class Tab_Fragment1 extends Fragment implements Tab_Fragment2.click {
    private View view;
    private ListView theme_listview;
    private List<CnInformation_Theme>CnInformation_ThemeList=new ArrayList <CnInformation_Theme>();
    Fragment1_Adapter fragment1_adapter;
    private CnInformation_Theme  cnInformation_theme;
    private View loadMoreView;     //加载更多布局
    private TextView loadMoreText;    //加载提示文本
    TextView hintText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_theme_listview, container, false); //获取布局
        theme_listview=(ListView)view.findViewById(R.id.theme_listview);//找到theme_listview控件
        initCollectNewsList();//初始化
        return view;
    }
    private void  initCollectNewsList(){
        initView();
        if (CnInformation_ThemeList.size() == 0) {      //如果数据库没数据
            hintText.setVisibility(View.VISIBLE);  //显示没有关注主题的提示
            theme_listview.setVisibility(View.GONE);  //隐藏ListView
        }
        }
    private void initView(){
        loadMoreView = getActivity().getLayoutInflater().inflate(R.layout.load_more, null);//找到 loadMoreView视图
        fragment1_adapter=new Fragment1_Adapter(getActivity(),
                R.layout.itv_tab1,CnInformation_ThemeList);//创建适配器
        loadMoreText = (TextView) loadMoreView.findViewById(R.id.load_more);
        loadMoreText.setBackgroundColor(getResources().getColor(R.color.gray));//设置背景颜色
        loadMoreText.setText("-- The End --");
        theme_listview.addFooterView(loadMoreView);//添加脚底视图
        theme_listview.setAdapter(fragment1_adapter);//设置适配器
        hintText = (TextView) view.findViewById(R.id.hint_text);   //当没有关注时提示的文本
    }

    @Override
    public void buttonClick(Integer id) {
        //创建Bundle,准备向Tab_Fragment1
    }
}
