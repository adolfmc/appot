package cn.licoy.wdog.core.vo.appot;import lombok.Data;import java.util.Date;import com.fasterxml.jackson.annotation.JsonFormat;/** * @author mc * @version Sun Dec 06 12:18:16 2020 */@Datapublic class VenueDetailVo{   private Integer id;   private String venueId;   private String sportType;   private String siteId;   private String productId;   private String isHalf;   @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")   private Date createDate;}