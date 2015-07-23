package org.zreo.cnbetareader.Adapters;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import org.zreo.cnbetareader.Model.CnComment;
import org.zreo.cnbetareader.R;

import java.util.List;

/**
 * Created by Administrator on 2015/7/22.
 */
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

        private TextView vuserName;
        private ImageView vimageId;
        private TextView vtestComment;
        private Button vsupport;
        private Button vagainst;
        private ImageButton vcommentmenu;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {

        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.commentlistview, null);
            viewHolder.vimageId = (ImageView) convertView.findViewById(R.id.viname);
            viewHolder.vuserName = (TextView) convertView.findViewById(R.id.user);
            viewHolder.vsupport = (Button) convertView.findViewById(R.id.support);
            viewHolder.vagainst = (Button) convertView.findViewById(R.id.against);
            viewHolder.vcommentmenu = (ImageButton) convertView.findViewById(R.id.menubtn);
            viewHolder.vtestComment = (TextView) convertView.findViewById(R.id.testComment);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.vimageId.setImageResource(objects.get(position).getImageId());
        viewHolder.vuserName.setText(objects.get(position).getUserName());
        viewHolder.vsupport.setText(objects.get(position).getSupport());
        viewHolder.vagainst.setText(objects.get(position).getAgainst());
        viewHolder.vcommentmenu.setImageResource(objects.get(position).getCommentmenu());
        viewHolder.vtestComment.setText(objects.get(position).getTestComment());
        return convertView;
    }

}
