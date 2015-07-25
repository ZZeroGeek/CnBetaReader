package org.zreo.cnbetareader.Adapters;

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

    public CommentAdapter(Context context, int textViewResourceId,List<CnComment> objects) {
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
    public CnComment getItem(int i) {
        return null;
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
        private ImageButton vCommentmenu;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {

        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = mInflater.inflate(resourceId, null);
            viewHolder.vImageId = (ImageView) convertView.findViewById(R.id.ivName);
            viewHolder.vUserName = (TextView) convertView.findViewById(R.id.user);
            viewHolder.vSupport = (TextView) convertView.findViewById(R.id.support);
            viewHolder.vAgainst = (TextView) convertView.findViewById(R.id.against);
            viewHolder.vCommentmenu = (ImageButton) convertView.findViewById(R.id.menubtn);
            viewHolder.vTestComment = (TextView) convertView.findViewById(R.id.testComment);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.vImageId.setImageResource(objects.get(position).getImageId());
        viewHolder.vUserName.setText(objects.get(position).getUserName());
        viewHolder.vSupport.setText(objects.get(position).getSupport());
        viewHolder.vAgainst.setText(objects.get(position).getAgainst());
        viewHolder.vCommentmenu.setImageResource(objects.get(position).getCommentMenu());
        viewHolder.vTestComment.setText(objects.get(position).getTestComment());
        return convertView;
    }

}
