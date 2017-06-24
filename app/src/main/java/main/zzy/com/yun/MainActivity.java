package main.zzy.com.yun;

import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.PersistableBundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.List;

import zzy.com.Application.XutilsApplication;
import zzy.com.Bean.qq_user;
import zzy.com.Fragment.Fragment_music;
import zzy.com.Fragment.Fragment_music2;
import zzy.com.Fragment.TestFragment2;
import zzy.com.Manager.SharedPreferencesHelper;
import zzy.com.Manager.Utils;

/**
 *
 */

public class MainActivity extends BaseActivity implements View.OnClickListener {
    private Intent intent;
    private Toolbar toobar;
    private DrawerLayout drawerLayout;
    private LinearLayout LeftDraw;
    private ActionBarDrawerToggle mActionBarDrawerToggle;
    //----------------------------------------------------
    private ViewPager viewPager;
    private FragmentPagerAdapter fragmentAdapter;
    private Fragment fragment_music;
    private Fragment fragment_music2;
    private Fragment testFragment2;
    private List<Fragment> list = new ArrayList<Fragment>();
    //-----------------------------------------------------
    private ImageButton actionbar_music;
    private ImageButton actionbar_discover;
    private ImageButton actionbar_friends;

    //-----------------------------------------------------
    private Button drawerLayout_login_button;
    private Button drawerLayout_LoginOut;
    private ImageView drawerLayout_bg,drawerLayout_touxiang;
    private TextView drawerLayout_text1,drawerLayout_text2,drawerLayout_name;

    //-----------------------------------------------------
    private XutilsApplication app;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        onClickButton();
        toobar();
        fragmentManager();
        viewPager();
        drawerLayout();
        app=(XutilsApplication) getApplication();
        isLogin();
    }



    private void onClickButton() {
        drawerLayout_login_button.setOnClickListener(this);
        drawerLayout_LoginOut.setOnClickListener(this);
        drawerLayout_touxiang.setOnClickListener(this);
    }


    private void init() {

        //--------------------------------------------------------
        toobar = (Toolbar) findViewById(R.id.activity_toobar);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        LeftDraw = (LinearLayout) findViewById(R.id.Left_Drawer);
        //-------------------------------------------------------
        viewPager = (ViewPager) findViewById(R.id.Actionbar_ViewPager);
        //-------------------------------------------------------
        actionbar_discover = (ImageButton) findViewById(R.id.actionbar_discover);
        actionbar_music = (ImageButton) findViewById(R.id.actionbar_music);
        actionbar_friends = (ImageButton) findViewById(R.id.actionbar_friends);
        //---------------------------------------------------------
        drawerLayout_login_button= (Button) findViewById(R.id.drawerLayout_login_button);
        drawerLayout_LoginOut= (Button) findViewById(R.id.drawerLayout_Logout);
        drawerLayout_bg= (ImageView) findViewById(R.id.drawerLayout_bg);
        drawerLayout_text1= (TextView) findViewById(R.id.drawerText1);
        drawerLayout_text2= (TextView) findViewById(R.id.drawerText2);
        drawerLayout_touxiang= (ImageView) findViewById(R.id.drawerLayout_touxiang);
        drawerLayout_name= (TextView) findViewById(R.id.drawerLayout_Login_name);
    }

    private void toobar() {
        toobar.setTitle("");
        setSupportActionBar(toobar);
    }


    /**
     * 添加管理fragment
     */
    private void fragmentManager() {
        fragment_music = new Fragment_music();
        fragment_music2 = new Fragment_music2();
        testFragment2 = new TestFragment2();
        list.add(fragment_music);
        list.add(fragment_music2);
        list.add(testFragment2);
        fragmentAdapter = new FragmentPagerAdapter(this.getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return list.get(position);
            }

            @Override
            public int getCount() {
                return list.size();
            }
        };
    }


    private void viewPager() {
        viewPager.setCurrentItem(0);
        viewPager.setAdapter(fragmentAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                resetButton();
                switch (position) {
                    case 0:
                        actionbar_discover.setBackgroundResource(R.mipmap.actionbar_discover_selected);
                        break;
                    case 1:
                        actionbar_music.setBackgroundResource(R.mipmap.actionbar_music_selected);
                        break;
                    case 2:
                        actionbar_friends.setBackgroundResource(R.mipmap.actionbar_friends_selected);
                        break;

                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {


            }
        });
    }

    private void resetButton() {
        actionbar_discover.setBackgroundResource(R.mipmap.actionbar_discover_normal);
        actionbar_music.setBackgroundResource(R.mipmap.actionbar_music_normal);
        actionbar_friends.setBackgroundResource(R.mipmap.actionbar_friends_normal);
    }


    private void drawerLayout() {
        mActionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toobar, R.string.open, R.string.close);
        mActionBarDrawerToggle.syncState();
        drawerLayout.setDrawerListener(mActionBarDrawerToggle);
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mActionBarDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void onPostCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onPostCreate(savedInstanceState, persistentState);
        mActionBarDrawerToggle.syncState();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toobar, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_Search:
                Toast.makeText(this, "搜索", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onRestart() {
              isLogin();
        super.onRestart();
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            //登录
            case R.id.drawerLayout_login_button:
                if(app.isLogin(MainActivity.this)) {
                    intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);

                }
           break;
            //登出
            case R.id.drawerLayout_Logout:
                    new SharedPreferencesHelper(MainActivity.this).remove("isLogin");
                Toast.makeText(MainActivity.this,"已经移除isLogin",Toast.LENGTH_LONG).show();
                break;
            //头像
            case R.id.drawerLayout_touxiang:
                 intent= new Intent(MainActivity.this,PersonalActivity.class);
                startActivity(intent);
                break;
        }

    }

   /* 登录状态*/
    private void isLogin() {
        if(!app.isLogin(MainActivity.this)){
            drawerLayout_login_button.setVisibility(View.GONE);
            drawerLayout_bg.setBackgroundResource(R.mipmap.dlogin);
            drawerLayout_text1.setVisibility(View.GONE);
            drawerLayout_text2.setVisibility(View.GONE);
            drawerLayout_name.setVisibility(View.VISIBLE);
            drawerLayout_touxiang.setVisibility(View.VISIBLE);
            //设置值
            //设置姓名
            qq_user qqUser=  app.getSqlLiteData(new SharedPreferencesHelper(MainActivity.this).get("isLogin","").toString());
            drawerLayout_name.setText(qqUser.getNickname());
            //设置头像
            Log.i("头像",qqUser.getBitmap());
               drawerLayout_touxiang.setImageBitmap(new Utils().setTouxiang(qqUser.getBitmap()));
        }else {
            drawerLayout_login_button.setVisibility(View.VISIBLE);
            drawerLayout_bg.setBackgroundResource(R.mipmap.drawerlayout_bg);
            drawerLayout_text1.setVisibility(View.VISIBLE);
            drawerLayout_text2.setVisibility(View.VISIBLE);
            drawerLayout_touxiang.setVisibility(View.GONE);
            drawerLayout_name.setVisibility(View.GONE);
        }

    }
}
