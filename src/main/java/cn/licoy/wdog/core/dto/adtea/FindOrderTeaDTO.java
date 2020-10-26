package cn.licoy.wdog.core.dto.adtea;

import cn.licoy.wdog.core.dto.SplitPageDTO;
import lombok.Data;

/**
 * @author Licoy
 * @version 2018/4/28/10:21
 */
@Data
public class FindOrderTeaDTO extends SplitPageDTO {
    private String id;
    private String mobile;
    private String statuss;
    private String paytype;
    private String startTime;
    private String endTime;
    private String channel;
}
