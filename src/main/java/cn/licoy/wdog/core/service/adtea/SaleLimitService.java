package cn.licoy.wdog.core.service.adtea;

import cn.licoy.wdog.common.service.QueryService;
import cn.licoy.wdog.core.dto.adtea.FindOrderTeaDTO;
import cn.licoy.wdog.core.dto.adtea.FindSaleLimitDTO;
import cn.licoy.wdog.core.dto.adtea.OrderTeaAddDTO;
import cn.licoy.wdog.core.dto.adtea.OrderTeaUpdateDTO;
import cn.licoy.wdog.core.entity.adtea.OrderTea;
import cn.licoy.wdog.core.entity.adtea.SaleLimit;
import cn.licoy.wdog.core.vo.adtea.OrderTeaVo;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * @author Licoy
 * @version 2018/4/28/9:56
 */
public interface SaleLimitService extends IService<SaleLimit>,QueryService<SaleLimit, FindSaleLimitDTO>
{
}
