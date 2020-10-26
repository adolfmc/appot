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
public class OrderTeaUpdateDTO {
    @NotNull(message = "id不能为空")
    private String id;
    @NotNull(message = "statuss不能为空")
    private String statuss;
    private String expressName;
    private String expressCode;
    private String province;
    private String city;
    private String area;
    private String address;
    private String paytype;
    private Integer sellcount;
    private BigDecimal amount;

}
