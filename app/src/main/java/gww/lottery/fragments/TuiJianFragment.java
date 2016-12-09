package gww.lottery.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.litesuits.orm.db.model.ConflictAlgorithm;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemClick;
import gww.lottery.App;
import gww.lottery.LotteryRetrofit;
import gww.lottery.R;
import gww.lottery.activities.WebViewActivity;
import gww.lottery.data.GankData;
import gww.lottery.data.entity.Gank;
import gww.lottery.restful.service.GankApi;
import gww.lottery.ui.LinkShareBar;
import gww.lottery.utils.NetworkUtils;
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

import static gww.lottery.App.liteOrm;

/**
 * Created by 高文文 on 2016/12/8.
 */

public class TuiJianFragment extends BaseFragment {
    private static final String TAG = "TuiJianFragment";
    @BindView(R.id.store_house_ptr_frame)
    PtrClassicFrameLayout ptrFrame;
    @BindView(R.id.rotate_header_list_view)
    ListView mListView;
    /**
     * Cube中用于加载图片
     */
    private ImageLoader mImageLoader;
    private ListViewDataAdapter<JsonData> mAdapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tuijian, null);
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
        mListView.setAdapter(mAdapter);

        return view;
    }

    @OnItemClick(R.id.rotate_header_list_view)
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if(position >= 0) {
            final String url = mAdapter.getItem(position).optString("url");
            if(!TextUtils.isEmpty(url)) {
                //跳到另一个界面展示图片
                Intent intent = new Intent(getContext(), WebViewActivity.class);
                intent.putExtra("url", url);
                startActivity(intent);
            }
        }
    }

    /**
     * 每日推荐数据
     */
    private GankData gankData;

    protected void updateData() {
        if(NetworkUtils.isWiFiConnected(getContext())) {
            Log.d(TAG, "updateData: 使用WiFi联网");
            Calendar calendae = Calendar.getInstance(Locale.CHINA);
            int year = calendae.get(Calendar.YEAR);
            int month = calendae.get(Calendar.MONTH) + 1;
            int day = calendae.get(Calendar.DATE);
            Log.d(TAG, "updateData: 推荐今天 "  + year + "年" + month + "月" + day + "日的数据");
            final GankApi gank = LotteryRetrofit.retrofit.create(GankApi.class);
            Call<GankData> call = gank.getGankData(year, month, day);
            call.enqueue(new Callback<GankData>() {
                @Override
                public void onResponse(Call<GankData> call, final Response<GankData> response) {
                    Log.d(TAG, "onResponse: " + response.body());
                        gankData = response.body();
                        if(!gankData.category.isEmpty()) {
                            clearAndUpdateDB(gankData);
                            showData();
                    } else {
                        Toast.makeText(getContext(), "今天没有推荐内容，显示昨天的", Toast.LENGTH_SHORT).show();

                    }
                }

                @Override
                public void onFailure(Call<GankData> call, Throwable t) {
                    t.printStackTrace();
                }
            });

        } else {
            Toast.makeText(getContext(), "没有联网", Toast.LENGTH_SHORT).show();
            final ArrayList<Gank> gankArrayList = App.liteOrm.query(Gank.class);
            ptrFrame.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mAdapter.getDataList().clear();
                    JsonData jsonData = JsonData.create(new Gson().toJson(gankArrayList));
                    ArrayList<JsonData> lists = new ArrayList<>();
                    Log.d(TAG, "run: JSONData " + jsonData.toArrayList());
                    mAdapter.getDataList().addAll(jsonData.toArrayList());
                    ptrFrame.refreshComplete();
                    mAdapter.notifyDataSetChanged();
                }
            }, 0);
        }

    }

    private void clearAndUpdateDB(GankData gankData) {
        //清空表中数据，并存放最新一期的数据
        liteOrm.deleteAll(Gank.class);
        try {
            for(Gank gank : gankData.getResults().getAndroidList()) {
                App.liteOrm.insert(gank, ConflictAlgorithm.Replace);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            for(Gank gank : gankData.getResults().getiOSList()) {
                App.liteOrm.insert(gank, ConflictAlgorithm.Replace);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            for(Gank gank : gankData.getResults().get休息视频List()) {
                App.liteOrm.insert(gank, ConflictAlgorithm.Replace);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            for(Gank gank : gankData.getResults().get前端List()) {
                App.liteOrm.insert(gank, ConflictAlgorithm.Replace);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            for(Gank gank : gankData.getResults().get瞎推荐List()) {
                App.liteOrm.insert(gank, ConflictAlgorithm.Replace);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.d(TAG, "clearAndUpdateDB: 删除并且存放最新一期的数据");
    }
    private void showData() {
        ptrFrame.postDelayed(new Runnable() {
            @Override
            public void run() {
                mAdapter.getDataList().clear();
                JsonData jsonData = JsonData.create(new Gson().toJson(gankData));
                ArrayList<JsonData> lists = new ArrayList<>();
                lists.addAll(jsonData.optJson("results").optJson("Android").toArrayList());
                lists.addAll(jsonData.optJson("results").optJson("iOS").toArrayList());
                lists.addAll(jsonData.optJson("results").optJson("前端").toArrayList());
                lists.addAll(jsonData.optJson("results").optJson("瞎推荐").toArrayList());
                lists.addAll(jsonData.optJson("results").optJson("休息视频").toArrayList());
                Log.d(TAG, "run: 总共的数据 " + lists.size() +  " " + lists);
                mAdapter.getDataList().addAll(lists);
                ptrFrame.refreshComplete();
                mAdapter.notifyDataSetChanged();
            }
        }, 0);
    }

    private class ViewHolder extends ViewHolderBase<JsonData> {
        private CubeImageView mImageView;
        private TextView mDesc;
        private LinkShareBar linkShareBar;
        @Override
        public View createView(LayoutInflater inflater) {
            View v = inflater.inflate(R.layout.list_view_recommend_item, null);
            mImageView = (CubeImageView) v.findViewById(R.id.list_view_item_image_view);
            mImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            mDesc = (TextView) v.findViewById(R.id.tv_desc);
            linkShareBar = (LinkShareBar) v.findViewById(R.id.lsb_bar);
            return v;
        }

        @Override
        public void showData(int position, JsonData itemData) {
            try {
                String imageUrl = itemData.optJson("images").optArrayOrNew().getString(0);
                if(!TextUtils.isEmpty(imageUrl)) {
//                    Log.d(TAG, "showData: 推荐中的图片地址 " + imageUrl);
                    mImageView.loadImage(mImageLoader, itemData.optJson("images").optArrayOrNew().getString(0));
                }
                mDesc.setText(itemData.optString("desc"));
                linkShareBar.setListener(new LinkShareBar.OnTagClickListener() {
                    @Override
                    public void onShareTagClick(View view) {
                        Toast.makeText(getContext(), "Share点击了", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCommentTagClick(View view) {
                        Toast.makeText(getContext(), "Comment点击了", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onLinkTagClick(View view) {
                        Toast.makeText(getContext(), "Link点击了", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onTypeTagClick(View view) {
                        Toast.makeText(getContext(), "Tag点击了", Toast.LENGTH_SHORT).show();
                    }
                });
            } catch (JSONException e) {
                Log.d(TAG, "showData: 没有图片的文章");
                mImageView.setImageResource(R.drawable.buildings);
                mDesc.setText(itemData.optString("desc"));
            }
        }
    }
}
