package cn.licoy.wdog.core.dto.appot;import org.hibernate.validator.constraints.NotBlank;import com.fasterxml.jackson.annotation.JsonFormat;import lombok.Data;import java.util.Date;/** * @author mc * @version Sun Dec 06 12:18:16 2020 */@Datapublic class VenueDetailAddDTO{   @NotBlank(message = "id")   private Integer id;   @NotBlank(message = "venueId")   private String venueId;   @NotBlank(message = "sportType")   private String sportType;   @NotBlank(message = "siteId")   private String siteId;   @NotBlank(message = "productId")   private String productId;   @NotBlank(message = "isHalf")   private String isHalf;   @NotBlank(message = "createDate")   @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")   private Date createDate;}