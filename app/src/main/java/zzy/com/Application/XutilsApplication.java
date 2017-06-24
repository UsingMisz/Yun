package zzy.com.Application;

import android.app.Application;
import android.content.Context;

import org.xutils.DbManager;
import org.xutils.db.Selector;
import org.xutils.ex.DbException;
import org.xutils.x;

import zzy.com.Bean.qq_user;
import zzy.com.Manager.SharedPreferencesHelper;

/**
 * Created by Administrator on 2017/3/31.
 */

public class XutilsApplication extends Application {
    private DbManager .DaoConfig daoConfig;
    private DbManager db;


/*返回db*/
    public DbManager getDb() {
        return db;
    }


  /*  判断sqlLite是否有数据 有数据返回false 没有数据返回true*/
   public boolean IsData(String openId){
       try {
        if(db.selector(qq_user.class).where("id","=",openId).count()!=0){
            return false;
        }
       } catch (DbException e) {
           e.printStackTrace();
           return true;
       }
       return true;
   }

    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
        daoConfig =new DbManager.DaoConfig()
                .setDbName("qq_user")
                .setDbVersion(1)
                .setDbUpgradeListener(new DbManager.DbUpgradeListener() {
                    @Override
                    public void onUpgrade(DbManager dbManager, int i, int i1) {
                        //更新数据库

                    }
                });
        db=x.getDb(daoConfig);
    }

    /**
     * 判断数据库里有没有数据 有数据return false 没有数据 return true
     * @param openId
     * @return
     */
    public boolean IsBaseData(String openId){
        return true;
    }


//判断是否登录过
    public boolean isLogin(Context context){
        return !new SharedPreferencesHelper(context).contains("isLogin");
    }

    //通过openId 得到本机数据库里的值
    public qq_user getSqlLiteData(String openId) {
        Selector<qq_user>qqUser1;
        qq_user qqUser = null;
        try {
            qqUser1 = db.selector(qq_user.class).where("id","=",openId);
            qqUser= qqUser1.findFirst();
        } catch (DbException e) {
            e.printStackTrace();
        }
        return qqUser;
    }
}
