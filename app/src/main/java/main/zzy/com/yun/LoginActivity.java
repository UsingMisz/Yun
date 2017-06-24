package main.zzy.com.yun;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.tencent.connect.UserInfo;
import com.tencent.connect.common.Constants;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.DbManager;
import org.xutils.ex.DbException;

import java.util.HashMap;
import java.util.Map;

import zzy.com.Application.XutilsApplication;
import zzy.com.Bean.qq_user;
import zzy.com.Manager.AppConstants;
import zzy.com.Manager.SharedPreferencesHelper;
import zzy.com.Manager.Utils;
import zzy.com.QQ_Login.QQUserInfoUiListener;

/**
 * Created by Administrator on 2017/3/28.
 */

public class LoginActivity extends AppCompatActivity {
    private Tencent mTencent;
    private IUiListener initListener;
    private IUiListener QQInfo;
    private XutilsApplication app ;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        app = (XutilsApplication)getApplication();
        initQQ();
        UserInfoQQ();
    }




    /**
     * 登录qq的初始化
     */
    private void initQQ() {
         initListener =new IUiListener() {
            @Override
            public void onComplete(Object o) {
                JSONObject jo = (JSONObject) o;
                String openID;
                try {
                    openID = jo.getString("openid");
                    String accessToken = jo.getString("access_token");
                    String expires = jo.getString("expires_in");
                    mTencent.setOpenId(openID);
                    mTencent.setAccessToken(accessToken, expires);
                    UserInfo();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onError(UiError uiError) {
            }
            @Override
            public void onCancel() {
            }
        };
    }


   /*
   获取qq数据,sp存储 1.先判断数据库中有没有一样的数据 2.没有就从数据库中找 3.最后没有在存
   */
    private void UserInfoQQ() {
        QQInfo=new IUiListener(){
            @Override
            public void onComplete(Object response) {
               qq_user qq=new Utils().JsonQQ(response);
                qq.setId(mTencent.getOpenId());
                try {
                    if (app.IsData(mTencent.getOpenId())&&app.IsBaseData(mTencent.getOpenId())) {
                        //存入数据库
                        app.getDb().saveOrUpdate(qq);
                        //存入sp
                        new SharedPreferencesHelper(LoginActivity.this).put("isLogin", mTencent.getOpenId());
                        Log.i("是否成功存入","出现的话就是已经顺利存了");
                        finish();
                        //这里应该使用子线程更新ui的方法而不是直接给finish掉

                    }else{
                        //数据库中已经存在该类数据
                        new SharedPreferencesHelper(LoginActivity.this).put("isLogin", mTencent.getOpenId());
                        finish();
                    }
                } catch (DbException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onError(UiError uiError) {
            }
            @Override
            public void onCancel() {
            }
        };
    }




    //点击登录QQ
    public void qq_login(View view) {
            mTencent=Tencent.createInstance(AppConstants.APP_ID,this.getApplicationContext());
        if (!mTencent.isSessionValid())
        {
            //这个Scope是什么？
            mTencent.login(this, "all",initListener);
        }

    }






    //获取qq信息
    public void UserInfo(){
         if(mTencent!=null&&mTencent.isSessionValid()){
             UserInfo info = new UserInfo(this, mTencent.getQQToken());
             info.getUserInfo(QQInfo);

         }

    }




    @Override
    protected void onDestroy() {
      /*  mTencent.logout(this);*/
        super.onDestroy();
    }







//回调
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
       if(requestCode== Constants.REQUEST_LOGIN) {
            if (resultCode == -1) {
                Tencent.onActivityResultData(requestCode, resultCode, data, initListener);
                Tencent.handleResultData(data, initListener);
                super.onActivityResult(requestCode, resultCode, data);
            }
        }
    }



}
