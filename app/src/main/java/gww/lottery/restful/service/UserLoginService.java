package gww.lottery.restful.service;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by 高文文 on 2016/12/5.
 */

public interface UserLoginService {
    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST("login.json")
    Call<String> login(@Body RequestBody request);

    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST("login.json")
    Observable<String> rxLogin(@Body RequestBody request);

    @GET("login.json")
    Call<String> login();
}
