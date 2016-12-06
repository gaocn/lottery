package gww.lottery.data.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.litesuits.orm.db.annotation.Column;
import com.litesuits.orm.db.annotation.NotNull;
import com.litesuits.orm.db.annotation.PrimaryKey;
import com.litesuits.orm.db.annotation.Table;
import com.litesuits.orm.db.annotation.Unique;
import com.litesuits.orm.db.enums.AssignType;

/**
 * Created by 高文文 on 2016/12/6.
 */

/*
      "_id": "58460694421aa939b58d31e3",
      "createdAt": "2016-12-06T08:30:12.824Z",
      "desc": "12-6",
      "publishedAt": "2016-12-06T11:33:36.433Z",
      "source": "chrome",
      "type": "\u798f\u5229",
      "url": "http://ww4.sinaimg.cn/large/610dc034jw1fagrnmiqm1j20u011hanr.jpg",
      "used": true,
      "who": "daimajia "
 */
@Table("meizhis")
public class MeiZhi {
    @PrimaryKey(AssignType.AUTO_INCREMENT)
    @Column("id")
    private long id;

    @NotNull @Unique
    @Column("objectId")
    @SerializedName("_id")
    @Expose
    private String objectId;

    @Column("createdAt")
    @SerializedName("createdAt")
    @Expose
    private String createdAt;

    @Column("desc")
    @SerializedName("desc")
    @Expose
    private String desc;

    @Column("publishedAt")
    @SerializedName("publishedAt")
    @Expose
    private String publishedAt;

    @Column("source")
    @SerializedName("source")
    @Expose
    private String source;

    @Column("type")
    @SerializedName("type")
    @Expose
    private String type;

    @Column("url")
    @SerializedName("url")
    @Expose
    private String url;

    @Column("used")
    @SerializedName("used")
    @Expose
    private Boolean used;
    @Column("who")
    @SerializedName("who")
    @Expose
    private String who;

    /**
     *
     * @return
     *     The objectId
     */
    public String getObjectId() {
        return objectId;
    }

    /**
     *
     * @param objectId
     *     The _id
     */
    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    /**
     *
     * @return
     *     The createdAt
     */
    public String getCreatedAt() {
        return createdAt;
    }

    /**
     *
     * @param createdAt
     *     The createdAt
     */
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    /**
     *
     * @return
     *     The desc
     */
    public String getDesc() {
        return desc;
    }

    /**
     *
     * @param desc
     *     The desc
     */
    public void setDesc(String desc) {
        this.desc = desc;
    }

    /**
     *
     * @return
     *     The publishedAt
     */
    public String getPublishedAt() {
        return publishedAt;
    }

    /**
     *
     * @param publishedAt
     *     The publishedAt
     */
    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    /**
     *
     * @return
     *     The source
     */
    public String getSource() {
        return source;
    }

    /**
     *
     * @param source
     *     The source
     */
    public void setSource(String source) {
        this.source = source;
    }

    /**
     *
     * @return
     *     The type
     */
    public String getType() {
        return type;
    }

    /**
     *
     * @param type
     *     The type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     *
     * @return
     *     The url
     */
    public String getUrl() {
        return url;
    }

    /**
     *
     * @param url
     *     The url
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     *
     * @return
     *     The used
     */
    public Boolean getUsed() {
        return used;
    }

    /**
     *
     * @param used
     *     The used
     */
    public void setUsed(Boolean used) {
        this.used = used;
    }

    /**
     *
     * @return
     *     The who
     */
    public String getWho() {
        return who;
    }

    /**
     *
     * @param who
     *     The who
     */
    public void setWho(String who) {
        this.who = who;
    }
}
