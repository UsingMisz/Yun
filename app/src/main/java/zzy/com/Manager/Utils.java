package zzy.com.Manager;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import zzy.com.Bean.qq_user;

import static android.content.ContentValues.TAG;

/**
 * Created by Administrator on 2017/3/30.
 */

public class Utils {
    private static Bitmap bit;
    public  qq_user qq;
    /**
     * 以最省内存的方式读取图片
     */
    public static Bitmap readBitmap(final String path){
        try{
            FileInputStream stream = new FileInputStream(new File(path+"test.jpg"));
            BitmapFactory.Options opts = new BitmapFactory.Options();
            opts.inSampleSize = 8;
            opts.inPurgeable=true;
            opts.inInputShareable=true;
            Bitmap bitmap = BitmapFactory.decodeStream(stream , null, opts);
            return bitmap;
        } catch (OutOfMemoryError e) {
            return null;
        } catch (Exception e) {
            return null;
        }
    }


    /**
     * 根据一个网络连接(String)获取bitmap图像
     *
     * @param imageUri
     * @return
     * @throws
     */
    public static Bitmap getbitmap(String imageUri) {
        Log.v(TAG, "getbitmap:" + imageUri);
        // 显示网络上的图片
        Bitmap bitmap = null;
        try {
            URL myFileUrl = new URL(imageUri);
            HttpURLConnection conn = (HttpURLConnection) myFileUrl
                    .openConnection();
            conn.setDoInput(true);
            conn.connect();
            InputStream is = conn.getInputStream();
            bitmap = BitmapFactory.decodeStream(is);
            is.close();
            Log.v(TAG, "image download finished." + imageUri);
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
            bitmap = null;
        } catch (IOException e) {
            e.printStackTrace();
            Log.v(TAG, "getbitmap bmp fail---");
            bitmap = null;
        }
        return bitmap;
    }

    /**
     * 下载图片
     */
    public void downLoadBitmap(String openId){

    }

    /**
     * 获取头像
     * @param imageUri
     * @return
     */
    public static Bitmap setTouxiang(final String imageUri){
    new AsyncTask<Bitmap, Bitmap, Bitmap>(){
        @Override
        protected Bitmap doInBackground(Bitmap... bitmaps) {
            Bitmap bitmap= getbitmap(imageUri);
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
                bit = getRoundedCornerBitmap(bitmap, bitmap.getWidth() / 2);

            super.onPostExecute(bitmap);
        }
    }.execute();
          return bit;

    }

    public static Bitmap getRoundedCornerBitmap(Bitmap bitmap,float roundPx)
    {
        if(bitmap == null)
            return null;
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        //  final Rect rectd = new Rect(0, 0, bitmap.getWidth()*1.5, bitmap.getHeight()1.5);
        final RectF rectF = new RectF(rect);

        //  canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));

        canvas.drawBitmap(bitmap, rect, rect, paint);

        return output;
    }
    /**
 * 获取qqjson数据转成为map类型
 */

    public qq_user JsonQQ(Object response) {
        if (response != null) {
            JSONObject jsonObject = (JSONObject) response;
            qq =new qq_user();
            try {
                String name = jsonObject.getString("nickname");
                String gender = jsonObject.getString("gender");
                String city = jsonObject.getString("city");
                String bitmap = jsonObject.getString("figureurl_qq_2");
                qq.setBitmap(bitmap);
                qq.setCity(city);
                qq.setNickname(name);
                qq.setSex(gender);
                qq.setAge("");
                return qq;
            } catch (Exception e) {
                Log.i("解析异常", e.toString());
                return qq;
            }
        }
        return null;
    }
}
