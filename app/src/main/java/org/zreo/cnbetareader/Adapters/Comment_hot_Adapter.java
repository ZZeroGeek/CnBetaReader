package org.zreo.cnbetareader.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.zreo.cnbetareader.Model.CnComment_hot;
import org.zreo.cnbetareader.R;

import java.util.List;

/**
 * Created by Administrator on 2015/7/29.
 */
public class Comment_hot_Adapter extends BaseAdapter {
    Context  context;
    private int resourceId;
    private List<CnComment_hot> Comment_hotItem;
    public Comment_hot_Adapter(Context mContext,int texeViewResourceId,List<CnComment_hot>objects){
       super();
        context = mContext;
        resourceId = texeViewResourceId;
        Comment_hotItem = objects;
    }
    @Override
    public  int getCount(){ return Comment_hotItem.size();}
    @Override
    public Object getItem(int i) {
        return null;
    }


    @Override
    public long getItemId(int i) {
        return 0;
    }
    @Override
    public View getView(int possion,View convertView,ViewGroup parent ){
        View view;
        final ViewHolder holder;
        if (convertView==null){
            LayoutInflater inflater =LayoutInflater.from(context);
            view = inflater.inflate(resourceId,null);
            holder = new ViewHolder();
            holder.firstword =(TextView) view.findViewById(R.id.firstword_text);
            holder.content =(TextView)view.findViewById(R.id.content_text);
            holder.comment =(TextView)view.findViewById(R.id.comment_text);
            view.setTag(holder);
        }else {
            view=convertView;
            holder=(ViewHolder)view.getTag();
        }
        holder.firstword.setText(Comment_hotItem.get(possion).getFirstWord());
        holder.content.setText(Comment_hotItem.get(possion).getContent());
        holder.comment.setText(Comment_hotItem.get(possion).getComment());
        return view;
    }

    class ViewHolder{

        private TextView firstword;
        private TextView content ;
        private TextView comment;

    }
}

