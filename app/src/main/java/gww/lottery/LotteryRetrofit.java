package gww.lottery;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import gww.lottery.config.PropertiesManager;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by 高文文 on 2016/12/2.
 */

public class LotteryRetrofit {
    private static final String TAG = "LotteryRetrofit";
    public static Retrofit retrofit;


    private static final Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
            .serializeNulls()
            .excludeFieldsWithoutExposeAnnotation()
            .create();

    public static void init() {
        String URL = PropertiesManager.getInstance().getValue("url");

        //添加输出日志
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        Log.d(TAG, "init: URL " + URL);
        retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .client(client)
                //增加返回值为String的支持
                .addConverterFactory(ScalarsConverterFactory.create())
                //增加返回值为JSON时转换相应JOPO对象的支持
                .addConverterFactory(GsonConverterFactory.create(gson))
                //增加使用rxJava的支持
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }

}
