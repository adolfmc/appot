package cn.licoy.wdog.core.service.adtea.impl;

import cn.licoy.wdog.common.bean.ResponseCode;
import cn.licoy.wdog.common.exception.RequestException;
import cn.licoy.wdog.core.dto.adtea.FindOrderTeaDTO;
import cn.licoy.wdog.core.dto.adtea.OrderTeaAddDTO;
import cn.licoy.wdog.core.dto.adtea.OrderTeaUpdateDTO;
import cn.licoy.wdog.core.entity.adtea.OrderTea;
import cn.licoy.wdog.core.mapper.adtea.OrderTeaMapper;
import cn.licoy.wdog.core.service.adtea.OrderTeaService;
import cn.licoy.wdog.core.vo.adtea.OrderTeaVo;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author Licoy
 * @version 2018/4/28/9:57
 */
@Service
public class OrderTeaServiceImpl extends ServiceImpl<OrderTeaMapper, OrderTea> implements OrderTeaService {

    @Override
    public Page<OrderTea> list(FindOrderTeaDTO findOrderTeaDTO) {
        EntityWrapper<OrderTea> wrapper = new EntityWrapper<>();
        wrapper.notIn("statuss","9999");
        wrapper.orderBy("create_date",findOrderTeaDTO.getAsc());
        return this.selectPage(new Page<>(findOrderTeaDTO.getPage(),findOrderTeaDTO.getPageSize()),wrapper);
    }


    @Override
    public OrderTea add(OrderTeaAddDTO addDTO) {
        OrderTea orderTea = new OrderTea();
        try {
            BeanUtils.copyProperties(addDTO,orderTea);
            orderTea.setCreateDate(new Date());
            this.insert(orderTea);
        }catch (Exception e){
            throw RequestException.fail("添加用户失败",e);
        }
        return orderTea;
    }

    @Override
    public void update(OrderTeaUpdateDTO updateDTO) {
        try {
            //0000初始0001已发货0002已收货0003申请退货0004退货入库9999置为无效0005弃单0006待电联0007待发货
            OrderTea orderTea = this.selectById(updateDTO.getId());
            orderTea.setStatuss(updateDTO.getStatuss());
            if("0007".equals(updateDTO.getStatuss()) ){
                orderTea.setDlianDate(new Date());
            }
            if("0001".equals(updateDTO.getStatuss())){
                orderTea.setFahuoDate(new Date());
                orderTea.setExpressCode(updateDTO.getExpressCode());
                orderTea.setExpressType(updateDTO.getExpressName());
            }

            if("0000".equals(updateDTO.getStatuss())){
                orderTea.setAddress(updateDTO.getAddress());
                orderTea.setProvince(updateDTO.getProvince());
                orderTea.setCity(updateDTO.getCity());
                orderTea.setArea(updateDTO.getArea());
            }

            if("0003".equals(updateDTO.getStatuss())){
                orderTea.setTuihuoDate(new Date());
            }
            if("0004".equals(updateDTO.getStatuss())){
                orderTea.setTuihuorukuDate(new Date());
            }
            if("货到付款".equals(orderTea.getPaytype())){
                orderTea.setPayStatus(null);
            }

            orderTea.setPaytype(orderTea.getPaytype());
            this.updateById(orderTea);
        }catch (Exception e){
            throw RequestException.fail("更新用户失败",e);
        }
    }

    @Override
    public void remove(List<String> idList) {
        try {
            this.deleteBatchIds(idList);
        }catch (Exception e){
            throw new RequestException(ResponseCode.FAIL.code,"批量删除日志失败",e);
        }
    }

    @Override
    public Page<OrderTea> getAllOrdersBySplitPage(FindOrderTeaDTO findOrderTeaDTO) {
        EntityWrapper<OrderTea> wrapper = new EntityWrapper<>();
        wrapper.orderBy("create_date",findOrderTeaDTO.getAsc());
        if(findOrderTeaDTO.getMobile()!=null && !"".equals(findOrderTeaDTO.getMobile())){
            wrapper.like("mobile",findOrderTeaDTO.getMobile());
        }
        if(findOrderTeaDTO.getStatuss()!=null && !"".equals(findOrderTeaDTO.getStatuss())){
            wrapper.eq("statuss",findOrderTeaDTO.getStatuss());
        }
        if(findOrderTeaDTO.getPaytype()!=null && !"".equals(findOrderTeaDTO.getPaytype())){
            wrapper.eq("paytype",findOrderTeaDTO.getPaytype());
        }

        if(findOrderTeaDTO.getStartTime()!=null && !"".equals(findOrderTeaDTO.getStartTime())  && findOrderTeaDTO.getEndTime()!=null && !"".equals(findOrderTeaDTO.getEndTime())){
            wrapper.between("create_date",findOrderTeaDTO.getStartTime() ,findOrderTeaDTO.getEndTime()) ;
        }

        return this.selectPage(new Page<>(findOrderTeaDTO.getPage(),findOrderTeaDTO.getPageSize()), wrapper);
    }

    @Override
    public Page<OrderTea> getCServceAllOrdersBySplitPage(FindOrderTeaDTO findOrderTeaDTO) {
        EntityWrapper<OrderTea> wrapper = new EntityWrapper<>();
        wrapper.orderBy("create_date",findOrderTeaDTO.getAsc());
        if(findOrderTeaDTO.getMobile()!=null && !"".equals(findOrderTeaDTO.getMobile())){
            wrapper.like("mobile",findOrderTeaDTO.getMobile());
        }
        if(findOrderTeaDTO.getStatuss()!=null && !"".equals(findOrderTeaDTO.getStatuss())){
            wrapper.eq("statuss",findOrderTeaDTO.getStatuss());
        }else{
            wrapper.in("statuss",new String[]{"0009","0010","0008","0006"});
        }
        if(findOrderTeaDTO.getPaytype()!=null && !"".equals(findOrderTeaDTO.getPaytype())){
            wrapper.eq("paytype",findOrderTeaDTO.getPaytype());
        }

        if(findOrderTeaDTO.getStartTime()!=null && !"".equals(findOrderTeaDTO.getStartTime())  && findOrderTeaDTO.getEndTime()!=null && !"".equals(findOrderTeaDTO.getEndTime())){
            wrapper.between("create_date",findOrderTeaDTO.getStartTime() ,findOrderTeaDTO.getEndTime()) ;
        }

        return this.selectPage(new Page<>(findOrderTeaDTO.getPage(),findOrderTeaDTO.getPageSize()), wrapper);
    }

    @Override
    public Page<OrderTea> queryTeasByChennelOrdersBySplitPage(FindOrderTeaDTO findOrderTeaDTO) {
        EntityWrapper<OrderTea> wrapper = new EntityWrapper<>();
        wrapper.orderBy("create_date",findOrderTeaDTO.getAsc());

        if(findOrderTeaDTO.getStatuss()!=null && !"".equals(findOrderTeaDTO.getStatuss())){
            wrapper.eq("statuss",findOrderTeaDTO.getStatuss());
        }

        if(findOrderTeaDTO.getPaytype()!=null && !"".equals(findOrderTeaDTO.getPaytype())){
            wrapper.eq("paytype",findOrderTeaDTO.getPaytype());
        }

        wrapper.eq("channel",findOrderTeaDTO.getChannel());

        if(findOrderTeaDTO.getStartTime()!=null && !"".equals(findOrderTeaDTO.getStartTime())  && findOrderTeaDTO.getEndTime()!=null && !"".equals(findOrderTeaDTO.getEndTime())){
            wrapper.between("create_date",findOrderTeaDTO.getStartTime() ,findOrderTeaDTO.getEndTime()) ;
        }

        return this.selectPage(new Page<>(findOrderTeaDTO.getPage(),findOrderTeaDTO.getPageSize()), wrapper);
    }




    @Override
    public Page<OrderTea> getAfterSaleOrdersBySplitPage(FindOrderTeaDTO findOrderTeaDTO) {
        EntityWrapper<OrderTea> wrapper = new EntityWrapper<>();
        wrapper.orderBy("create_date",findOrderTeaDTO.getAsc());
        if(findOrderTeaDTO.getMobile()!=null && !"".equals(findOrderTeaDTO.getMobile())){
            //wrapper.eq("mobile",findOrderTeaDTO.getMobile());
            wrapper.like("mobile",findOrderTeaDTO.getMobile());
        }

        wrapper.in("statuss",new String[]{"0001","0002","0003","0004",""});
        return this.selectPage(new Page<>(findOrderTeaDTO.getPage(),findOrderTeaDTO.getPageSize()), wrapper);
    }
}
