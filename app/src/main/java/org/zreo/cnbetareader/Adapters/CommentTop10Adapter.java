package org.zreo.cnbetareader.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.zreo.cnbetareader.Model.CnCommentTop10;
import org.zreo.cnbetareader.R;

import java.util.List;

/**
 * Created by Administrator on 2015/7/28.
 */
public class CommentTop10Adapter extends BaseAdapter {

    Context context;
    private int resourceId;
    private List<CnCommentTop10> CommentTop10Item;


    public CommentTop10Adapter(Context mContext, int textViewResourceId, List<CnCommentTop10> objects) {
        super();
        context=mContext;
        resourceId = textViewResourceId;
        CommentTop10Item=objects;

    }

    @Override
    public int getCount() {
        return CommentTop10Item.size();
    }

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
            holder.newsTitle =(TextView) view.findViewById(R.id.news_title1);
            holder.hot =(TextView)view.findViewById(R.id.hot);
            holder.ranking =(TextView)view.findViewById(R.id.ranking);
            view.setTag(holder);
        }else {
            view=convertView;
            holder=(ViewHolder)view.getTag();
        }
        holder.newsTitle.setText(CommentTop10Item.get(possion).getNewsTitle());
        holder.hot.setText(CommentTop10Item.get(possion).getHot());
        holder.ranking.setText(CommentTop10Item.get(possion).getRanking());
        return view;
    }

    class ViewHolder{

        private TextView newsTitle;
        private TextView ranking ;
        private TextView hot;

    }
}

