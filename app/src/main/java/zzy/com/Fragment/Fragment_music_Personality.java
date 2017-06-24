package zzy.com.Fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.GridView;
import android.widget.ImageView;

import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import main.zzy.com.yun.R;
import zzy.com.Manager.pagerScroll;
import zzy.com.adapter.Dujia_GridViewAdapter;
import zzy.com.adapter.Mv_GridViewAdapter;
import zzy.com.adapter.TuiJian_GridViewAdapter;
import zzy.com.adapter.ZhuBo_GridViewAdapter;
import zzy.com.adapter.Zhu_ViewPagerAdapter;
import zzy.com.adapter.ZuiXin_GridViewAdapter;


/**
 * Created by Administrator on 2017/3/10.
 */

public class Fragment_music_Personality extends Fragment {
   private View view;
    private ImageHandler handler =new ImageHandler(new WeakReference<Fragment_music_Personality>(this));
    private List<ImageView> picture;
    private ViewPager viewPager;
    private ViewGroup viewCircle;
    private ImageView imageView;
    private ImageView [] imageViews;
    private GridView tuijian,zhubo,zuixin,mv;
    /*测试gridview的数据*/
    private String [] titles=new String[]{"1","2","3","4","5","6"};
    private int[]images=new int[]{R.mipmap.error_image_tuijian,R.mipmap.error_image_tuijian,R.mipmap.error_image_tuijian,R.mipmap.error_image_tuijian,R.mipmap.error_image_tuijian,R.mipmap.error_image_tuijian,};
    private String []titles1=new String[]{"1","2","3","4"};
    private int [] images1=new int[]{R.mipmap.error_image_mv,R.mipmap.error_image_mv,R.mipmap.error_image_mv,R.mipmap.error_image_mv};
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
      view=inflater.inflate(R.layout.fragment_music_personality,null);
           init();
        gridviewAdapter();
        setViewScrollSpeed();
        insertImageData();
        CircleData();
         /*  TimeAuto();*/
        viewPager.setCurrentItem(Integer.MAX_VALUE/2);//默认在中间，使用户看不到边界
        //开始轮播效果
        handler.sendEmptyMessageDelayed(ImageHandler.MSG_UPDATE_IMAGE, ImageHandler.MSG_DELAY);
       return view;
    }

    private void gridviewAdapter() {
        //推荐的gridview
        TuiJian_GridViewAdapter adapter =new TuiJian_GridViewAdapter(getContext(),titles,images);
        tuijian.setAdapter(adapter);
        //主播的gridview
        ZhuBo_GridViewAdapter zhuboAdapter =new ZhuBo_GridViewAdapter(getContext(),titles,images);
        zhubo.setAdapter(zhuboAdapter);
        //最新的gridview
        ZuiXin_GridViewAdapter zuiXin_gridViewAdapter=new ZuiXin_GridViewAdapter(getContext(),titles,images);
        zuixin.setAdapter(zuiXin_gridViewAdapter);
        //独家的gridview
       Mv_GridViewAdapter Mv_gridViewAdapter=new Mv_GridViewAdapter(getContext(),titles1,images1);
        mv.setAdapter(Mv_gridViewAdapter);
    }


    private void init() {
        viewPager= (ViewPager) view.findViewById(R.id.personality_viewPager);
        viewCircle= (ViewGroup) view.findViewById(R.id.personality_viewPager_circle);
        tuijian= (GridView) view.findViewById(R.id.personality_tuijian_gridView);
        zhubo= (GridView) view.findViewById(R.id.personality_zhu_gridView);
        zuixin= (GridView) view.findViewById(R.id.personality_zuixin_gridView);
        mv= (GridView) view.findViewById(R.id.personality_dujia_gridView);
    }
/*设置自动viewpager的速度*/
    private void setViewScrollSpeed() {
        try {
            Field mScroller = null;
            mScroller = ViewPager.class.getDeclaredField("mScroller");
            mScroller.setAccessible(true);
            pagerScroll scroller = new pagerScroll(getContext());
            mScroller.set( viewPager, scroller);
        }catch(NoSuchFieldException e){

        }catch (IllegalArgumentException e){

        }catch (IllegalAccessException e){

        }
    }
/*为每一个viewpager设置数据*/
    private void insertImageData() {
        picture=new ArrayList<>();
        ImageView image1=new ImageView(getContext());
        image1.setBackgroundResource(R.mipmap.drawerlayout_bg);
        ImageView image2=new ImageView(getContext());
        image2.setBackgroundResource(R.mipmap.drawerlayout_bg);
        ImageView image3=new ImageView(getContext());
        image3.setBackgroundResource(R.mipmap.drawerlayout_bg);
        ImageView image4=new ImageView(getContext());
        image4.setBackgroundResource(R.mipmap.drawerlayout_bg);
        picture.add(image1);
        picture.add(image2);
        picture.add(image3);
        picture.add(image4);
        Zhu_ViewPagerAdapter m=new Zhu_ViewPagerAdapter(picture);
        viewPager.setAdapter(m);//填充数据
        viewPager.addOnPageChangeListener(new mPagerChangedListener());
    }
/*圆的数据*/
    private void CircleData() {
        imageViews=new ImageView[picture.size()];
        for(int i=0;i<picture.size();i++){
            imageView=new ImageView(getContext());
            imageView.setLayoutParams(new ViewGroup.LayoutParams(20,20));//设置宽和高
            imageView.setPadding(5,5,5,5);
            imageViews[i]=imageView;
            if(i==0) {
                imageViews[i].setBackgroundResource(R.drawable.actionbar_discover_selector);
            }else{
                imageViews[i].setBackgroundResource(R.mipmap.actionbar_discover_normal);
            }
            viewCircle.addView(imageViews[i]);

        }
    }

    private static class ImageHandler extends Handler {

        /**
         * 请求更新显示的View。
         */
        protected static final int MSG_UPDATE_IMAGE  = 1;
        /**
         * 请求暂停轮播。
         */
        protected static final int MSG_KEEP_SILENT   = 2;
        /**
         * 请求恢复轮播。
         */
        protected static final int MSG_BREAK_SILENT  = 3;
        /**
         * 记录最新的页号，当用户手动滑动时需要记录新页号，否则会使轮播的页面出错。
         * 例如当前如果在第一页，本来准备播放的是第二页，而这时候用户滑动到了末页，
         * 则应该播放的是第一页，如果继续按照原来的第二页播放，则逻辑上有问题。
         */
        protected static final int MSG_PAGE_CHANGED  = 4;

        //轮播间隔时间
        protected static final long MSG_DELAY = 3000;

        //使用弱引用避免Handler泄露.这里的泛型参数可以不是Activity，也可以是Fragment等
        private WeakReference<Fragment_music_Personality> weakReference;
        private int currentItem = 0;

        protected ImageHandler(WeakReference<Fragment_music_Personality> wk){
            weakReference = wk;
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            Fragment_music_Personality page = weakReference.get();
            if (page==null){
                //Activity已经回收，无需再处理UI了
                return ;
            }
            //检查消息队列并移除未发送的消息，这主要是避免在复杂环境下消息出现重复等问题。
            if (page.handler.hasMessages(MSG_UPDATE_IMAGE)){
                page.handler.removeMessages(MSG_UPDATE_IMAGE);
            }
            switch (msg.what) {
                case MSG_UPDATE_IMAGE:
                    currentItem++;
                    page.viewPager.setCurrentItem(currentItem);
                    //准备下次播放
                    page.handler.sendEmptyMessageDelayed(MSG_UPDATE_IMAGE, MSG_DELAY);
                    break;
                case MSG_KEEP_SILENT:
                    //只要不发送消息就暂停了
                    break;
                case MSG_BREAK_SILENT:
                    page.handler.sendEmptyMessageDelayed(MSG_UPDATE_IMAGE, MSG_DELAY);
                    break;
                case MSG_PAGE_CHANGED:
                    //记录当前的页号，避免播放的时候页面显示不正确。
                    currentItem = msg.arg1;
                    break;
                default:
                    break;
            }
        }
    }










    class mPagerChangedListener implements ViewPager.OnPageChangeListener{

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
           /* what.getAndSet(position);*/

            for(int i=0;i<picture.size();i++){
                if(i==(position+1)%4) {
                    imageViews[i].setBackgroundResource(R.mipmap.actionbar_discover_selected);
                }else{
                    imageViews[i].setBackgroundResource(R.mipmap.actionbar_discover_normal);
                }
            }

            handler.sendMessage(Message.obtain(handler, ImageHandler.MSG_PAGE_CHANGED, position, 0));
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            switch (state) {
                case ViewPager.SCROLL_STATE_DRAGGING:
                    handler.sendEmptyMessage(ImageHandler.MSG_KEEP_SILENT);
                    break;
                case ViewPager.SCROLL_STATE_IDLE:
                    handler.sendEmptyMessageDelayed(ImageHandler.MSG_UPDATE_IMAGE, ImageHandler.MSG_DELAY);
                    break;
                default:
                    break;
            }
        }

    }



}
