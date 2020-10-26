package cn.licoy.wdog.core.dto.adtea;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author Licoy
 * @version 2018/4/24/16:40
 */
@Data
public class SalelimitUpdateDTO {
    private String statuss;
    private Integer limitCount;
    private Integer tempCount;

}
