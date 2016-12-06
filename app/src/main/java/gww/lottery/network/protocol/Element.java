package gww.lottery.network.protocol;

/**
 * Created by 高文文 on 2016/12/5.
 */

/**
 *  * 请求数据 body中element的封装
 *  "element":
 *      {
 *          "lotteryid":"118",
 *          "issues":"1"
 *      }
 *  每个请求需要实现并添加请求的参数，同时该请求的类型
 */
public abstract class Element {

    /**
     *返回该请求的类型标识
     * @return
     */
    public abstract String getTransactionType();
}
