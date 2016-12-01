package gww.lottery;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import gww.lottery.bus.RxBus;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    @BindView(R.id.btn_1)
    Button mBtn1;
    @BindView(R.id.tv_hint)
    TextView mTvHint;

    @Override
    protected void onStart() {
//        OttoBus.getInstance().register(this);
        super.onStart();
    }

    @Override
    protected void onStop() {
//        OttoBus.getInstance().unregister(this);
        super.onStop();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        //RxView.

        initData();
        showHint();
    }

    @OnClick(R.id.btn_1)
    public void onClick(View view) {
//        OttoBus.getInstance().post("点我" + new Random().nextInt());
        RxBus.getInstance().post("点我" + new Random().nextInt());
    }

/*    @Subscribe
    public void showHint(String hint) {
        mTvHint.setText(hint);
    }*/

    public void showHint() {
        RxBus.getInstance().toObservable().subscribe(new Action1<Object>() {
            @Override
            public void call(Object s) {
                mTvHint.setText((String)s);
            }
        });
    }
    private void initData() {

    }

    private void rxJavaTest() {

        /**
         * 创建出 Observable 和 Subscriber ，再用 subscribe() 将它们串起来
         *
         * 在哪个线程调用 subscribe()，就在哪个线程生产事件；在哪个线程生产事件，就在哪个线程消费事件。
         * 如果需要切换线程，就需要用到 Scheduler。
         *  subscribeOn(): 指定 subscribe 所发生的线程，即 Observable.OnSubscribe 被激活时所处的线程。或者叫做事件产生的线程。
         *  observeOn():  指定 Subscriber 所运行在的线程。或者叫做事件消费的线程。
         */
        Observable observable = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("Hello");
                subscriber.onNext("Haha");
                subscriber.onCompleted();
            }
        })
                .subscribeOn(Schedulers.io())  // 事件产生的线程，只能调用一次
                .observeOn(AndroidSchedulers.mainThread()); //事件消费的线程，可多次调用，指定其后的操作所在的线程

        Action1<String> onNextAction = new Action1<String>() {
            @Override
            public void call(String s) {
                Log.d(TAG, "call: " + s);
            }
        };
        Action1<Throwable> onErrorAction = new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                Log.d(TAG, "call: error");
                throwable.printStackTrace();
            }
        };
        Action0 onCompleteAction = new Action0() {
            @Override
            public void call() {
                Log.d(TAG, "call:  finished");
            }
        };
        observable.subscribe(onNextAction, onErrorAction, onCompleteAction);
    }
}
