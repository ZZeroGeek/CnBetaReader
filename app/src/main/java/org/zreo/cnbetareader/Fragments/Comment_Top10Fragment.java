package org.zreo.cnbetareader.Fragments;

        import android.app.Fragment;
        import android.os.Bundle;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ListView;

        import org.zreo.cnbetareader.Adapters.CommentTop10Adapter;
        import org.zreo.cnbetareader.Model.CnCommentTop10;
        import org.zreo.cnbetareader.R;

        import java.util.ArrayList;
        import java.util.List;

/**
 * Created by Administrator on 2015/7/29.
 */
public class Comment_Top10Fragment extends Fragment {
    private ListView top10_listView;
    private CommentTop10Adapter mAdapter;
    private List<CnCommentTop10> cnCommentTop10List = new ArrayList<CnCommentTop10>();

    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState){
        View rootview = inflater.inflate(R.layout.fragment_c_top10listview,container,false);
        initCommentTop10List();
        top10_listView = (ListView)rootview.findViewById(R.id.top10_listView);
        CommentTop10Adapter myAdapter = new CommentTop10Adapter(getActivity(),R.layout.fragment_c_top10textview,cnCommentTop10List);
        top10_listView.setAdapter(myAdapter);
        return rootview;
    }
    private void initCommentTop10List(){
        String title = "I love you not for who you are, but for who I am before you ";
        String hot = "25452";
        String ranking ="i";
        for(int i = 1; i < 11; i++){

            CnCommentTop10 cnCommentTop10s = new CnCommentTop10();
            cnCommentTop10s.setNewsTitle(title);
            cnCommentTop10s.setRanking(i+"");
            cnCommentTop10s.setHot(hot);
            cnCommentTop10List.add(cnCommentTop10s);
        }
    }
}