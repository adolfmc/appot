package cn.licoy.wdog.core.entity.appot;import com.baomidou.mybatisplus.annotations.TableId;import com.baomidou.mybatisplus.annotations.TableName;import lombok.AllArgsConstructor;import lombok.Builder;import lombok.Data;import java.math.BigDecimal;import lombok.NoArgsConstructor;import java.io.Serializable;import java.util.Date;/** * @author mc * @version Fri Sep 25 16:39:55 2020 */@Data@NoArgsConstructor@AllArgsConstructor@Builder@TableName(value ="biz_venue_login")public class VenueLogin implements Serializable {@TableId   private String id;   private String venueId;   private String venueName;   private String url;   private String loginName;   private String loginPwd;   private Date createDate;}