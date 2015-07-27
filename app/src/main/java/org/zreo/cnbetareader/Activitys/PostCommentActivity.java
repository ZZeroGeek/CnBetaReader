package org.zreo.cnbetareader.Activitys;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


import org.zreo.cnbetareader.R;

public class PostCommentActivity extends Activity {

    public Button send;

    public PostCommentActivity() {
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_comment);
        send = (Button)findViewById(R.id.send_btn);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(PostCommentActivity.this,"发送成功", Toast.LENGTH_SHORT ).show();
            }
        });
    }

}
