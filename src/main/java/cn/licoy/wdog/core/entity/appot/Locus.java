package cn.licoy.wdog.core.entity.appot;import com.baomidou.mybatisplus.annotations.TableId;import com.baomidou.mybatisplus.annotations.TableName;import lombok.AllArgsConstructor;import lombok.Builder;import lombok.Data;import java.math.BigDecimal;import lombok.NoArgsConstructor;import java.io.Serializable;import java.util.Date;/** * @author mc * @version Mon Nov 16 16:29:53 2020 */@Data@NoArgsConstructor@AllArgsConstructor@Builder@TableName(value ="data_locus")public class Locus implements Serializable {@TableId   private String id;   private String openId;   private BigDecimal lng;   private BigDecimal lat;   private Date createDate;}