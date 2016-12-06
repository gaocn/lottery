package gww.lottery.network.protocol;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import static android.content.ContentValues.TAG;

/**
 * Created by 高文文 on 2016/12/5.
 */

public class Message implements Serializable{
    //GSON序列化后的字段名
    @SerializedName("header")
    private Header header;
    @SerializedName("body")
    private String  encBody;
    //不序列化Body对象
    transient private Body body;

    public Message(Element element) {
        header = new Header();
        body = new Body(element);
        header.setTransactionType(element.getTransactionType());
        header.generateAndFillMD5(body.getBody());
        encBody = body.gtEncodedBody();
    }

    /**
     * 获取加密body后的JSON串
     * @return
     */
    public String getRequest() {
        Gson gson = new Gson();
        String request = gson.toJson(this);
        Log.d(TAG, "getRequest: 请求报文(密文)" + request);
        return request;
    }

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public Body getBody() {
        return body;
    }

    public void setBody(Body body) {
        this.body = body;
    }

    public String getEncBody() {
        return encBody;
    }

    public void setEncBody(String encBody) {
        this.encBody = encBody;
    }
}
