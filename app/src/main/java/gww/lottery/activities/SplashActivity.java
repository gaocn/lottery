package gww.lottery.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.umeng.analytics.MobclickAgent;

import butterknife.BindView;
import butterknife.ButterKnife;
import gww.lottery.BeanFactory;
import gww.lottery.MainActivity;
import gww.lottery.R;
import gww.lottery.activities.base.BaseActivity;
import gww.lottery.engine.UpdateEngine;

public class SplashActivity extends BaseActivity {
    private static final String TAG = "SplashActivity";
    @BindView(R.id.llt_loading)
    LinearLayout mLltLoading;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);

        checkVersion();
    }
    public void checkVersion() {
        mLltLoading.setVisibility(View.VISIBLE);
        UpdateEngine updateEngine = BeanFactory.getImpl(UpdateEngine.class);
        updateEngine.checkUpdate();
        jumpToMainActivity();
    }
    private void jumpToMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
}
