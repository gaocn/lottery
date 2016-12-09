package gww.lottery.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.litesuits.orm.db.annotation.Column;
import com.litesuits.orm.db.annotation.PrimaryKey;
import com.litesuits.orm.db.annotation.Table;
import com.litesuits.orm.db.enums.AssignType;

import java.util.List;

import gww.lottery.data.entity.Gank;

/**
 * Created by 高文文 on 2016/12/6.
 */

@Table("gankdata")
public class GankData {

    @PrimaryKey(AssignType.AUTO_INCREMENT)
    @Column("id")
    private long id;

    @Column("error")
    @SerializedName("error")
    @Expose
    private Boolean error;
    @Column("results")
    @SerializedName("results")
    @Expose
    public Result results;
    @Column("category")
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

        @SerializedName("休息视频")
        @Expose
        public List<Gank> 休息视频List;

        @SerializedName("瞎推荐")
        @Expose
        public List<Gank> 瞎推荐List;

        @Override
        public String toString() {
            return "Result{" +
                    "androidList=" + androidList +
                    ", iOSList=" + iOSList +
                    ", 前端List=" + 前端List +
                    ", 瞎推荐List=" + 瞎推荐List +
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

        public List<Gank> get前端List() {
            return 前端List;
        }

        public void set前端List(List<Gank> 前端List) {
            this.前端List = 前端List;
        }

        public List<Gank> get休息视频List() {
            return 休息视频List;
        }

        public void set休息视频List(List<Gank> 休息视频List) {
            this.休息视频List = 休息视频List;
        }

        public List<Gank> get瞎推荐List() {
            return 瞎推荐List;
        }

        public void set瞎推荐List(List<Gank> 瞎推荐List) {
            this.瞎推荐List = 瞎推荐List;
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
