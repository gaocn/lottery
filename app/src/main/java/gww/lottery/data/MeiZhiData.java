package gww.lottery.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import gww.lottery.data.entity.MeiZhi;

/**
 * Created by 高文文 on 2016/12/6.
 */

public class MeiZhiData {



    @SerializedName("error")
    @Expose
    private Boolean error;
    @SerializedName("results")
    @Expose
    private List<MeiZhi> results = null;
    /**
     *
     * @return
     *     The error
     */
    public Boolean getError() {
        return error;
    }

    /**
     *
     * @param error
     *     The error
     */
    public void setError(Boolean error) {
        this.error = error;
    }

    /**
     *
     * @return
     *     The results
     */
    public List<MeiZhi> getResults() {
        return results;
    }

    /**
     *
     * @param results
     *     The results
     */
    public void setResults(List<MeiZhi> results) {
        this.results = results;
    }

    @Override
    public String toString() {
        return "MeiZhiData{" +
                "error=" + error +
                ", results=" + results +
                '}';
    }
}
