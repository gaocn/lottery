package gww.lottery.data.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.litesuits.orm.db.annotation.Column;
import com.litesuits.orm.db.annotation.NotNull;
import com.litesuits.orm.db.annotation.PrimaryKey;
import com.litesuits.orm.db.annotation.Table;
import com.litesuits.orm.db.annotation.Unique;
import com.litesuits.orm.db.enums.AssignType;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 高文文 on 2016/12/6.
 */
@Table("gank")
public class Gank implements Serializable {
    @PrimaryKey(AssignType.AUTO_INCREMENT)
    @Column("id")
    private long id;

    @NotNull
    @Unique
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

    @Column("images")
    @SerializedName("images")
    @Expose
    private List<String> images;

    @Column("used")
    @SerializedName("used")
    @Expose
    private Boolean used;
    @Column("who")
    @SerializedName("who")
    @Expose
    private String who;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Boolean getUsed() {
        return used;
    }

    public void setUsed(Boolean used) {
        this.used = used;
    }

    public String getWho() {
        return who;
    }

    public void setWho(String who) {
        this.who = who;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    @Override
    public String toString() {
        return "Gank{" +
                "id=" + id +
                ", objectId='" + objectId + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", desc='" + desc + '\'' +
                ", publishedAt='" + publishedAt + '\'' +
                ", source='" + source + '\'' +
                ", type='" + type + '\'' +
                ", url='" + url + '\'' +
                ", images=" + images +
                ", used=" + used +
                ", who='" + who + '\'' +
                '}';
    }
}
