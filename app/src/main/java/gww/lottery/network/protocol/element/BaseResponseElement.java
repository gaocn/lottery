package gww.lottery.network.protocol.element;

/**
 * Created by 高文文 on 2016/12/5.
 */

public class BaseResponseElement {
    /**
     * 响应码
     */
    private String retCode;
    /**
     * 响应消息
     */
    private String retMsg;

    public String getRetCode() {
        return retCode;
    }

    public void setRetCode(String retCode) {
        this.retCode = retCode;
    }

    public String getRetMsg() {
        return retMsg;
    }

    public void setRetMsg(String retMsg) {
        this.retMsg = retMsg;
    }

    @Override
    public String toString() {
        return "BaseResponseElement{" +
                "retCode='" + retCode + '\'' +
                ", retMsg='" + retMsg + '\'' +
                '}';
    }
}
