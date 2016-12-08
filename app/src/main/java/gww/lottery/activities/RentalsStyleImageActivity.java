package gww.lottery.activities;

import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import butterknife.BindView;
import butterknife.ButterKnife;
import gww.lottery.R;
import gww.lottery.activities.base.BaseActivity;
import gww.lottery.header.RentalsSunHeaderView;
import gww.lottery.ui.TitleBar;
import in.srain.cube.image.CubeImageView;
import in.srain.cube.image.ImageLoader;
import in.srain.cube.image.ImageLoaderFactory;
import in.srain.cube.image.ImageTask;
import in.srain.cube.image.iface.ImageLoadHandler;
import in.srain.cube.util.LocalDisplay;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

public class RentalsStyleImageActivity extends BaseActivity {
    private static final String TAG = "RentalsStyleImageActivi";
    @BindView(R.id.title_bar)
    TitleBar mTitleBar;
    @BindView(R.id.material_style_image_view)
    CubeImageView mImageView;
    @BindView(R.id.material_style_ptr_frame)
    PtrFrameLayout mPtrFrame;

    private String mUrl;
    private long mStartLoadingTime = -1;
    private boolean mImageHasLoaded = false;
    private ImageLoader imageLoader;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rentals_style_image);
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

        mUrl = getIntent().getStringExtra("url");
        Log.d(TAG, "onCreate: 显示图片地址 " + mUrl);

        imageLoader = ImageLoaderFactory.create(getApplicationContext());
        // header
        final RentalsSunHeaderView header = new RentalsSunHeaderView(getApplicationContext());
        header.setLayoutParams(new PtrFrameLayout.LayoutParams(-1, -2));
        header.setPadding(0, LocalDisplay.dp2px(15), 0, LocalDisplay.dp2px(10));
        header.setUp(mPtrFrame);

        mPtrFrame.setLoadingMinTime(1000);
        mPtrFrame.setDurationToCloseHeader(1500);
        mPtrFrame.setHeaderView(header);
        mPtrFrame.addPtrUIHandler(header);
        mPtrFrame.postDelayed(new Runnable() {
            @Override
            public void run() {
                mPtrFrame.autoRefresh(true);
            }
        }, 100);
        mPtrFrame.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return true;
            }

            @Override
            public void onRefreshBegin(final PtrFrameLayout frame) {
                if (mImageHasLoaded) {
                    long delay = 1500;
                    frame.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            frame.refreshComplete();
                        }
                    }, delay);
                } else {
                    mStartLoadingTime = System.currentTimeMillis();
                    mImageView.loadImage(imageLoader, mUrl);
                }
            }
        });

        ImageLoadHandler imageLoadHandler = new ImageLoadHandler() {
            @Override
            public void onLoading(ImageTask imageTask, CubeImageView cubeImageView) {
            }

            @Override
            public void onLoadFinish(ImageTask imageTask, final CubeImageView cubeImageView, final BitmapDrawable bitmapDrawable) {
                mImageHasLoaded = true;
                long delay = 1500;
                mPtrFrame.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (cubeImageView != null && bitmapDrawable != null) {
                            TransitionDrawable w1 = new TransitionDrawable(new Drawable[]{new ColorDrawable(Color.WHITE), (Drawable) bitmapDrawable});
                            mImageView.setImageDrawable(w1);
                            w1.startTransition(200);
                        }
                        mPtrFrame.refreshComplete();
                    }
                }, delay);
            }

            @Override
            public void onLoadError(ImageTask imageTask, CubeImageView cubeImageView, int i) {

            }
        };
        imageLoader.setImageLoadHandler(imageLoadHandler);
    }
}
