package gww.lottery.engine;

import android.util.Log;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;

import gww.lottery.LotteryConstants;
import gww.lottery.network.protocol.Message;
import gww.lottery.utils.DES;

import static android.content.ContentValues.TAG;

/**
 * Created by 高文文 on 2016/12/5.
 */

public abstract class BaseEngine {
    protected String orgInfo;
    /**
     * 检查好接收的报文MD5和计算出来的MD5是否一样，对报文完整性进行检验
     * @param message
     * @return
     */
    public boolean isPackageValid(Message message) {
        //原始数据：时间戳 + 代理密码 + body明文
        String body = new DES().authcode(message.getEncBody(), "ENCODE", LotteryConstants.DES_PASSWORD);
        orgInfo = message.getHeader().getTimestamp() +
                        LotteryConstants.AGENTER_PASSWORD +"{\"element\":" + body + "}";
        String recvMsgMD5 = new String(Hex.encodeHex(DigestUtils.md5(orgInfo)));
        Log.d(TAG, "isPackageValid: 接收报文MD5 " + recvMsgMD5 + "\n原始摘要信息 " + orgInfo);
        if(recvMsgMD5.equals(message.getHeader().getMD5()))
            return true;
        return false;
    }
}
