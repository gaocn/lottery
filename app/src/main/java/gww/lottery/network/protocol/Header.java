package gww.lottery.network.protocol;

import android.util.Log;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

import gww.lottery.LotteryConstants;

import static android.content.ContentValues.TAG;


/**
 * Created by 高文文 on 2016/12/5.
 */

public class Header {
    /**
     * 辨识发送信息的用户群，是属于子代理商还是自己
     */
    private String agentId;

    /**
     * 区分信息来源：iPhone，Android，其他
     * 可以通过手机IMEI设备编号或手机SIM卡编号，手机手机上的APP列表信息，利用数据分析技术做些事
     * 例如：了解比较火的软件寻求合作
     */
    private String source;

    /**
     * Body中的加密算法，DES,AES,MD5
     */
    private String compress;

    /**
     * 消息发送时间，SimpleDateFormat
     * 例如：201612051019
     */
    private String timestamp;

    /**
     *消息标识：时间戳+六位随机数
     */
    private String messageId;

    /**
     * 数据包摘要，原始数据构成：时间戳+代理的密码+完整body(明文)
     * MD5特性(报文完整性)：1、加密原始数据一样，加密结果相同；2、不可逆
     * 不可以去掉时间戳：若发送相同的请求时，MD5不变，这样是可破解的。
     */
    private String MD5;

    /**
     * 请求报文类型，唯一，服务端根据请求类型处理报文并返回相应结果
     */
    private String transactionType;

    /**
     * 发送报文的用户名
     */
    private String userName;

    public Header() {
        agentId = LotteryConstants.AGENTER_ID;
        source = LotteryConstants.SOURCE;
        compress = LotteryConstants.COMPRESS;
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddhhmmss", Locale.CHINA);
        timestamp = format.format(new Date());

        Log.d(TAG, "Header: 消息时间戳 " + timestamp);

        Random random = new Random();
        int rdnNumber = random.nextInt(999999) + 1; //[0,n),随机数范围为[0,999999)
        DecimalFormat decimalFormat = new DecimalFormat("000000");
        messageId = timestamp + decimalFormat.format(rdnNumber);
        Log.d(TAG, "Header: 消息标识 " + messageId);
    }


    /**
     * 根据 时间戳 + 代理商密码 + 明文body生成MD5并且将其复制到Header的MD5属性中
     * @param body
     */
    public void generateAndFillMD5(String body) {
        String metaInfo = timestamp + LotteryConstants.AGENTER_PASSWORD + body;
        MD5 = new String(Hex.encodeHex(DigestUtils.md5(metaInfo)));
        Log.d(TAG, "generateAndFillMD5: 消息摘要 " + MD5 + "\n原始摘要信息 " + metaInfo);
    }

    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getCompress() {
        return compress;
    }

    public void setCompress(String compress) {
        this.compress = compress;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getMD5() {
        return MD5;
    }

    public void setMD5(String MD5) {
        this.MD5 = MD5;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
