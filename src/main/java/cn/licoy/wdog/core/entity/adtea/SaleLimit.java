package cn.licoy.wdog.core.entity.adtea;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName(value = "biz_limit")
public class SaleLimit {
    @TableId
    private String id;
    private int limitCount;
    private int tempCount;
    private Date createDate;
    private String memo;
}
