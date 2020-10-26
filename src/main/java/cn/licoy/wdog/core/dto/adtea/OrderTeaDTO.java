package cn.licoy.wdog.core.dto.adtea;

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
public class OrderTeaDTO {

    @NotBlank(message = "id")
    private String id;

    @NotNull(message = "name")
    private String name;

    @NotNull(message = "mobile")
    private String mobile;

    @NotBlank(message = "province")
    private String province;

    @NotNull(message = "city")
    private String city;

    @NotNull(message = "area")
    private String area;

    @NotNull(message = "address")
    private String address;

    @NotNull(message = "paytype")
    private String paytype;

    private String message;

    @NotNull(message = "sellpackage")
    private String sellpackage;

    @NotNull(message = "sellcount")
    private Integer sellcount;

    @NotNull(message = "amount")
    private BigDecimal amount;

    @NotNull(message = "create_date")
    private Date createDate;

    @NotNull(message = "statuss")
    private String statuss;

    @NotNull(message = "expressCode")
    private String expressCode;

    @NotNull(message = "expressType")
    private String expressType;

    @NotNull
    private Date fahuoDate;

    private Date shouhuoDate;

    private Date tuihuoDate;

    private Date tuihuorukuDate;

    private Date dlianDate;


}
