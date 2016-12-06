package gww.lottery.restful.service;

import gww.lottery.data.GankData;
import gww.lottery.data.MeiZhiData;
import gww.lottery.data.RespiteData;
import gww.lottery.data.entity.History;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by 高文文 on 2016/12/6.
 */

public interface GankApi {
    //请求个数
    final int SIZE = 10;

    /**
     * 分类数据: http://gank.io/api/data/数据类型/请求个数/第几页
     * 数据类型： 福利 | Android | iOS | 休息视频 | 拓展资源 | 前端 | all
     */

    /**
       * 请求gank福利中的第page页的10条数据
    * @param page
    * @return
            */
    @GET("data/福利/" + SIZE +"/{page}")
    Call<MeiZhiData> getMeiZhiData(@Path("page") int page);
    @GET("data/福利/" + SIZE +"/{page}")
    Observable<MeiZhiData> getMeiZhiDataRx(@Path("page") int page);

    /**
     * 请求gank休息视频中的第page页的10条数据
     * @param page
     * @return
     */
    @GET("data/休息视频/" + SIZE +"/{page}")
    Call<RespiteData> getRespiteData(@Path("page") int page);
    @GET("data/休息视频/" + SIZE +"/{page}")
    Observable<RespiteData> getRespiteDataRx(@Path("page") int page);

    /**
     * 每日数据： http://gank.io/api/day/年/月/日
     */
    @GET("day/{year}/{month}/{day}")
    Call<GankData> getGankData(@Path("year") int year, @Path("month") int month, @Path("day") int day);
    @GET("day/{year}/{month}/{day}")
    Observable<GankData> getGankDataRx(@Path("year") int year, @Path("month") int month, @Path("day") int day);

    /**
     * 获取发过干货日期
     * http://gank.io/api/day/history
     */
    @GET("day/history")
    Call<History> getHistory();
    @GET("day/history")
    Observable<History> getHistoryRx();

    /**
     * http://gank.io/api/search/query/listview/category/Android/count/10/page/1
     * 注：
     *    category 后面可接受参数 all | Android | iOS | 休息视频 | 福利 | 拓展资源 | 前端 | 瞎推荐 | App
     *    count 最大 50
     */
    @GET("day/search/query/listview/category/{category}/count/" + SIZE+ "/page/{page}")
    Call<GankData> searchGankData(@Path("category") String which, @Path("page") int page);
    @GET("day/search/query/listview/category/{category}/count/" + SIZE+ "/page/{page}")
    Observable<GankData> searchGankDataRx(@Path("category") String which, @Path("page") int page);


    /**
     * 随机数据：http://gank.io/api/random/data/分类/个数
     * 数据类型：福利 | Android | iOS | 休息视频 | 拓展资源 | 前端
     * 个数： 数字，大于0
     * 例：http://gank.io/api/random/data/Android/20
     */
    @GET("random/data/{category}/" + SIZE)
    Call<GankData> randomGankData(@Path("category") String category);
    @GET("day/history")
    Observable<GankData> randomGankDataRx(@Path("category") String category);
}
