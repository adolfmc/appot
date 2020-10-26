package cn.licoy.wdog.core.entity.adtea;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Licoy
 * @version 2018/4/27/17:20
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName(value = "biz_order_tea")
public class OrderTea implements Serializable {

    @TableId
    private String id;
    private String name;
    private String mobile;
    private String province;
    private String city;
    private String area;
    private String address;
    private String paytype;
    private String message;
    private String sellpackage;
    private Integer sellcount;
    private BigDecimal amount;
    private Date createDate;
    private String statuss;
    private String expressCode;
    private String expressType;
    private Date fahuoDate;
    private Date shouhuoDate;
    private Date tuihuoDate;
    private Date tuihuorukuDate;
    private Date dlianDate;
    private String outTradeNo;
    private Date tradeDate;
    private String channel;
    private String payStatus;
}
