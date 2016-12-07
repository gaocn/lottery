package gww.lottery;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.facebook.stetho.Stetho;
import com.litesuits.orm.LiteOrm;

import gww.lottery.config.PropertiesManager;
import gww.lottery.config.SharedPreferenceManager;
import gww.lottery.image.DemoDuiTangImageReSizer;
import gww.lottery.image.PtrImageLoadHandler;
import in.srain.cube.Cube;
import in.srain.cube.image.ImageLoaderFactory;
import in.srain.cube.request.RequestCacheManager;
import in.srain.cube.util.CLog;
import in.srain.cube.util.CubeDebug;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * Created by 高文文 on 2016/12/1.
 */

public class App extends Application {
    private static final String TAG = "gww.lottery.App";
    private static final String DB_NAME = "lattery.db";

    public Context mContext;
    public LiteOrm liteOrm;

    private void initLiteOrm() {
        Log.d(TAG, "initLiteOrm: 初始化数据库");
        if(liteOrm == null)
           liteOrm = LiteOrm.newSingleInstance(this, DB_NAME);
        liteOrm.setDebugged(true);
    }
    @Override
    public void onCreate() {
        Log.d(TAG, "onCreate: 应用程序上下文初始化");
        mContext = this;
        initLiteOrm();
        PropertiesManager.getInstance().initialize(this);
        SharedPreferenceManager.getInstance().initialize(this, "lottery", Context.MODE_PRIVATE);
        BeanFactory.beanFactoryInit();
        /**
         * 开启网络请求查看: new OkHttpClient.Builder().addNetworkInterceptor(new StethoInterceptor()) .build()
         */
        Log.d(TAG, "onCreate: 初始化Stetho，可以通过Chrom查看应用布局，网络请求，sqlite，sharepreference");
        Stetho.initializeWithDefaults(this);
        LotteryRetrofit.init();


        /**
         * Cube初始化
         */
        Log.d(TAG, "onCreate: 初始化Cube");
        String environment = "";

        if (environment.equals("production")) {
            CLog.setLogLevel(CLog.LEVEL_ERROR);
        } else if (environment.equals("beta")) {
            CLog.setLogLevel(CLog.LEVEL_WARNING);
        } else {
            // development
            CLog.setLogLevel(CLog.LEVEL_VERBOSE);
        }

        CubeDebug.DEBUG_IMAGE = true;
        PtrFrameLayout.DEBUG = true;
        PtrFrameLayout.DEBUG = false;

        ImageLoaderFactory.setDefaultImageReSizer(DemoDuiTangImageReSizer.getInstance());
        ImageLoaderFactory.setDefaultImageLoadHandler(new PtrImageLoadHandler());
        String dir = "request-cache";
        // ImageLoaderFactory.init(this);
        RequestCacheManager.init(this, dir, 1024 * 10, 1024 * 10);
        Cube.onCreate(this);

        super.onCreate();
    }
}
