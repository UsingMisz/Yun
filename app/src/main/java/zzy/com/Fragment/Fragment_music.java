package zzy.com.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import java.util.ArrayList;
import java.util.List;

import main.zzy.com.yun.R;

/**
 * Created by Administrator on 2017/3/10.
 */

public class Fragment_music extends Fragment {
    private View view;
    private Fragment fragment_music_musicList;
    private Fragment fragment_music_personality;
    private Fragment fragment_music_ZhuAudio;
    private Fragment fragment_music_pai;
    private TabLayout fragment_music_TabLayout;
    private ViewPager fragment_music_ViewPager;
    private List<String> list_tab=new ArrayList<>();
    private List<Fragment> list=new ArrayList<>();
    private FragmentPagerAdapter fragmentPagerAdapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view= inflater.inflate(R.layout.fragment_music,null);
         init();
         addFragment();
        content();
        fragmentAdaper();

        return view;
    }

    /**
     * 添加四个fragment
     *
     */
    private void addFragment() {
        fragment_music_personality=new Fragment_music_Personality();
        fragment_music_musicList=new Fragment_music_musicList();
        fragment_music_ZhuAudio=new Fragment_music_ZhuAudio();
        fragment_music_pai=new Fragment_music_pai();
        if(list.size()==0) {
            list.add(fragment_music_personality);
            list.add(fragment_music_musicList);
            list.add(fragment_music_ZhuAudio);
            list.add(fragment_music_pai);
        }
    }


    private void init() {
        fragment_music_TabLayout= (TabLayout) view.findViewById(R.id.fragment_music_TabLayout);
        fragment_music_ViewPager= (ViewPager) view.findViewById(R.id.fragment_music_ViewPager);
        if(list_tab.size()==0) {
            list_tab.add("个性推荐");
            list_tab.add("歌单");
            list_tab.add("主播电台");
            list_tab.add("排行榜");
        }
    }


    private void content() {
        fragment_music_TabLayout.setTabMode(TabLayout.MODE_FIXED);
        fragment_music_TabLayout.addTab(fragment_music_TabLayout.newTab().setText(list_tab.get(0)));
        fragment_music_TabLayout.addTab(fragment_music_TabLayout.newTab().setText(list_tab.get(1)));
        fragment_music_TabLayout.addTab(fragment_music_TabLayout.newTab().setText(list_tab.get(2)));
        fragment_music_TabLayout.addTab(fragment_music_TabLayout.newTab().setText(list_tab.get(3)));
    }
    private void fragmentAdaper() {

        fragmentPagerAdapter=new FragmentPagerAdapter(this.getFragmentManager() ) {
            @Override
            public Fragment getItem(int position) {
                return list.get(position);
            }

            @Override
            public int getCount() {
                return  list.size();
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return list_tab.get(position);
            }


        };
        fragment_music_ViewPager.setCurrentItem(0);
        fragment_music_ViewPager.setAdapter(fragmentPagerAdapter);
        fragment_music_TabLayout.setupWithViewPager(fragment_music_ViewPager);
        fragment_music_TabLayout.setTabsFromPagerAdapter(fragmentPagerAdapter);

    }
}
