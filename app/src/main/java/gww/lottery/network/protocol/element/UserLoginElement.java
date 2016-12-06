package gww.lottery.network.protocol.element;

import gww.lottery.network.protocol.Element;

/**
 * Created by 高文文 on 2016/12/5.
 */

public class UserLoginElement extends Element {

    private String passwd;

    /**
     * 报文类型为14001,表示请求登录报文
     */
    @Override
    public String getTransactionType() {
        return "14001";
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    @Override
    public String toString() {
        return "UserLoginElement{" +
                "passwd='" + passwd + '\'' +
                '}';
    }
}
