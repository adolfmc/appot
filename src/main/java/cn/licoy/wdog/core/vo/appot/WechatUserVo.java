package cn.licoy.wdog.core.vo.appot;import lombok.Data;import java.util.Date;import com.fasterxml.jackson.annotation.JsonFormat;/** * @author mc * @version Tue Dec 15 19:38:18 2020 */@Datapublic class WechatUserVo{   private String id;   private String country;   private String province;   private String city;   private String openid;   private String mobile;   private String sex;   private String nickname;   private String headimgurl;   private String language;   private String privilege;   @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")   private Date createDate;   private String fromVenueId;   private String venueName;   private String idcard;   private String intro;}