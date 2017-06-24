package zzy.com.QQ_Login;

import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.tencent.tauth.IUiListener;
import com.tencent.tauth.UiError;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import main.zzy.com.yun.LoginActivity;
import zzy.com.Application.XutilsApplication;
import zzy.com.Manager.Utils;

/**
 * Created by Administrator on 2017/3/30.
 */

public class QQUserInfoUiListener implements IUiListener {
    public QQUserInfoUiListener() {

    }
    @Override
    public void onComplete(Object response) {
        if(response == null){
            return;
        }

                    Log.i(response+"","0--------获取到的值");


    }

    @Override
    public void onError(UiError uiError) {

    }

    @Override
    public void onCancel() {

    }
}
