package zzy.com.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;

import java.util.List;

/**
 * Created by Administrator on 2017/3/28.
 */

public class Zhu_ViewPagerAdapter extends PagerAdapter {

    private List<ImageView> picture;
    public Zhu_ViewPagerAdapter(List<ImageView> picture){
        this.picture=picture;
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        position%=picture.size();
        if(position<0){
            position=picture.size()+position;
        }
        ImageView view1=picture.get(position);
        ViewParent vp= view1.getParent();
        if(vp!=null){
            ViewGroup parent= (ViewGroup) vp;
            parent.removeView(view1);
        }
        container.addView(view1);

        return view1;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        /*super.destroyItem(container, position, object);*/
    }
}
