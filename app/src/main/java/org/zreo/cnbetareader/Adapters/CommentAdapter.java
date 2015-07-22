package org.zreo.cnbetareader.Adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import org.zreo.cnbetareader.Model.CnComment;

import java.util.List;

/**
 * Created by Administrator on 2015/7/22.
 */
public class CommentAdapter extends BaseAdapter{

    private  int resourceId;

    public CommentAdapter(Context context, int textViewResourceId, List<CnComment> objects) {

        this.resourceId = resourceId;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }
}
