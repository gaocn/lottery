package gww.lottery.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import butterknife.BindView;
import butterknife.ButterKnife;
import gww.lottery.LotteryRetrofit;
import gww.lottery.R;
import gww.lottery.activities.base.BaseActivity;
import gww.lottery.data.GankData;
import gww.lottery.data.MeiZhiData;
import gww.lottery.data.RespiteData;
import gww.lottery.restful.service.GankApi;
import gww.lottery.ui.TitleBar;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GankActivity extends BaseActivity {
    private static final String TAG = "GankActivity";

    @BindView(R.id.title_bar)
    TitleBar mTitleBar;
    @BindView(R.id.store_house_ptr_frame)
    PtrFrameLayout ptrFrame;
/*    @BindView(R.id.btn_gank)
    Button mBtnGank;*/


    private int mPage = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gank);
        ButterKnife.bind(this);
        mTitleBar.setListener(new TitleBar.OnEventClickListener() {
            @Override
            public void onBack(View view) {
                finish();
            }

            @Override
            public void onOperation(View view) {

            }
        });
        ptrFrame.setPtrHandler(new PtrHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                frame.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ptrFrame.refreshComplete();
                    }
                }, 1800);
            }

            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }
        });


    }

 /*   @OnClick({R.id.btn_gank})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_gank:
                break;
        }
    }*/

    private void getGank() {
        GankApi gank = LotteryRetrofit.retrofit.create(GankApi.class);
        Call<MeiZhiData> call = gank.getMeiZhiData(mPage);
        call.enqueue(new Callback<MeiZhiData>() {
            @Override
            public void onResponse(Call<MeiZhiData> call, Response<MeiZhiData> response) {
                MeiZhiData data = response.body();
                Log.d(TAG, "onResponse: "
                        + response.body());
            }

            @Override
            public void onFailure(Call<MeiZhiData> call, Throwable t) {
                t.printStackTrace();
            }
        });

        Call<RespiteData> respiteDataCall = gank.getRespiteData(mPage);
        respiteDataCall.enqueue(new Callback<RespiteData>() {
            @Override
            public void onResponse(Call<RespiteData> call, Response<RespiteData> response) {
                RespiteData data = response.body();
                Log.d(TAG, "onResponse: "
                        + response.body());
            }

            @Override
            public void onFailure(Call<RespiteData> call, Throwable t) {
                t.printStackTrace();
            }
        });
        Call<GankData> gankDataCall = gank.getGankData(2016, 12, 5);
        gankDataCall.enqueue(new Callback<GankData>() {
            @Override
            public void onResponse(Call<GankData> call, Response<GankData> response) {
                GankData data = response.body();
                Log.d(TAG, "onResponse: "
                        + response.body());
            }

            @Override
            public void onFailure(Call<GankData> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}
