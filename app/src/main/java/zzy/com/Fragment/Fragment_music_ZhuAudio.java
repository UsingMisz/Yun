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
import android.widget.ImageView;

import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import main.zzy.com.yun.R;
import zzy.com.Manager.pagerScroll;
import zzy.com.adapter.Zhu_ViewPagerAdapter;

/**
 * Created by Administrator on 2017/3/10.
 */

public class Fragment_music_ZhuAudio extends Fragment {

    private  View view;
    private zhu_ImageHandler handler =new zhu_ImageHandler(new WeakReference<Fragment_music_ZhuAudio>(this));
    private List<ImageView> picture;
    private ViewPager viewPager;
    private ViewGroup viewCircle;
    private ImageView imageView;
    private ImageView [] imageViews;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_music_zhu,null);
        init();
        setViewScrollSpeed();
        insertImageData();
        CircleData();
        viewPager.setCurrentItem(Integer.MAX_VALUE/2);//默认在中间，使用户看不到边界
        //开始轮播效果
        handler.sendEmptyMessageDelayed(Fragment_music_ZhuAudio.zhu_ImageHandler.MSG_UPDATE_IMAGE, Fragment_music_ZhuAudio.zhu_ImageHandler.MSG_DELAY);
        return view;
    }

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

    private void insertImageData() {
        picture=new ArrayList<>();
        ImageView image1=new ImageView(getContext());
        image1.setBackgroundResource(R.mipmap.drawerlayout_bg);
        ImageView image2=new ImageView(getContext());
        image2.setBackgroundResource(R.mipmap.drawerlayout_bg);
        ImageView image3=new ImageView(getContext());
        image3.setBackgroundResource(R.mipmap.drawerlayout_bg);
        picture.add(image1);
        picture.add(image2);
        picture.add(image3);
        Zhu_ViewPagerAdapter m=new Zhu_ViewPagerAdapter(picture);
        viewPager.setAdapter(m);//填充数据
        viewPager.addOnPageChangeListener(new Fragment_music_ZhuAudio.mPagerChangedListener());
    }

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

    private void init() {
        viewPager= (ViewPager) view.findViewById(R.id.zhu_viewPager);
        viewCircle= (ViewGroup) view.findViewById(R.id.zhu_viewPager_circle);
        
    }


    private static class zhu_ImageHandler extends Handler {

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
        private WeakReference<Fragment_music_ZhuAudio> weakReference;
        private int currentItem = 0;

        protected zhu_ImageHandler(WeakReference<Fragment_music_ZhuAudio> wk){
            weakReference = wk;
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            Fragment_music_ZhuAudio page = weakReference.get();
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
                if(i==(position+1)%3) {
                    imageViews[i].setBackgroundResource(R.mipmap.actionbar_discover_selected);
                }else{
                    imageViews[i].setBackgroundResource(R.mipmap.actionbar_discover_normal);
                }
            }

            handler.sendMessage(Message.obtain(handler, Fragment_music_ZhuAudio.zhu_ImageHandler.MSG_PAGE_CHANGED, position, 0));
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            switch (state) {
                case ViewPager.SCROLL_STATE_DRAGGING:
                    handler.sendEmptyMessage(Fragment_music_ZhuAudio.zhu_ImageHandler.MSG_KEEP_SILENT);
                    break;
                case ViewPager.SCROLL_STATE_IDLE:
                    handler.sendEmptyMessageDelayed(Fragment_music_ZhuAudio.zhu_ImageHandler.MSG_UPDATE_IMAGE, Fragment_music_ZhuAudio.zhu_ImageHandler.MSG_DELAY);
                    break;
                default:
                    break;
            }
        }

    }





}
