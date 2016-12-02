package gww.lottery.activities.base;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by 高文文 on 2016/12/2.
 */

public abstract class BaseActivity extends AppCompatActivity {
    private static final String TAG = "BaseActivity";
    /**
     * 统一管理所有的订阅subscription
     */
    CompositeSubscription mCompositeSubscription;

    public CompositeSubscription getmCompositeSubscription() {
        if(mCompositeSubscription == null) {
            mCompositeSubscription = new CompositeSubscription();
        }
        return mCompositeSubscription;
    }

    public void addCompositeSubscription(Subscription subscription) {
        if(mCompositeSubscription == null) {
            mCompositeSubscription = new CompositeSubscription();
        }
        mCompositeSubscription.add(subscription);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mCompositeSubscription != null) {
            Log.d(TAG, "onDestroy: 取消订阅");
            mCompositeSubscription.unsubscribe();
        }
    }
}
