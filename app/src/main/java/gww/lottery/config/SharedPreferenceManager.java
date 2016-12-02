package gww.lottery.config;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by 高文文 on 2016/11/10.
 */

public class SharedPreferenceManager {
    private static SharedPreferenceManager mInstance;
    private Context mContext;
    private SharedPreferences sharedPreferences;

    private SharedPreferenceManager() {

    }
    public static SharedPreferenceManager getInstance() {
        synchronized (SharedPreferenceManager.class) {
            if(mInstance == null) {
                mInstance = new SharedPreferenceManager();
            }
        }
        return mInstance;
    }
    public void initialize(Context context, String path, int mode) {
        mContext = context;
        sharedPreferences = context.getSharedPreferences(path, mode);
    }

    public SharedPreferences getSharedPreferences() {
        return sharedPreferences;
    }
}
