package org.zreo.cnbetareader.Activitys;

import android.app.Activity;
import android.os.Bundle;

import android.widget.ListView;
import android.widget.TextView;

import org.zreo.cnbetareader.Adapters.CommentAdapter;
import org.zreo.cnbetareader.Model.CnComment;
import org.zreo.cnbetareader.R;

import java.util.ArrayList;
import java.util.List;


public class CommentActivity extends Activity{

    TextView textView;
    ListView listView;
    TextView editText;
    private List<CnComment> cnCommentList = new ArrayList<CnComment>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.comment_listview);
        initCommentList();
        listView = (ListView) findViewById(R.id.listView);
        CommentAdapter myAdapter = new CommentAdapter(this, R.layout.comment_textview, cnCommentList);
        listView.setAdapter(myAdapter);
    }

    private void initCommentList(){
        String userName = "匿名用户";
        String textComment = "100块都不给我";
        String SaText = "支持:1  反对:0";
        for(int i = 0; i < 30; i++){

            CnComment cnComments = new CnComment();
            cnComments.setImageId(R.drawable.ic_launcher);
            cnComments.setUserName(userName);
            cnComments.setTestComment(textComment);
            cnComments.setCommentMenu(R.drawable.more_grey);
            cnComments.setSupportAgainst(SaText);
            cnCommentList.add(cnComments);
        }
    }
}
