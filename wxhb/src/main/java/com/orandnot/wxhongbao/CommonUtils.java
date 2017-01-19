package com.orandnot.wxhongbao;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.os.Vibrator;

import java.util.List;

/**
 * Created by Administrator on 2017/1/14.
 */
public class CommonUtils {

    public static void playMusic(Context context) {
        try {
            AssetManager assetManager = context.getAssets();
            AssetFileDescriptor afd = assetManager.openFd("hbll.aac");
            MediaPlayer player = new MediaPlayer();
            player.setDataSource(afd.getFileDescriptor(),
                    afd.getStartOffset(), afd.getLength());
            player.setLooping(false);//循环播放
            player.prepare();
            player.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void shark(Context context) {
        Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        long[] pattern = {100, 400, 100, 400};   // 停止 开启 停止 开启
        vibrator.vibrate(1000);
    }

    /**
     * 2  * 获取版本号
     * 3  * @return 当前应用的版本号
     * 4
     */
    public static String getVersion(Context context,String packageName) {
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(packageName, 0);
            String version = info.versionName;
            return version;
        } catch (Exception e) {
            e.printStackTrace();
            return "获取微信版本失败";
        }
    }

    public static String getRPId(Context context){
        String[] info= getVXInfo(context);
        return info==null?"":info[0];
    }

    public static String getRPBack(Context context){
        String[] info= getVXInfo(context);
        LogUtils.e("info[1]:"+info[1]);
        return info==null?"":info[1];
    }

    public static String[] getVXInfo(Context context){
        String[] info = null;
        try {
            Resources res=context.getResources();
            String temp = "wx"+getVersion(context,"com.tencent.mm").replace(".","_");
            info  = context.getResources().getStringArray(res.getIdentifier(temp,"array",context.getPackageName()));
            return info;
        } catch (Exception e) {
            return info;
        }
    }

    public static boolean isHaveVersion(Context context){
        return getVXInfo(context)==null?false:true;
    }

    /**
     * 判断某个服务是否正在运行的方法
     *
     * @param mContext
     * @param serviceName
     *            是包名+服务的类名（例如：net.loonggg.testbackstage.TestService）
     * @return true代表正在运行，false代表服务没有正在运行
     */
    public static boolean isServiceWork(Context mContext, String serviceName) {
        boolean isWork = false;
        ActivityManager myAM = (ActivityManager) mContext
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> myList = myAM.getRunningServices(40);
        if (myList.size() <= 0) {
            return false;
        }
        for (int i = 0; i < myList.size(); i++) {
            String mName = myList.get(i).service.getClassName().toString();
            if (mName.equals(serviceName)) {
                isWork = true;
                break;
            }
        }
        return isWork;
    }
}
