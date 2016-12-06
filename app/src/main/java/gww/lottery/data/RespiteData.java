package gww.lottery.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import gww.lottery.data.entity.Gank;

/**
 * 休息视频信息
 * Created by 高文文 on 2016/12/6.
 */
public class RespiteData {

    @SerializedName("error")
    @Expose
    private Boolean error;
    @SerializedName("results")
    @Expose
    private List<Gank> results = null;

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    public List<Gank> getResults() {
        return results;
    }

    public void setResults(List<Gank> results) {
        this.results = results;
    }

    @Override
    public String toString() {
        return "RespiteData{" +
                "error=" + error +
                ", results=" + results +
                '}';
    }
}
