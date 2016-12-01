package gww.lottery.restful.service;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by 高文文 on 2016/12/1.
 */
//http://reactnative.cn/docs/0.38/getting-started.html
public interface TestService {
    @GET("docs/0.38/{article}")
    Call<String> getContent(@Path("article") String article);

    @GET("docs/0.38/{article}")
    Observable<String> getContentRx(@Path("article") String article);
}
