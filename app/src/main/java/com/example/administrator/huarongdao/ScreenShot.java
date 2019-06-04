package com.example.administrator.huarongdao;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ScreenShot {

    // 获取指定Activity的截屏，保存到png文件
    static String newFilePath;
    private static Bitmap takeScreenShot(Activity activity) {
        // View是你需要截图的View
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap b1 = view.getDrawingCache();

        // 获取状态栏高度
        Rect frame = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        int statusBarHeight = frame.top;
        Log.i("TAG", "" + statusBarHeight);

        // 获取屏幕长和高
        int width = activity.getWindowManager().getDefaultDisplay().getWidth();
        int height = activity.getWindowManager().getDefaultDisplay()
                .getHeight();
        // 去掉标题栏
        // Bitmap b = Bitmap.createBitmap(b1, 0, 25, 320, 455);
        Bitmap b = Bitmap.createBitmap(b1, 0, statusBarHeight, width, height
                - statusBarHeight);
        view.destroyDrawingCache();
        return b;
    }

    // 保存到sdcard
    private static void savePic(Bitmap b, String strFileName) {
        FileOutputStream fos = null;
        try {
            //判断是否存在该文件夹和文件，如果没有新建
            File file = new File(strFileName);
            //获取父目录
            File fileParent = file.getParentFile();

            //判断是否存在
            if (!fileParent.exists()) {
//                创建父目录文件
                fileParent.mkdirs();
            }
            //权限问题
            file.createNewFile();
            Log.d("wcy", file.toString());
            fos = new FileOutputStream(new File(strFileName));
            if (null != fos) {
                b.compress(Bitmap.CompressFormat.PNG, 90, fos);
                fos.flush();
                fos.close();
            }
        } catch (FileNotFoundException e) {
            Log.d("wcy", e.toString());
        } catch (IOException e) {
            Log.d("wcy", e.toString());
        }
    }

    //用于获取当前最新的截图
    public static String getPath(){
        return newFilePath;
    }
    // 截屏方法
    public  boolean shoot(Activity a) {
        Date date = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String fileName = df.format(date)+".png";
        String sdCardPath = Environment.getExternalStorageDirectory().getPath();
        String filePath = sdCardPath + File.separator + "华容道" + File.separator + "Screenshoots"+ File.separator + fileName;
        newFilePath = filePath;
        try{
            ScreenShot.savePic(ScreenShot.takeScreenShot(a), filePath);
            return true;
        }catch (Exception e){
            return false;
        }

    }



}
