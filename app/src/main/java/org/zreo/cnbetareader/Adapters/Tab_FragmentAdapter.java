package org.zreo.cnbetareader.Adapters;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.zreo.cnbetareader.Entitys.CnComment;
import org.zreo.cnbetareader.Model.CnInformation_Theme;
import org.zreo.cnbetareader.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 2015/8/10.
 */
public class Tab_FragmentAdapter extends BaseAdapter {
    Context mContext;
    private int resourceId;
    private List<CnInformation_Theme> CollectNewsItem;
    public  Tab_FragmentAdapter(Context context,int resourceId,List<CnInformation_Theme>objects){
        super();
        this.mContext=context;
        this.resourceId=resourceId;
        this.CollectNewsItem=objects;
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
        final View view;
        final ViewHolder viewHolder;
        if(convertView==null){
            LayoutInflater inflater=LayoutInflater.from(mContext);
            view=inflater.inflate(resourceId,null);
            viewHolder=new ViewHolder();
            viewHolder.newType= (TextView) view.findViewById(R.id.tv_type2);
            viewHolder.newFirstword= (TextView) view.findViewById(R.id.firstword_text2);
            viewHolder.newTitle= (TextView) view.findViewById(R.id.itv_context2);
            viewHolder.btn= (Button) view.findViewById(R.id.ibtn_theme);
            viewHolder.btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mContext,"关注新闻成功！",Toast.LENGTH_SHORT).show();
                }
            });
            view.setTag(viewHolder);
        }
        else{
            view = convertView;
            viewHolder=(ViewHolder)view.getTag();
        }
        String title = CollectNewsItem.get(position).getContent();
        String newFirstword = CollectNewsItem.get(position).getFirstWord();
        String newType = CollectNewsItem.get(position).getThemetype();
        viewHolder.newTitle.setText(title);
        viewHolder.newFirstword.setText(newFirstword);
        //viewHolder.newType.setText(newType);
        return view;
    }
public class ViewHolder{
    public TextView newType;
    public TextView newFirstword;
    public TextView newTitle;
    public Button btn;

}
}