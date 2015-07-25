package org.zreo.cnbetareader.Activitys;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import org.zreo.cnbetareader.Adapters.CommentAdapter;
import org.zreo.cnbetareader.Model.CnComment;
import org.zreo.cnbetareader.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/7/23.
 */
public class CommentActivity extends Activity{
    private ListView listView;
    private CommentAdapter adapter;
    private List<CnComment> cnCommentList = new ArrayList<CnComment>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.commentlistview);

        initCnComments();

        adapter = new CommentAdapter(this,R.layout.commenttextview,cnCommentList);
        listView = (ListView)findViewById(R.id.listview);

        listView.setAdapter(adapter);
    }

    private void initCnComments(){
        CnComment cnComment1 = new CnComment();
        cnComment1.setImageId();



    }
}
