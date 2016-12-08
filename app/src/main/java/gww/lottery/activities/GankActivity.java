package gww.lottery.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;
import gww.lottery.LotteryRetrofit;
import gww.lottery.R;
import gww.lottery.activities.base.BaseActivity;
import gww.lottery.data.GankData;
import gww.lottery.data.MeiZhiData;
import gww.lottery.data.RespiteData;
import gww.lottery.restful.service.GankApi;
import gww.lottery.ui.TitleBar;
import in.srain.cube.image.CubeImageView;
import in.srain.cube.image.ImageLoader;
import in.srain.cube.image.ImageLoaderFactory;
import in.srain.cube.request.JsonData;
import in.srain.cube.views.list.ListViewDataAdapter;
import in.srain.cube.views.list.ViewHolderBase;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
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
    PtrClassicFrameLayout ptrFrame;
    @BindView(R.id.rotate_header_list_view)
    ListView mListView;
    @BindView(R.id.btn_web_view)
    Button mBtnWebView;
    /**
     * Cube中用于加载图片
     */
    private ImageLoader mImageLoader;
    private ListViewDataAdapter<JsonData> mAdapter;

    private int mPage = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gank);
        ButterKnife.bind(this);

        mImageLoader = ImageLoaderFactory.create(getApplicationContext());
        mTitleBar.setListener(new TitleBar.OnEventClickListener() {
            @Override
            public void onBack(View view) {
                finish();
            }

            @Override
            public void onOperation(View view) {

            }
        });

        ptrFrame.setLoadingMinTime(3000);
        ptrFrame.postDelayed(new Runnable() {
            @Override
            public void run() {
                ptrFrame.autoRefresh(true);
            }
        }, 150);
        ptrFrame.setLastUpdateTimeRelateObject(this);
        ptrFrame.setPtrHandler(new PtrHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                updateData();
            }

            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }
        });

        mAdapter = new ListViewDataAdapter<>();
        mAdapter.setViewHolderClass(this, ViewHolder.class);
        mListView.setAdapter(mAdapter);

    }

    @OnClick(R.id.btn_web_view)
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_web_view:
                Intent intent = new Intent(this, WebViewActivity.class);
                startActivity(intent);
                break;
        }
    }

    @OnItemClick(R.id.rotate_header_list_view)
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if(position >= 0) {
            final String url = mAdapter.getItem(position).optString("pic");
            if(!TextUtils.isEmpty(url)) {
                //跳到另一个界面展示图片
                Toast.makeText(this, "跳到另一个界面展示 + " + url, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), RentalsStyleImageActivity.class);
                intent.putExtra("url", url);
                startActivity(intent);
            }
        }
    }

    protected void updateData() {

        GankApi gank = LotteryRetrofit.retrofit.create(GankApi.class);
        Call<MeiZhiData> call = gank.getMeiZhiData(mPage);
        call.enqueue(new Callback<MeiZhiData>() {
            @Override
            public void onResponse(Call<MeiZhiData> call, final Response<MeiZhiData> response) {
                final MeiZhiData data = response.body();
                Log.d(TAG, "onResponse: "
                        + response.body());
                ptrFrame.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mAdapter.getDataList().clear();

                        JsonData jsonData = JsonData.create(new Gson().toJson(data));
                        Log.d(TAG, "run: jsonData " + jsonData.optString("error"));
                        mAdapter.getDataList().addAll(jsonData.optJson("results").toArrayList());
                        ptrFrame.refreshComplete();
                        mAdapter.notifyDataSetChanged();
                    }
                }, 0);
            }

            @Override
            public void onFailure(Call<MeiZhiData> call, Throwable t) {
                t.printStackTrace();
            }
        });


    }

    private class ViewHolder extends ViewHolderBase<JsonData> {
        private CubeImageView mImageView;

        @Override
        public View createView(LayoutInflater inflater) {
            View v = inflater.inflate(R.layout.list_view_meizhi_item, null);
            mImageView = (CubeImageView) v.findViewById(R.id.list_view_item_image_view);
            mImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            return v;
        }

        @Override
        public void showData(int position, JsonData itemData) {
            Log.d(TAG, "showData: itemData" + itemData.optString("url") + " " + itemData);
            mImageView.loadImage(mImageLoader, itemData.optString("url"));
        }
    }

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


    /**
     * 双击后退出
     */

    long[] hits = new long[2];
    private boolean doubleClicked() {
        System.arraycopy(hits, 1, hits, 0, hits.length - 1);
        hits[hits.length - 1] = SystemClock.uptimeMillis();
        if(SystemClock.uptimeMillis() - hits[0] < 1000) {
            return true;
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        if(doubleClicked()) {
            finish();
        } else {
            Toast.makeText(this, "再一次单击后退出应用", Toast.LENGTH_SHORT).show();
        }
//        super.onBackPressed();
    }
}
