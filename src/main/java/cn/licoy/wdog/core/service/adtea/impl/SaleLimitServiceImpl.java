package cn.licoy.wdog.core.service.adtea.impl;

import cn.licoy.wdog.core.dto.adtea.FindSaleLimitDTO;
import cn.licoy.wdog.core.entity.adtea.SaleLimit;
import cn.licoy.wdog.core.mapper.adtea.LimitMapper;
import cn.licoy.wdog.core.service.adtea.SaleLimitService;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author Licoy
 * @version 2018/4/28/9:57
 */
@Service
public class SaleLimitServiceImpl extends ServiceImpl<LimitMapper, SaleLimit> implements SaleLimitService {

    @Override
    public Page<SaleLimit> list(FindSaleLimitDTO findDTO) {
        return null;
    }
}
