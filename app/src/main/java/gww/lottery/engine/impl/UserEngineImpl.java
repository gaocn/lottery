package gww.lottery.engine.impl;

import android.util.Log;

import com.google.gson.Gson;

import gww.lottery.LotteryRetrofit;
import gww.lottery.engine.BaseEngine;
import gww.lottery.engine.UserEngine;
import gww.lottery.network.protocol.Message;
import gww.lottery.network.protocol.element.UserLoginElement;
import gww.lottery.restful.service.UserLoginService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

/**
 * Created by 高文文 on 2016/12/5.
 */

public class UserEngineImpl extends BaseEngine implements UserEngine {
    @Override
    public String login(String user) {

        //构造请求报文
        UserLoginElement element = new UserLoginElement();
        element.setPasswd("我是密码");
        final Message request = new Message(element);
        request.getHeader().setUserName("我是用户名");

        Log.d(TAG, "login: 请求报文 " + request.getRequest());

        final Gson gson = new Gson();
        /**
         * 服务端解析步骤
         *         String decodedBody = new DES().authcode(request.getEncBody(), "ENCODE", LotteryConstants.DES_PASSWORD);
         *         UserLoginElement resp = gson.fromJson(decodedBody, UserLoginElement.class);
         *         Log.d(TAG, "login: resp " + resp);
         */

        UserLoginService loginService = LotteryRetrofit.retrofit.create(UserLoginService.class);
//        RequestBody requestBoody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), request.getRequest());

        retrofit2.Call<String> call = loginService.login();



//        Log.d(TAG, "login: 请求url ");

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                Log.d(TAG, "login: 接收报文 " + response.body());

                /*
                 * 客户端解析响应报文
                Message msg = gson.fromJson(response.body(), Message.class);
                Log.d(TAG, "login: 接收报文 " + msg);
                if(isPackageValid(msg)) {
                    BaseResponseElement resp1 = gson.fromJson("{\"retCode\":\"0000\",\"retMsg\":\"SUCCESS\"}", BaseResponseElement.class);
                    Log.d(TAG, "login: resp " + resp1);
                }*/
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.d(TAG, "onFailure: 服务端无响应 ");
                t.printStackTrace();
            }
        });
        /**
         * 客户端解析响应报文
         */
/*        Message msg = gson.fromJson(request.getRequest(), Message.class);
        Log.d(TAG, "login: 接收报文 " + msg);
        if(isPackageValid(msg)) {
            BaseResponseElement resp1 = gson.fromJson("{\"retCode\":\"0000\",\"retMsg\":\"SUCCESS\"}", BaseResponseElement.class);
            Log.d(TAG, "login: resp " + resp1);
        }*/

        return "HaHa";
    }
}
