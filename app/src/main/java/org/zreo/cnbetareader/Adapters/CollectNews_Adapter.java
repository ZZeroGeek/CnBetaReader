package org.zreo.cnbetareader.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import org.zreo.cnbetareader.Model.CollectNews;
import org.zreo.cnbetareader.R;

import java.util.List;

/**
 * Created by Admin on 2015/8/1.
 */
public class CollectNews_Adapter extends BaseAdapter {
    Context newscontext;
    private int resourceId;
    private List<CollectNews> CollectNewsItem;

    public CollectNews_Adapter(Context mContext,int newsResourceId,List<CollectNews>objects){
        super();
        newscontext=mContext;
        resourceId=newsResourceId;
        CollectNewsItem=objects;
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
        if (convertView==null){
            LayoutInflater inflater =LayoutInflater.from(newscontext);
            view = inflater.inflate(resourceId,null);
            holder = new ViewHolder();
            holder.firstword_text3 =(TextView)view.findViewById(R.id.firstword_text3);
            holder.newscontent =(TextView)view.findViewById(R.id.itv_news);
            holder. newstypes =(TextView)view.findViewById(R.id.itv_type);
            view.setTag(holder);
        }else {
            view=convertView;
            holder=(ViewHolder)view.getTag();
        }
        holder.firstword_text3.setText(CollectNewsItem.get(position).getNewsfirstWord());
        holder.newscontent.setText(CollectNewsItem.get(position).getNewscontent());
        holder. newstypes.setText(CollectNewsItem.get(position).getNewstypes());
        return view;
    }

    public class ViewHolder{

        public TextView firstword_text3;
        public TextView newscontent ;
        public TextView  newstypes;

    }
    }

