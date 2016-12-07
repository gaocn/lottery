package gww.lottery.image;

import in.srain.cube.request.CacheAbleRequest;
import in.srain.cube.request.CacheAbleRequestHandler;
import in.srain.cube.request.CacheAbleRequestJsonHandler;
import in.srain.cube.request.JsonData;
import in.srain.cube.request.RequestFinishHandler;

/**
 * Created by 高文文 on 2016/12/7.
 */

public class RequestImagesUtil {
    public static void getImageList(final RequestFinishHandler<JsonData> requestFinishHandler) {

        CacheAbleRequestHandler requestHandler = new CacheAbleRequestJsonHandler() {
            @Override
            public void onCacheAbleRequestFinish(JsonData data, CacheAbleRequest.ResultType type, boolean outOfDate) {
                requestFinishHandler.onRequestFinish(data);
            }
        };
        CacheAbleRequest<JsonData> request = new CacheAbleRequest<JsonData>(requestHandler);
        String url = "http://cube-server.liaohuqiu.net/api_demo/image-list.php";
        request.setCacheTime(3600);
        request.setTimeout(1000);
        request.getRequestData().setRequestUrl(url);
        request.setAssertInitDataPath("demo/image-list.json");
        request.setCacheKey("image-list-1");
        request.send();
    }
}
