package gww.lottery.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import gww.lottery.data.entity.Gank;

/**
 * Created by 高文文 on 2016/12/6.
 */

public class GankData {
    @SerializedName("error")
    @Expose
    private Boolean error;
    @SerializedName("results")
    @Expose
    public Result results;
    @SerializedName("category")
    @Expose
    public List<String> category;

    public class Result {
        @SerializedName("Android")
        @Expose
        public List<Gank> androidList;
        @SerializedName("iOS")
        @Expose
        public List<Gank> iOSList;
        @SerializedName("前端")
        @Expose
        public List<Gank> 前端List;
        @SerializedName("福利")
        @Expose
        public List<Gank> 妹纸List;
        @SerializedName("休息视频")
        @Expose
        public List<Gank> 休息视频List;
//        @SerializedName("拓展资源") public List<Gank> 拓展资源List;
//        @SerializedName("瞎推荐") public List<Gank> 瞎推荐List;

        @Override
        public String toString() {
            return "Result{" +
                    "androidList=" + androidList +
                    ", iOSList=" + iOSList +
                    ", 前端List=" + 前端List +
                    ", 妹纸List=" + 妹纸List +
                    ", 休息视频List=" + 休息视频List +
                    '}';
        }

        public List<Gank> getAndroidList() {
            return androidList;
        }

        public void setAndroidList(List<Gank> androidList) {
            this.androidList = androidList;
        }

        public List<Gank> getiOSList() {
            return iOSList;
        }

        public void setiOSList(List<Gank> iOSList) {
            this.iOSList = iOSList;
        }

        public List<Gank> getAppList() {
            return 前端List;
        }

        public void setAppList(List<Gank> appList) {
            this.前端List = appList;
        }

        public List<Gank> get妹纸List() {
            return 妹纸List;
        }

        public void set妹纸List(List<Gank> 妹纸List) {
            this.妹纸List = 妹纸List;
        }

        public List<Gank> get休息视频List() {
            return 休息视频List;
        }

        public void set休息视频List(List<Gank> 休息视频List) {
            this.休息视频List = 休息视频List;
        }
    }

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    public Result getResults() {
        return results;
    }

    public void setResults(Result results) {
        this.results = results;
    }

    public List<String> getCategory() {
        return category;
    }

    public void setCategory(List<String> category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "GankData{" +
                "error=" + error +
                ", results=" + results +
                ", category=" + category +
                '}';
    }
}
