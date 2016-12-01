package gww.lottery.bus;

import com.squareup.otto.Bus;

/**
 * Created by 高文文 on 2016/12/1.
 *
 * otto基于Guava项目的Android支持库，如果你在Android程序开发的过程中想要不同的组件之间进行有效的通信可以使用这个库。
 * 通过otto库可以降低程序之间的耦合性。
 */

public class OttoBus extends Bus{
    private static OttoBus mBus;
    public static OttoBus getInstance() {
        if(mBus == null) {
            mBus = new OttoBus();
        }
        return mBus;
    }
    private OttoBus(){}

    /**
     * 使用步骤：
     * 1、使用OttoBus.getInstance.register(this)在onStart方法中注册当前对象到事件总线中；
     * 2、使用@Subscribe注解在所相应的方法上标志，表示订阅者，该方法能够接收到数据的变化，数据参数为自定义的事件总线；
     * 3、使用post方法发布消息，例如：
     *      AppBus.getInstance().post(new BusEventData("somebody alive"));
     *      AppBus.getInstance().post("hello");
     *4、在对象销毁之前调用 AppBus.getInstance().unregister(this);取消绑定，防止内存泄露，因为基于观察者模式各自持有对方的引用
     *
     */
}
