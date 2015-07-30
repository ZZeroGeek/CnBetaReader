package org.zreo.cnbetareader.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.zreo.cnbetareader.Entitys.NewsEntity;
import org.zreo.cnbetareader.R;

import java.util.List;

/**
 * Created by guang on 2015/7/24. 实现视图与数据的绑定
 */

public class NewsTitleAdapter extends BaseAdapter{
    private LayoutInflater mInflater;
    private int resourceId;
    private List<NewsEntity> listItem;
    /**构造函数*/
    public NewsTitleAdapter(Context context, int textViewResourcedId, List<NewsEntity> objects) {
        super();
        resourceId = textViewResourcedId;
        listItem = objects;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return listItem.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        ViewHolder viewHolder;
        if (convertView == null) {
            view = mInflater.inflate(resourceId, null);
            viewHolder = new ViewHolder();
            viewHolder.newsTitle = (TextView) view.findViewById(R.id.news_title);
            viewHolder.newsContent = (TextView) view.findViewById(R.id.news_summary);
            viewHolder.publishTime = (TextView) view.findViewById(R.id.news_publish_time);
            viewHolder.imageId = (ImageView) view.findViewById(R.id.news_picture);
            viewHolder.commentNumber = (TextView) view.findViewById(R.id.news_comment_number);
            viewHolder.readerNumber = (TextView) view.findViewById(R.id.news_reader_number);
            view.setTag(viewHolder);
        }
        else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag(); //重新获取viewHolder
        }
        viewHolder.newsTitle.setText(listItem.get(position).getTitle());
        viewHolder.newsContent.setText(listItem.get(position).getHometext());
        viewHolder.publishTime.setText(listItem.get(position).getInputtime());
        //viewHolder.imageId.setImageResource(listItem.get(position).getImageId());
        viewHolder.commentNumber.setText(String.valueOf(listItem.get(position).getComments()));
        viewHolder.readerNumber.setText(String.valueOf(listItem.get(position).getCounter()));
        return view;
    }
    class ViewHolder {
        private TextView newsTitle;
        private TextView newsContent;
        private TextView publishTime;
        private ImageView imageId;
        private TextView commentNumber;
        private TextView readerNumber;
    }
}
