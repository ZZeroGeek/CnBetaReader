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
    public Object getItem(int position) {
        return position;
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(int position,View convertView,ViewGroup parent ){
        View view;
        final ViewHolder holder;
        if (convertView==null){
            LayoutInflater inflater =LayoutInflater.from(context);
            view = inflater.inflate(resourceId,null);
            holder = new ViewHolder();
            holder.firstWord =(TextView)view.findViewById(R.id.firstWord_text);
            holder.content =(TextView)view.findViewById(R.id.content_text);
            holder.comment =(TextView)view.findViewById(R.id.comment_text);
            view.setTag(holder);
        }else {
            view=convertView;
            holder=(ViewHolder)view.getTag();
        }
        holder.firstWord.setText(Comment_hotItem.get(position).getFirstWord());
        holder.content.setText(Comment_hotItem.get( position).getContent());
        holder.comment.setText(Comment_hotItem.get(position).getComment());
        return view;
    }

   public class ViewHolder{

        public TextView firstWord;
        public TextView content ;
        public TextView comment;

    }
}

