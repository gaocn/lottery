package gww.lottery;

import android.util.Log;

import java.io.IOException;
import java.util.Properties;

/**
 * 工厂类：依据配置文件bean.properties加载业务实例
 * Created by 高文文 on 2016/12/5.
 */

public class BeanFactory {
    private static final String TAG = "gww.lottery.BeanFactory";
    private static Properties properties;

    public static void beanFactoryInit() {
        properties = new Properties();
        try {
            Log.d(TAG, "beanFactoryInit: 初始化Bean工厂");
            properties.load(BeanFactory.class.getResourceAsStream("/assets/bean.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static <T> T getImpl(Class<T> clazz) {
        String key = clazz.getSimpleName();
        String className = properties.getProperty(key);
        try {
            Log.d(TAG, "getImpl: className " + className);
            return (T) Class.forName(className).newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
