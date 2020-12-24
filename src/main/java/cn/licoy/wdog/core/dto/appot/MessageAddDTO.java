package cn.licoy.wdog.core.dto.appot;import org.hibernate.validator.constraints.NotBlank;import com.fasterxml.jackson.annotation.JsonFormat;import lombok.Data;import java.util.Date;/** * @author mc * @version Sat Dec 19 19:26:24 2020 */@Datapublic class MessageAddDTO{   @NotBlank(message = "id")   private String id;   @NotBlank(message = "mobile")   private String mobile;   @NotBlank(message = "userId")   private String userId;   @NotBlank(message = "info")   private String info;   @NotBlank(message = "createDate")   @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")   private Date createDate;   @NotBlank(message = "title")   private String title;   @NotBlank(message = "isRead")   private String isRead;}