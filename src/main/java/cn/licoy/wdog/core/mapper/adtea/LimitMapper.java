package cn.licoy.wdog.core.mapper.adtea;

import cn.licoy.wdog.core.entity.adtea.OrderTea;
import cn.licoy.wdog.core.entity.adtea.SaleLimit;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author Licoy
 * @version 2018/4/28/9:56
 */
@Mapper
@Repository
public interface LimitMapper extends BaseMapper<SaleLimit> {
}
