package gww.lottery.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.gson.Gson;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemClick;
import gww.lottery.LotteryRetrofit;
import gww.lottery.R;
import gww.lottery.activities.RentalsStyleImageActivity;
import gww.lottery.data.MeiZhiData;
import gww.lottery.restful.service.GankApi;
import in.srain.cube.image.CubeImageView;
import in.srain.cube.image.ImageLoader;
import in.srain.cube.image.ImageLoaderFactory;
import in.srain.cube.request.JsonData;
import in.srain.cube.util.LocalDisplay;
import in.srain.cube.views.list.ListViewDataAdapter;
import in.srain.cube.views.list.ViewHolderBase;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by 高文文 on 2016/12/8.
 */

public class FuLiFragment extends Fragment {
    private static final String TAG = "FuLiFragment";
    @BindView(R.id.store_house_ptr_frame)
    PtrClassicFrameLayout ptrFrame;
    @BindView(R.id.rotate_header_grid_view)
    GridView mGridView;
    private static final int sGirdImageSize = (LocalDisplay.SCREEN_WIDTH_PIXELS - LocalDisplay.dp2px(12 + 12 + 10)) / 2;
    /**
     * Cube中用于加载图片
     */
    private ImageLoader mImageLoader;
    private ListViewDataAdapter<JsonData> mAdapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fuli, null);
        ButterKnife.bind(this, view);
        mImageLoader = ImageLoaderFactory.create(getContext());
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
        mGridView.setAdapter(mAdapter);

        return view;
    }

    @OnItemClick(R.id.rotate_header_grid_view)
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if(position >= 0) {
            final String url = mAdapter.getItem(position).optString("url");
            if(!TextUtils.isEmpty(url)) {
                //跳到另一个界面展示图片
//                Toast.makeText(getContext(), "跳到另一个界面展示 + " + url, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getContext(), RentalsStyleImageActivity.class);
                intent.putExtra("url", url);
                startActivity(intent);
            }
        }
    }

    /**
     * 每次刷新获取不同页面中的数据，操作5页后请求缓存ListView
     */
    private int mPage;
    protected void updateData() {
        mPage++;
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
                        if(mPage > 5) {
                            mAdapter.getDataList().clear();
                            mPage = 0;
                        }
                        JsonData jsonData = JsonData.create(new Gson().toJson(data));
//                        Log.d(TAG, "run: jsonData " + jsonData.optString("error"));
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
            mImageView = (CubeImageView) v.findViewById(R.id.with_grid_view_item_image);
            mImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            LinearLayout.LayoutParams lyp = new LinearLayout.LayoutParams(sGirdImageSize, sGirdImageSize);
            mImageView.setLayoutParams(lyp);
            return v;
        }

        @Override
        public void showData(int position, JsonData itemData) {
//            Log.d(TAG, "showData: itemData" + itemData.optString("url") + " " + itemData);
            mImageView.loadImage(mImageLoader, itemData.optString("url"));
        }
    }
}
