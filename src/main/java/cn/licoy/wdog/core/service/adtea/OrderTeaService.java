package cn.licoy.wdog.core.service.adtea;

import cn.licoy.wdog.common.service.QueryService;
import cn.licoy.wdog.core.dto.adtea.FindOrderTeaDTO;
import cn.licoy.wdog.core.dto.adtea.OrderTeaAddDTO;
import cn.licoy.wdog.core.dto.adtea.OrderTeaUpdateDTO;
import cn.licoy.wdog.core.entity.adtea.OrderTea;
import cn.licoy.wdog.core.vo.adtea.OrderTeaVo;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * @author Licoy
 * @version 2018/4/28/9:56
 */
public interface OrderTeaService extends IService<OrderTea>,QueryService<OrderTea, FindOrderTeaDTO>
{

    Page<OrderTea> list(FindOrderTeaDTO findOrderTeaDTO);

    /**
     * 添加OrderTea
     * @param addDTO OrderTea数据DTO
     */
    OrderTea add(OrderTeaAddDTO addDTO);

    void update(OrderTeaUpdateDTO updateDTO);

    void remove(List<String> idList);

    Page<OrderTea> getAllOrdersBySplitPage(FindOrderTeaDTO findOrderTeaDTO);

    Page<OrderTea> getCServceAllOrdersBySplitPage(FindOrderTeaDTO findOrderTeaDTO);

    Page<OrderTea> getAfterSaleOrdersBySplitPage(FindOrderTeaDTO findOrderTeaDTO);

    Page<OrderTea> queryTeasByChennelOrdersBySplitPage(FindOrderTeaDTO findOrderTeaDTO);
}
