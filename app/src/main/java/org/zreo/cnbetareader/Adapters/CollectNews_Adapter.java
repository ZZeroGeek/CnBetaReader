package org.zreo.cnbetareader.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import org.zreo.cnbetareader.Entitys.NewsEntity;
import org.zreo.cnbetareader.Model.CollectNews;
import org.zreo.cnbetareader.R;

import java.util.List;

/**
 * Created by Admin on 2015/8/1.
 */
public class CollectNews_Adapter extends BaseAdapter {
    Context mContext;
    private int resourceId;
    private List<NewsEntity> CollectNewsItem;

    public CollectNews_Adapter(Context context,int newsResourceId,List<NewsEntity>objects){
        super();
        mContext = context;
        resourceId = newsResourceId;
        CollectNewsItem = objects;
    }
    @Override
    public int getCount() {
        return CollectNewsItem.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        final ViewHolder holder;
        if (convertView == null){

            LayoutInflater inflater = LayoutInflater.from(mContext);
            view = inflater.inflate(resourceId, null);
            holder = new ViewHolder();
            holder.firstWord =(TextView)view.findViewById(R.id.first_word);
            holder.collectNewsTitle =(TextView)view.findViewById(R.id.collect_news_title);
            view.setTag(holder);

        }else {
            view = convertView;
            holder = (ViewHolder)view.getTag();
        }

        String title = CollectNewsItem.get(position).getTitle();
        holder.collectNewsTitle.setText(title);
        if(title.charAt(0) == '['){
            holder.firstWord.setText(String.valueOf(title.charAt(4)));
        }else{
            if(title.charAt(0) == '《'){
                holder.firstWord.setText(String.valueOf(title.charAt(1)));
            }else {
                holder.firstWord.setText(String.valueOf(title.charAt(0)));
            }

        }

        return view;
    }

    public class ViewHolder{

        public TextView firstWord;
        public TextView collectNewsTitle ;

    }
}

