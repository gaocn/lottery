package gww.lottery.engine.impl;

import android.util.Log;

import gww.lottery.LotteryRetrofit;
import gww.lottery.engine.UpdateEngine;
import gww.lottery.restful.service.UpdateService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

/**
 * Created by 高文文 on 2016/12/6.
 */

public class UpdateEngineImpl implements UpdateEngine {
    @Override
    public void checkUpdate() {
        Log.d(TAG, "checkUpdate: 进行软件升级相关操作");
        UpdateService updateService = LotteryRetrofit.retrofit.create(UpdateService.class);
        Call<String> call = updateService.getApkVersion();
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.d(TAG, "onResponse: 响应码 " + response.code());
                Log.d(TAG, "onResponse: 响应结果为 " + response.body());

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.d(TAG, "onFailure: 服务端无响应");
                t.printStackTrace();
            }
        });

    }
}
