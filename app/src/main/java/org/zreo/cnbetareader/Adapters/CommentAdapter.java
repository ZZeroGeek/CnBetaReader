package org.zreo.cnbetareader.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import org.zreo.cnbetareader.Entitys.CnComment;
import org.zreo.cnbetareader.R;

import java.util.ArrayList;
import java.util.List;

public class CommentAdapter extends BaseAdapter{

    Context _context;
    private int resourceId;
    private List<CnComment> commentItem;

    public  CommentAdapter(Context mContext,int textViewResourcedId, List<CnComment> objects) {
        // TODO Auto-generated constructor stub
        _context=mContext;
        resourceId = textViewResourcedId;
        commentItem=objects;
    }

    public void addList(ArrayList<CnComment> list) {
        if (list != null) {
            commentItem.addAll(list);
        } else {
            commentItem = new ArrayList<CnComment>();
        }
    }
    public void AddData(ArrayList<CnComment> list) {
        this.addList(list);
        this.notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return commentItem.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub

        // TODO Auto-generated method stub
        View view;
        final ViewHolder holder;

        if(convertView==null){
            LayoutInflater inflater = LayoutInflater.from(_context);
            view = inflater.inflate(resourceId, null);
            holder = new ViewHolder();
            holder.imageView=(ImageView)view.findViewById(R.id.imageView1);
            holder.textView=(TextView)view.findViewById(R.id.user_name);
            holder.textView1 =(TextView)view.findViewById(R.id.support);
            holder.viewBtn=(ImageButton)view.findViewById(R.id.button1);
            holder.textView2 =(TextView)view.findViewById(R.id.comment_text);
            holder.textView3 = (TextView)view.findViewById(R.id.against);

           View.OnClickListener listener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PopupMenu popup = new PopupMenu(_context, v);
                    popup.getMenuInflater().inflate(R.menu.popup, popup.getMenu());

                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            switch (item.getItemId()) {
                                case R.id.action1:
                                    Toast.makeText(_context, "你点击了: " + item.getTitle(), Toast.LENGTH_SHORT).show();
                                    break;
                                case R.id.action2:
                                    Toast.makeText(_context, "你点击了: " + item.getTitle(), Toast.LENGTH_SHORT).show();
                                    break;
                                case R.id.action3:

                                    AlertDialog.Builder builder = new AlertDialog.Builder(_context);
                                    LayoutInflater inflater = LayoutInflater.from(_context);//getLayoutInflater();
                                    View view = inflater.inflate(R.layout.response_dialog, null);

                                    // builder.setTitle();
                                    builder.setView(view);

                                    builder.setPositiveButton("发送", new DialogInterface.OnClickListener() {

                                        @Override
                                        public void onClick(DialogInterface arg0, int arg1) {
                                            // TODO Auto-generated method stub

                                        }
                                    });

                                    builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

                                        @Override
                                        public void onClick(DialogInterface arg0, int arg1) {
                                            // TODO Auto-generated method stub

                                        }
                                    });

                                    AlertDialog dialog = builder.create();
                                    dialog.show();

                                    //Toast.makeText(_context, " 发送成功" , Toast.LENGTH_SHORT).show();
                                    break;
                            }

                            return true;
                        }
                    });
                    popup.show();
                }
            };
            holder.viewBtn.setOnClickListener(listener);
            view.setTag(holder);
        }
        else{
            view = convertView;
            holder=(ViewHolder)view.getTag();
        }

        //ImageView imageView=(ImageView)view.findViewById(R.id.imageView1);
        //TextView textView = (TextView)view.findViewById(R.id.textView1);
        //imageView.setImageResource(_images[position]);
        //textView.setText(_texts[position]);
        holder.imageView.setImageResource(commentItem.get(position).getImageId());
        holder.textView2.setText(commentItem.get(position).getTestComment());
        holder.textView.setText(commentItem.get(position).getUserName());
        holder.textView1.setText(commentItem.get(position).getSupport());
        holder.viewBtn.setImageResource(commentItem.get(position).getCommentMenu());
        holder.textView3.setText(commentItem.get(position).getAgainst());
       /* holder.viewBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

              // Toast.makeText(_context, "你点击了ImageButton", Toast.LENGTH_SHORT) .show();

            }
        });*/
        return view;
    }
    public class ViewHolder{
        public ImageView imageView;
        public TextView textView1;
        public TextView textView;
        public ImageButton viewBtn;
        public TextView textView2;
        public TextView textView3;
    }

}
