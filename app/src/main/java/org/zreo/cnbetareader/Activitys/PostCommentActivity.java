package org.zreo.cnbetareader.Activitys;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import org.simple.eventbus.EventBus;
import org.zreo.cnbetareader.Adapters.CommentAdapter;
import org.zreo.cnbetareader.Entitys.CheckCode;
import org.zreo.cnbetareader.Entitys.CnComment;
import org.zreo.cnbetareader.R;
import org.zreo.cnbetareader.Views.XListView;

import java.util.ArrayList;
import java.util.List;

public class PostCommentActivity extends AppCompatActivity {

    EditText commentTest;
    ImageView codeImage;
    Button refresh;
    String getCode=null;
    EditText editCode;
    public Button send;
    private Toolbar mToolbar;
    private List<CnComment> cnCommentList = new ArrayList<CnComment>();
    CommentAdapter myAdapter = new CommentAdapter(this, R.layout.comment_textview, cnCommentList);
    private XListView myListView;

    public PostCommentActivity() {
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_comment);
        commentTest = (EditText)findViewById(R.id.commentTest);
        codeImage =(ImageView)findViewById(R.id.codeImage);
        codeImage.setImageBitmap(CheckCode.getInstance().getBitmap());
        editCode =(EditText) findViewById(R.id.mEditPass);
        getCode=CheckCode.getInstance().getCode(); //获取显示的验证码
        EventBus.getDefault().register(this);//注册EventBus
        refresh =(Button)findViewById(R.id.refresh);
        refresh.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                codeImage.setImageBitmap(CheckCode.getInstance().getBitmap());
                getCode = CheckCode.getInstance().getCode();
            }
        });

        send = (Button)findViewById(R.id.send_btn);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                String v_code = editCode.getText().toString().trim();
                if (v_code == null || v_code.equals("")) {
                    Toast.makeText(PostCommentActivity.this, "没有填写验证码", Toast.LENGTH_LONG).show();
                } else if (!v_code.equals(getCode)) {
                    Toast.makeText(PostCommentActivity.this, "验证码填写不正确", Toast.LENGTH_LONG).show();
                } else {

                    EventBus.getDefault().post(new PostCommentActivityEvent(commentTest.getText().toString()));
                    // String content = commentTest.getText().toString();
                  // if (!"".equals(content)) {
                       /* new一个Intent对象，并指定class */
//                       Intent intent = new Intent();
//                       intent.setClass(PostCommentActivity.this, CommentActivity.class);
//                       //new一个Bundle对象，并将要传递的数据传入
//                       Bundle bundle = new Bundle();
//                       bundle.putString("content", content);
//                       intent.putExtras(bundle);//将Bundle对象assign给Intent
//                       startActivityForResult(intent, 0);// 调用Activity
//                       ArrayList<CnComment> resultList = new ArrayList<CnComment>();
//                       CnComment cnComments = new CnComment();
//                       cnComments.setFName("匿");
//                       cnComments.setSupportNumber(0);
//                       cnComments.setAgainstNumber(0);
//                       cnComments.setImageId(R.drawable.circle_image);
//                       cnComments.setUserName("匿名用户");
//                       cnComments.setTestComment(content);
//                       cnComments.setCommentMenu(R.id.button1);
//                       cnComments.setSupport("支持");
//                       cnComments.setAgainst("反对");
//                       resultList.add(cnComments);
//                      // CnComment cnComment = new CnComment("匿", "匿名用户",R.id.imageView1, content, "支持", "反对",R.id.button1, 0, 0,0);
//                       myAdapter.AddData(resultList);
//                     //   myAdapter.notifyDataSetChanged(); // 当有新消息时，刷新ListView中的显示
//                      // myListView.setSelection(cnCommentList.size()); // 将ListView定位到最后一行
//                       commentTest.setText(""); // 清空输入框中的内容
                        Toast.makeText(PostCommentActivity.this, "发送成功", Toast.LENGTH_LONG).show();
                        onBackPressed();
                   // }
                    // Toast.makeText(PostCommentActivity.this,"发送成功", Toast.LENGTH_SHORT ).show();
                }
           }
        });
        mToolbar = (Toolbar) findViewById(R.id.toolbar);   //ToolBar布局
        mToolbar.setTitle("发表评论");   // 标题的文字需在setSupportActionBar之前，不然会无效
        mToolbar.setTitleTextColor(Color.WHITE);  //设置ToolBar字体颜色为白色
        setSupportActionBar(mToolbar);  //将ToolBar设置为ActionBAr
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);  //在ToolBar左边，即当前标题前添加图标
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    /* 重写 onActivityResult() */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case RESULT_OK :
                Bundle bundle = data.getExtras();
                String content = bundle.getString("content");
                commentTest.setText(content);
                break;
            default:
                break;
        }
    }
    class PostCommentActivityEvent{
        String content = commentTest.getText().toString();
        public PostCommentActivityEvent( String pContent){
            content = pContent;
        }
        public  String getContent(){
            return content;
        }
    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
        EventBus.getDefault().unregister(this);//反注册EventBus
    }
}
