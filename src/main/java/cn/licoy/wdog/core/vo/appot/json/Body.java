package cn.licoy.wdog.core.vo.appot.json;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "minHour",
    "cancelHour",
    "isCancel",
    "siteInfos",
    "productId",
    "isConfirm",
    "etime",
    "bill",
    "stime"
})
public class Body {

    @JsonProperty("minHour")
    private Integer minHour;
    @JsonProperty("cancelHour")
    private String cancelHour;
    @JsonProperty("isCancel")
    private Integer isCancel;
    @JsonProperty("siteInfos")
    private List<SiteInfo> siteInfos = new ArrayList<SiteInfo>();
    @JsonProperty("productId")
    private Integer productId;
    @JsonProperty("isConfirm")
    private Integer isConfirm;
    @JsonProperty("etime")
    private String etime;
    @JsonProperty("bill")
    private Integer bill;
    @JsonProperty("stime")
    private String stime;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("minHour")
    public Integer getMinHour() {
        return minHour;
    }

    @JsonProperty("minHour")
    public void setMinHour(Integer minHour) {
        this.minHour = minHour;
    }

    @JsonProperty("cancelHour")
    public String getCancelHour() {
        return cancelHour;
    }

    @JsonProperty("cancelHour")
    public void setCancelHour(String cancelHour) {
        this.cancelHour = cancelHour;
    }

    @JsonProperty("isCancel")
    public Integer getIsCancel() {
        return isCancel;
    }

    @JsonProperty("isCancel")
    public void setIsCancel(Integer isCancel) {
        this.isCancel = isCancel;
    }

    @JsonProperty("siteInfos")
    public List<SiteInfo> getSiteInfos() {
        return siteInfos;
    }

    @JsonProperty("siteInfos")
    public void setSiteInfos(List<SiteInfo> siteInfos) {
        this.siteInfos = siteInfos;
    }

    @JsonProperty("productId")
    public Integer getProductId() {
        return productId;
    }

    @JsonProperty("productId")
    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    @JsonProperty("isConfirm")
    public Integer getIsConfirm() {
        return isConfirm;
    }

    @JsonProperty("isConfirm")
    public void setIsConfirm(Integer isConfirm) {
        this.isConfirm = isConfirm;
    }

    @JsonProperty("etime")
    public String getEtime() {
        return etime;
    }

    @JsonProperty("etime")
    public void setEtime(String etime) {
        this.etime = etime;
    }

    @JsonProperty("bill")
    public Integer getBill() {
        return bill;
    }

    @JsonProperty("bill")
    public void setBill(Integer bill) {
        this.bill = bill;
    }

    @JsonProperty("stime")
    public String getStime() {
        return stime;
    }

    @JsonProperty("stime")
    public void setStime(String stime) {
        this.stime = stime;
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
        sb.append(Body.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("minHour");
        sb.append('=');
        sb.append(((this.minHour == null)?"<null>":this.minHour));
        sb.append(',');
        sb.append("cancelHour");
        sb.append('=');
        sb.append(((this.cancelHour == null)?"<null>":this.cancelHour));
        sb.append(',');
        sb.append("isCancel");
        sb.append('=');
        sb.append(((this.isCancel == null)?"<null>":this.isCancel));
        sb.append(',');
        sb.append("siteInfos");
        sb.append('=');
        sb.append(((this.siteInfos == null)?"<null>":this.siteInfos));
        sb.append(',');
        sb.append("productId");
        sb.append('=');
        sb.append(((this.productId == null)?"<null>":this.productId));
        sb.append(',');
        sb.append("isConfirm");
        sb.append('=');
        sb.append(((this.isConfirm == null)?"<null>":this.isConfirm));
        sb.append(',');
        sb.append("etime");
        sb.append('=');
        sb.append(((this.etime == null)?"<null>":this.etime));
        sb.append(',');
        sb.append("bill");
        sb.append('=');
        sb.append(((this.bill == null)?"<null>":this.bill));
        sb.append(',');
        sb.append("stime");
        sb.append('=');
        sb.append(((this.stime == null)?"<null>":this.stime));
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
        result = ((result* 31)+((this.minHour == null)? 0 :this.minHour.hashCode()));
        result = ((result* 31)+((this.cancelHour == null)? 0 :this.cancelHour.hashCode()));
        result = ((result* 31)+((this.isCancel == null)? 0 :this.isCancel.hashCode()));
        result = ((result* 31)+((this.siteInfos == null)? 0 :this.siteInfos.hashCode()));
        result = ((result* 31)+((this.productId == null)? 0 :this.productId.hashCode()));
        result = ((result* 31)+((this.isConfirm == null)? 0 :this.isConfirm.hashCode()));
        result = ((result* 31)+((this.etime == null)? 0 :this.etime.hashCode()));
        result = ((result* 31)+((this.bill == null)? 0 :this.bill.hashCode()));
        result = ((result* 31)+((this.stime == null)? 0 :this.stime.hashCode()));
        result = ((result* 31)+((this.additionalProperties == null)? 0 :this.additionalProperties.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Body) == false) {
            return false;
        }
        Body rhs = ((Body) other);
        return (((((((((((this.minHour == rhs.minHour)||((this.minHour!= null)&&this.minHour.equals(rhs.minHour)))&&((this.cancelHour == rhs.cancelHour)||((this.cancelHour!= null)&&this.cancelHour.equals(rhs.cancelHour))))&&((this.isCancel == rhs.isCancel)||((this.isCancel!= null)&&this.isCancel.equals(rhs.isCancel))))&&((this.siteInfos == rhs.siteInfos)||((this.siteInfos!= null)&&this.siteInfos.equals(rhs.siteInfos))))&&((this.productId == rhs.productId)||((this.productId!= null)&&this.productId.equals(rhs.productId))))&&((this.isConfirm == rhs.isConfirm)||((this.isConfirm!= null)&&this.isConfirm.equals(rhs.isConfirm))))&&((this.etime == rhs.etime)||((this.etime!= null)&&this.etime.equals(rhs.etime))))&&((this.bill == rhs.bill)||((this.bill!= null)&&this.bill.equals(rhs.bill))))&&((this.stime == rhs.stime)||((this.stime!= null)&&this.stime.equals(rhs.stime))))&&((this.additionalProperties == rhs.additionalProperties)||((this.additionalProperties!= null)&&this.additionalProperties.equals(rhs.additionalProperties))));
    }

}
