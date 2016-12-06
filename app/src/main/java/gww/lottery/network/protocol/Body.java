package gww.lottery.network.protocol;

import android.util.Log;

import com.google.gson.Gson;

import gww.lottery.LotteryConstants;
import gww.lottery.utils.DES;

import static android.content.ContentValues.TAG;

/**
 * Created by 高文文 on 2016/12/5.
 */

public class Body {
    Element element;

    public Body(Element element) {
        this.element = element;
    }

    public Element getElement() {
        return element;
    }

    public void setElement(Element element) {
        this.element = element;
    }

    /**
     * 获取当前body的明文信息
     * @return
     */
    public String getBody() {
        Gson gson = new Gson();
        String body = gson.toJson(this);
        Log.d(TAG, "getBody: 明文body " + body);
        return  body;
    }

    /**
     * 获得DES加密后的bod数据，不需要body:{ }的开始结束标签
     * @return
     */
    public  String gtEncodedBody() {
        Gson gson = new Gson();
        String orgDesInfo = gson.toJson(element);
        Log.d(TAG, "gtEncodedBody: 需要加密的数据 " + orgDesInfo);

        /**加密，加密的调试不容易，一般需要1~2天
		 * 加密中容易出现的错误：
		 * 1. 加密算法实现不同(有的不考虑空格，有个考虑空格结果不同);
		 * 2. 加密的原始数据不同,需要文本比对工具，若是回车换行则不容易看出来;
		 */
        DES des = new DES();
        String decodedInfo = des.authcode(orgDesInfo, "DECODE", LotteryConstants.DES_PASSWORD);
        Log.d(TAG, "gtEncodedBody: 加密后的body " + decodedInfo);
        return decodedInfo;
    }

    @Override
    public String toString() {
        return "Body{" +
                "elements=" + element +
                '}';
    }
}
