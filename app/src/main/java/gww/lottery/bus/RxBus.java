package gww.lottery.bus;


import rx.Observable;
import rx.subjects.PublishSubject;
import rx.subjects.SerializedSubject;
import rx.subjects.Subject;

/**
 * Created by 高文文 on 2016/12/1.
 * ！！ 若不希望使用otto额外的依赖库，并且对RxJava很熟悉的话可以不使用otto；
 */

public class RxBus {
    private static RxBus mRxBus;
    private final Subject<Object, Object> mBus = new SerializedSubject<>(PublishSubject.create());
    private RxBus() {}
    public static RxBus getInstance() {
        if(mRxBus == null) {
            mRxBus = new RxBus();
        }
        return mRxBus;
    }
    public void post(Object obj) {
        mBus.onNext(obj);
    }

    public Observable<Object> toObservable() {
        return mBus;
    }

    /**
     * 使用说明
     * 1、发送消息：RxBus.getInstance().psot(Event)
     *
     * 2、处理消息：RxBus.getInstance.toObservable().subscribe(new Action1<Object>() {
                        @Override
                        public void call(Object event) {
                            if(event instanceof TapEvent) {
                                _showTapText();

                            }else if(event instanceof SomeOtherEvent) {
                                _doSomethingElse();
                            }
                        }
                  });
        PS: 上述代码得在对应的观察者中被调用后才能进行监听工作
     */

}
