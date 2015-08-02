package org.zreo.cnbetareader.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.zreo.cnbetareader.Model.CnInformation_Theme;
import org.zreo.cnbetareader.R;

import java.util.List;

/**
 * Created by Admin on 2015/7/30.
 */
public class Information_theme_Adapter extends BaseAdapter {

     Context context;
    private int resourceId;
    private List<CnInformation_Theme> CnInformation_ThemeItem;
    public Information_theme_Adapter(Context mContext, int textViewResourceId, List<CnInformation_Theme> objects) {
        super();
        context=mContext;
        resourceId = textViewResourceId;
        CnInformation_ThemeItem=objects;

    }

    @Override
    public int getCount() {return CnInformation_ThemeItem.size();}

    @Override
    public Object getItem(int position) {return null;}

    @Override
    public long getItemId(int position) {return 0;}

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View iview;
        final ViewHolder holder;
        if(convertView==null){
            LayoutInflater inflater =LayoutInflater.from(context);
            iview = inflater.inflate(resourceId,null);
            holder = new ViewHolder();
            holder.firstword=(TextView)iview.findViewById(R.id.firstword_text1);
            holder.content =(TextView)iview.findViewById(R.id.content_text);
            holder.themetype=(TextView)iview.findViewById(R.id.itv_type);
            iview.setTag(holder);
        }
        else {
            iview=convertView;
            holder=(ViewHolder)iview.getTag();
        }
        holder.firstword.setText(CnInformation_ThemeItem.get(position).getFirstWord());
        holder.content.setText(CnInformation_ThemeItem.get(position).getContent());
        holder.themetype.setText(CnInformation_ThemeItem.get(position).getThemetype());

        return iview;
    }
    class ViewHolder{
        private TextView firstword;
        private TextView content ;
        private TextView themetype;

    }
}
