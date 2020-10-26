package cn.licoy.wdog.core.dto.adtea;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Licoy
 * @version 2018/4/24/16:40
 */
@Data
public class OrderTeaAddDTO {

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

    private Date createDate;

    @NotNull(message = "statuss")
    private String statuss;

    private String expressCcode;

    private String expressType;

    private Date fahuoDate;

    private Date shouhuoDate;

    private Date tuihuoDate;

    private Date tuihuorukuDate;

    private Date dlianDate;

    private String channel;
}
