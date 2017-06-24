package zzy.com.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import main.zzy.com.yun.R;
import zzy.com.Bean.GridViewBean;

/**
 * Created by Administrator on 2017/3/25.
 */

public class ZuiXin_GridViewAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private List<GridViewBean> pictures;
    public ZuiXin_GridViewAdapter(Context context, String[]titles, int []images){
        super();
        pictures = new ArrayList<>();
        inflater = LayoutInflater.from(context);
        for (int i = 0; i < images.length; i++)
        {
            GridViewBean picture = new GridViewBean(titles[i], images[i]);
            pictures.add(picture);
        }

    }
    @Override
    public int getCount() {
        if (null != pictures)
        {
            return pictures.size();
        } else
        {
            return 0;
        }
    }

    @Override
    public Object getItem(int i) {
        return pictures.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (convertView == null)
        {
            convertView = inflater.inflate(R.layout.fragment_music_personality_gridview, null);
            viewHolder = new ViewHolder();
            viewHolder.title = (TextView) convertView.findViewById(R.id.personality_tuijian_gridView_TextView);
            viewHolder.image = (ImageView) convertView.findViewById(R.id.personality_tuijian_girdView_ImageView);
            convertView.setTag(viewHolder);
        } else
        {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.title.setText(pictures.get(position).getTitle());
        viewHolder.image.setImageResource(pictures.get(position).getImageId());
        return convertView;
    }
}




