package main.zzy.com.yun;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import zzy.com.Application.XutilsApplication;
import zzy.com.Bean.qq_user;
import zzy.com.Manager.SharedPreferencesHelper;
import zzy.com.Manager.Utils;
import zzy.com.zidingyi.ui.ParallaxScollListView;

/**
 * Created by Administrator on 2017/4/4.
 */

public class PersonalActivity extends AppCompatActivity{
    private ParallaxScollListView mListView;
    private ImageView mImageView,mTouxiang;
    private TextView name;
    private XutilsApplication app;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal);
        app=(XutilsApplication) getApplication();
        init();
        initTouxiang();

    }



    private void init() {
        mListView = (ParallaxScollListView) findViewById(R.id.parallaxListView);
        View header = LayoutInflater.from(this).inflate(R.layout.activity_listview_header, null);
        mImageView = (ImageView) header.findViewById(R.id.layout_header_image);

        mListView.setZoomRatio(ParallaxScollListView.NO_ZOOM);
        mListView.setParallaxImageView(mImageView);
        mListView.addHeaderView(header);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_expandable_list_item_1,
                new String[]{

                }
        );
        mListView.setAdapter(adapter);

    }
    /**
     * 头像应该之前下载好读的
     */
    private void initTouxiang() {
        name= (TextView) findViewById(R.id.activity_header_textview);
        qq_user qqUser=  app.getSqlLiteData(new SharedPreferencesHelper(PersonalActivity.this).get("isLogin","").toString());
        mTouxiang= (ImageView) findViewById(R.id.activity_header_touxiang);
        mTouxiang.setImageBitmap(new Utils().setTouxiang(qqUser.getBitmap()));
        name.setText(qqUser.getNickname());

    }


}
