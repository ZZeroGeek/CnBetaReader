package org.zreo.cnbetareader.Fragments.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import org.zreo.cnbetareader.Model.CnComment;
import org.zreo.cnbetareader.R;

import java.util.List;

public class CommentAdapter extends BaseAdapter{

    LayoutInflater mInflater;
    private  int resourceId;
    List<CnComment> objects;

    public CommentAdapter(Context context, int textViewResourceId, List<CnComment> objects) {
        super();
        this.mInflater = LayoutInflater.from(context);
        this.objects = objects;
        this.resourceId = textViewResourceId;
    }

    @Override
    public int getCount() {
        return objects.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    class ViewHolder{

        private TextView vUserName;
        private ImageView vImageId;
        private TextView vTestComment;
        private TextView vSupport;
        private TextView vAgainst;
        private ImageButton vCommentMenu;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        View view;
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            view = mInflater.inflate(resourceId, null);
            viewHolder.vImageId = (ImageView) convertView.findViewById(R.id.ivName);
            viewHolder.vUserName = (TextView) convertView.findViewById(R.id.user);
            viewHolder.vSupport  = (TextView)convertView.findViewById(R.id.support);
            viewHolder.vAgainst = (TextView) convertView.findViewById(R.id.against);
            viewHolder.vCommentMenu = (ImageButton) convertView.findViewById(R.id.igBtn);
            viewHolder.vTestComment = (TextView) convertView.findViewById(R.id.testComment);
            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.vImageId.setImageResource(objects.get(position).getImageId());
        viewHolder.vUserName.setText(objects.get(position).getUserName());
        viewHolder.vSupport.setText(String.valueOf(objects.get(position).getSupport()));
        viewHolder.vAgainst.setText(String.valueOf(objects.get(position).getAgainst()));
        viewHolder.vCommentMenu.setImageResource(objects.get(position).getCommentMenu());
        viewHolder.vTestComment.setText(objects.get(position).getTestComment());
        return view;
    }

}
