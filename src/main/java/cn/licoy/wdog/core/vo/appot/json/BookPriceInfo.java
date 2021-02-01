package cn.licoy.wdog.core.vo.appot.json;

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "bookStatus",
    "salePrice",
    "beginTime",
    "endTime",
    "isGroup",
    "xq",
    "goodsId"
})
public class BookPriceInfo implements Cloneable{

    @JsonProperty("bookStatus")
    private Integer bookStatus;
    @JsonProperty("salePrice")
    private Double salePrice;
    @JsonProperty("beginTime")
    private String beginTime;
    @JsonProperty("endTime")
    private String endTime;
    @JsonProperty("isGroup")
    private Integer isGroup;
    @JsonProperty("xq")
    private String xq;
    @JsonProperty("goodsId")
    private String goodsId;

    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("bookStatus")
    public Integer getBookStatus() {
        return bookStatus;
    }

    @JsonProperty("bookStatus")
    public void setBookStatus(Integer bookStatus) {
        this.bookStatus = bookStatus;
    }

    @JsonProperty("salePrice")
    public Double getSalePrice() {
        return salePrice;
    }

    @JsonProperty("salePrice")
    public void setSalePrice(Double salePrice) {
        this.salePrice = salePrice;
    }

    @JsonProperty("beginTime")
    public String getBeginTime() {
        return beginTime;
    }

    @JsonProperty("beginTime")
    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    @JsonProperty("xq")
    public String getXq() {
        return xq;
    }

    @JsonProperty("xq")
    public void setXq(String xq) {
        this.xq = xq;
    }

    @JsonProperty("goodsId")
    public String getGoodsId() {
        return goodsId;
    }

    @JsonProperty("goodsId")
    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }


    @JsonProperty("endTime")
    public String getEndTime() {
        return endTime;
    }

    @JsonProperty("endTime")
    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    @JsonProperty("isGroup")
    public Integer getIsGroup() {
        return isGroup;
    }

    @JsonProperty("isGroup")
    public void setIsGroup(Integer isGroup) {
        this.isGroup = isGroup;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(BookPriceInfo.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("bookStatus");
        sb.append('=');
        sb.append(((this.bookStatus == null)?"<null>":this.bookStatus));
        sb.append(',');
        sb.append("salePrice");
        sb.append('=');
        sb.append(((this.salePrice == null)?"<null>":this.salePrice));
        sb.append(',');
        sb.append("beginTime");
        sb.append('=');
        sb.append(((this.beginTime == null)?"<null>":this.beginTime));
        sb.append(',');
        sb.append("endTime");
        sb.append('=');
        sb.append(((this.endTime == null)?"<null>":this.endTime));
        sb.append(',');
        sb.append("isGroup");
        sb.append('=');
        sb.append(((this.isGroup == null)?"<null>":this.isGroup));
        sb.append(',');
        sb.append("additionalProperties");
        sb.append('=');
        sb.append(((this.additionalProperties == null)?"<null>":this.additionalProperties));
        sb.append(',');
        if (sb.charAt((sb.length()- 1)) == ',') {
            sb.setCharAt((sb.length()- 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = ((result* 31)+((this.bookStatus == null)? 0 :this.bookStatus.hashCode()));
        result = ((result* 31)+((this.salePrice == null)? 0 :this.salePrice.hashCode()));
        result = ((result* 31)+((this.beginTime == null)? 0 :this.beginTime.hashCode()));
        result = ((result* 31)+((this.endTime == null)? 0 :this.endTime.hashCode()));
        result = ((result* 31)+((this.additionalProperties == null)? 0 :this.additionalProperties.hashCode()));
        result = ((result* 31)+((this.isGroup == null)? 0 :this.isGroup.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof BookPriceInfo) == false) {
            return false;
        }
        BookPriceInfo rhs = ((BookPriceInfo) other);
        return (((((((this.bookStatus == rhs.bookStatus)||((this.bookStatus!= null)&&this.bookStatus.equals(rhs.bookStatus)))&&((this.salePrice == rhs.salePrice)||((this.salePrice!= null)&&this.salePrice.equals(rhs.salePrice))))&&((this.beginTime == rhs.beginTime)||((this.beginTime!= null)&&this.beginTime.equals(rhs.beginTime))))&&((this.endTime == rhs.endTime)||((this.endTime!= null)&&this.endTime.equals(rhs.endTime))))&&((this.additionalProperties == rhs.additionalProperties)||((this.additionalProperties!= null)&&this.additionalProperties.equals(rhs.additionalProperties))))&&((this.isGroup == rhs.isGroup)||((this.isGroup!= null)&&this.isGroup.equals(rhs.isGroup))));
    }

    @Override
    public BookPriceInfo clone() {
        BookPriceInfo person = null;
        try {
            person = (BookPriceInfo) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return person;
    }
}
