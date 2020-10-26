package cn.licoy.wdog.core.vo.adtea;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author licoy.cn
 * @version 2018/4/22
 */
@Data
public class OrderTeaVo {
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
    private Date create_date;
    private String statuss;
    private String express_code;
    private String express_type;
    private Date fahuo_date;
    private Date shouhuo_date;
    private Date tuihuo_date;
    private Date tuihuoruku_date;
}
