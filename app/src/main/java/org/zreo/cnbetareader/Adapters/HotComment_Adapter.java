package org.zreo.cnbetareader.Adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.zreo.cnbetareader.Entitys.HotCommentItemEntity;
import org.zreo.cnbetareader.R;

import java.util.List;

/**
 * Created by Administrator on 2015/7/29.
 */
public class HotComment_Adapter extends BaseAdapter {
    Context mContext;
    private int resourceId;
    SharedPreferences pref;
    private List<HotCommentItemEntity> HotCommentItem;

    public HotComment_Adapter(Context context,int textViewResourceId,List<HotCommentItemEntity>objects){
        super();
        mContext = context;
        resourceId = textViewResourceId;
        HotCommentItem = objects;
        pref = mContext.getSharedPreferences("org.zreo.cnbetareader_preferences", Context.MODE_PRIVATE);

    }
    @Override
    public int getCount() {
        return HotCommentItem.size();
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
            holder.firstWord = (TextView) view.findViewById(R.id.firstWord_text);
            holder.hotCommentNewsTitle = (TextView) view.findViewById(R.id.hot_comment_news_title);
            holder.title = (TextView) view.findViewById(R.id.hot_comment_title);
            holder.description =(TextView) view.findViewById(R.id.description);
            view.setTag(holder);

        }else {
            view = convertView;
            holder = (ViewHolder)view.getTag();
        }

        String title = HotCommentItem.get(position).getTitle();
        holder.title.setText(title);
        if(title.charAt(0) == '['){
            holder.firstWord.setText(String.valueOf(title.charAt(4)));
        }else{
            if(title.charAt(0) == '《'){
                holder.firstWord.setText(String.valueOf(title.charAt(1)));
            }else {
                holder.firstWord.setText(String.valueOf(title.charAt(0)));
            }

        }

       // GradientDrawable grad = (GradientDrawable) holder.firstWord.getBackground();
        //myGrad.setColor(Color.RED);
       // setImageColor(grad, position);
        holder.hotCommentNewsTitle.setText(HotCommentItem.get(position).getNewstitle());
        holder.description.setText(HotCommentItem.get(position).getDescription());
        holder.title.setText(HotCommentItem.get(position).getTitle());

        return view;
    }

  /*  public void setImageColor(GradientDrawable grad, int index){

        int blue = Resources.getSystem().getColor(android.R.color.holo_blue_light);
        int gray = Resources.getSystem().getColor(android.R.color.darker_gray);
        int greenDark = Resources.getSystem().getColor(android.R.color.holo_green_dark);
        int greenLight = Resources.getSystem().getColor(android.R.color.holo_green_light);
        int purple = Resources.getSystem().getColor(android.R.color.holo_purple);
        int orange = Resources.getSystem().getColor(android.R.color.holo_orange_light);
        int mainColor = mContext.getResources().getColor(R.color.mainColor);
        int [] colorList = {blue, gray, purple, greenDark, orange, greenLight, mainColor};  //7种颜色
        grad.setColor(colorList[Math.abs(HotCommentItem.size() - index) % 7]);

    }*/


    public class ViewHolder{

        public TextView firstWord;
        public TextView hotCommentNewsTitle ;
        public TextView title;
        public TextView description;
    }
}

