package gww.lottery;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by 高文文 on 2016/12/2.
 */

public class LotteryRetrofit {
    private static final String TAG = "LotteryRetrofit";
    private Retrofit retrofit;

    private final Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
            .serializeNulls()
            .excludeFieldsWithoutExposeAnnotation()
            .create();

     public LotteryRetrofit(String url) {



          retrofit = new Retrofit.Builder()
                 .baseUrl(url)
//                .client(client)
                 .addConverterFactory(GsonConverterFactory.create(gson))
                 .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                 .build();
    }
}
