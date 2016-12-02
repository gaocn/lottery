package gww.lottery.config;

import android.content.Context;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by 高文文 on 2016/11/7.
 */

public class PropertiesManager {
    private static final String TAG = "PropertiesManager";
    private static PropertiesManager mInstance;
    private Properties properties;
    private PropertiesManager(){}
    public static PropertiesManager getInstance() {
        synchronized (PropertiesManager.class) {
            if(mInstance == null) {
                mInstance = new PropertiesManager();
            }
        }
        return mInstance;
    }

    public void initialize(Context context) {
        Log.d(TAG, "initialize: properties初始化");
        properties = new Properties();
        try {
            properties.load(context.getAssets().open("lottery.properties"));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void initialize() {
        Log.d(TAG, "initialize: properties初始化");
        try {
            properties = new Properties();
            InputStream is = PropertiesManager.class.getResourceAsStream("/assets/lottery.properties");
            properties.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getValue(String key) {
        Log.d(TAG, "getValue: key = " + key);
        if(properties == null) {
            initialize();
        }
        return properties.get(key).toString();
    }

}
