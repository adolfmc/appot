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
    "bookPriceInfos",
    "siteNo",
    "siteName"
})
public class SiteInfo {

    @JsonProperty("bookPriceInfos")
    private List<BookPriceInfo> bookPriceInfos = new ArrayList<BookPriceInfo>();
    @JsonProperty("siteNo")
    private String siteNo;
    @JsonProperty("siteName")
    private String siteName;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("bookPriceInfos")
    public List<BookPriceInfo> getBookPriceInfos() {
        return bookPriceInfos;
    }

    @JsonProperty("bookPriceInfos")
    public void setBookPriceInfos(List<BookPriceInfo> bookPriceInfos) {
        this.bookPriceInfos = bookPriceInfos;
    }

    @JsonProperty("siteNo")
    public String getSiteNo() {
        return siteNo;
    }

    @JsonProperty("siteNo")
    public void setSiteNo(String siteNo) {
        this.siteNo = siteNo;
    }

    @JsonProperty("siteName")
    public String getSiteName() {
        return siteName;
    }

    @JsonProperty("siteName")
    public void setSiteName(String siteName) {
        this.siteName = siteName;
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
        sb.append(SiteInfo.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("bookPriceInfos");
        sb.append('=');
        sb.append(((this.bookPriceInfos == null)?"<null>":this.bookPriceInfos));
        sb.append(',');
        sb.append("siteNo");
        sb.append('=');
        sb.append(((this.siteNo == null)?"<null>":this.siteNo));
        sb.append(',');
        sb.append("siteName");
        sb.append('=');
        sb.append(((this.siteName == null)?"<null>":this.siteName));
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
        result = ((result* 31)+((this.siteName == null)? 0 :this.siteName.hashCode()));
        result = ((result* 31)+((this.bookPriceInfos == null)? 0 :this.bookPriceInfos.hashCode()));
        result = ((result* 31)+((this.siteNo == null)? 0 :this.siteNo.hashCode()));
        result = ((result* 31)+((this.additionalProperties == null)? 0 :this.additionalProperties.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof SiteInfo) == false) {
            return false;
        }
        SiteInfo rhs = ((SiteInfo) other);
        return (((((this.siteName == rhs.siteName)||((this.siteName!= null)&&this.siteName.equals(rhs.siteName)))&&((this.bookPriceInfos == rhs.bookPriceInfos)||((this.bookPriceInfos!= null)&&this.bookPriceInfos.equals(rhs.bookPriceInfos))))&&((this.siteNo == rhs.siteNo)||((this.siteNo!= null)&&this.siteNo.equals(rhs.siteNo))))&&((this.additionalProperties == rhs.additionalProperties)||((this.additionalProperties!= null)&&this.additionalProperties.equals(rhs.additionalProperties))));
    }

}
