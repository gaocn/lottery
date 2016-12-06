package gww.lottery.restful.service;


import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by 高文文 on 2016/12/6.
 */

public interface UpdateService {
    @GET("updateinfo.json")
    public Call<String> getApkVersion();

}
