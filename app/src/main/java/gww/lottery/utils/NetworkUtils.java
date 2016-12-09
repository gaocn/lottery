package gww.lottery.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.format.Formatter;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import static android.util.Log.d;

/**
 * Created by 高文文 on 2016/11/7.
 */

public class NetworkUtils {
    private static final String TAG = "NetworkUtils";

    private static Context mContext;

    public static boolean isWiFiConnected(Context concext) {
        ConnectivityManager cm = (ConnectivityManager)concext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfos = cm.getActiveNetworkInfo();
        if(netInfos == null || !netInfos.isAvailable()) {
            //network disabled
            return false;
        } else if(netInfos.getType() == ConnectivityManager.TYPE_WIFI) {
            return netInfos.isConnected();
        } else {
            //采用mobile 网络
            return false;
        }
    }

    public  static boolean isConnectedToInternet(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if(cm != null) {
            NetworkInfo netInfos = cm.getActiveNetworkInfo();
            Log.d(TAG, "isConnectedToInternet: 网络连接状态 " + netInfos.getState());
            return netInfos.getState() == NetworkInfo.State.CONNECTED;
        }
        return false;
    }

    public static  boolean isConnectedToInternet() {
        if(mContext == null) {
            d(TAG, "isConnectedToInternet: Context为空， 请调用带参数的重载方法");
            return false;
        }
        return isConnectedToInternet(mContext);
    }

    /**
     * 获取正在运行进程数量
     * PackageManager 相当于程序管理器，即控件面板中显示的安装软件，管理的是静态信息
     * ActivityManager 管理手机中运行的应用程序信息，管理的是动态信息
     * @param context
     * @return
     */
    public static int getRunningProcessCount(Context context) {
       ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        int count = am.getRunningAppProcesses().size();
        d(TAG, "getRunningProcessCount: 运行进程数量" + count);
        return count;
    }

    /**
     * 获取手机可用剩余内存
     * @param context
     * @return
     */
    public static long getAvailMem(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
        am.getMemoryInfo(memoryInfo);
        d(TAG, "getAvailMem: 可用内存" + Formatter.formatFileSize(context, memoryInfo.availMem));
        return memoryInfo.availMem;
    }

    /**
     * 获取手机全部可用空间，只能在高版本的API上使用(Android4.00以上)，可能存在不兼容问题
     * @param context
     * @return
     */
    public static long getTotalMemWithHighAPI(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
        am.getMemoryInfo(memoryInfo);
        return memoryInfo.totalMem;
    }

    /**
     * 获取手机全部可用空间，解决兼容问题
     * @param context
     * @return
     */
    public static long getTotalMem(Context context) {
        try {
            File file = new File("/proc/meminfo");
            FileInputStream fis = new FileInputStream(file);
            BufferedReader br = new BufferedReader(new InputStreamReader(fis));
            String line = br.readLine();
            StringBuilder sb = new StringBuilder();
            for(char c : line.toCharArray()) {
                if(c >= '0' && c <= '9')
                    sb.append(c);
            }
            long totalMem = Long.parseLong(sb.toString()) * 1024;
            d(TAG, "getTotalMem: 总内存" + Formatter.formatFileSize(context, totalMem));
            return totalMem;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
