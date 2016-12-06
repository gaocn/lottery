package gww.lottery.data.entity;

import com.google.gson.annotations.Expose;

/**
 * Created by 高文文 on 2016/12/6.
 */

public class DGank {
    /**
     * 没有@Expose(serialize=false|true, deserialize=true|false)注释的属性将不会被序列化
     * 使用@SerializedName标签定义属性序列化后的名字
     * Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
     */
    @Expose public String preview;
    @Expose public String tag;
    @Expose public String createdAt;
    @Expose public String updatedAt;
    @Expose public String objectId;
}
