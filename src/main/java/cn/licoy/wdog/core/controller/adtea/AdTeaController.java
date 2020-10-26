package cn.licoy.wdog.core.controller.adtea;

import cn.licoy.wdog.common.annotation.SysLogs;
import cn.licoy.wdog.common.bean.ResponseCode;
import cn.licoy.wdog.common.bean.ResponseResult;
import cn.licoy.wdog.common.controller.QueryController;
import cn.licoy.wdog.common.util.SmsUtilCR;
import cn.licoy.wdog.core.dto.adtea.FindOrderTeaDTO;
import cn.licoy.wdog.core.dto.adtea.OrderTeaAddDTO;
import cn.licoy.wdog.core.dto.adtea.OrderTeaUpdateDTO;
import cn.licoy.wdog.core.dto.adtea.SalelimitUpdateDTO;
import cn.licoy.wdog.core.entity.adtea.OrderTea;
import cn.licoy.wdog.core.entity.adtea.SaleLimit;
import cn.licoy.wdog.core.service.adtea.OrderTeaService;
import cn.licoy.wdog.core.service.adtea.SaleLimitService;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @author Licoy
 * @version 2018/4/28/10:22
 */
@RestController
@RequestMapping(value = "/adtea/orderTea")
@Api(tags = {"AdTea订单管理"})
public class AdTeaController implements QueryController<OrderTea, FindOrderTeaDTO, OrderTeaService>{
    @Autowired
    private OrderTeaService orderTeaService;
    @Autowired
    private SaleLimitService saleLimitService;
    @Override
    public OrderTeaService getService() {
        return orderTeaService;
    }

    @PostMapping(value = {"/cservice"})
    @ApiOperation(value = "分页获取客服订单数据")
    @SysLogs("分页获取客服订单数据")
    @ApiImplicitParam(paramType = "header",name = "Authorization",value = "身份认证Token")
    public ResponseResult cservice(@RequestBody @Validated @ApiParam(value = "订单获取过滤条件") FindOrderTeaDTO findOrderTeaDTO){
        return ResponseResult.e(ResponseCode.OK,orderTeaService.getAllOrdersBySplitPage(findOrderTeaDTO));
    }

    @PostMapping(value = {"/cservice2"})
    @ApiOperation(value = "分页获取客服订单数据")
    @SysLogs("分页获取客服订单数据")
    @ApiImplicitParam(paramType = "header",name = "Authorization",value = "身份认证Token")
    public ResponseResult cservice2(@RequestBody @Validated @ApiParam(value = "订单获取过滤条件") FindOrderTeaDTO findOrderTeaDTO){
        return ResponseResult.e(ResponseCode.OK,orderTeaService.getCServceAllOrdersBySplitPage(findOrderTeaDTO));
    }

    @PostMapping(value = {"/aftersale"})
    @ApiOperation(value = "分页获取客服订单数据")
    @SysLogs("分页获取客服订单数据")
    @ApiImplicitParam(paramType = "header",name = "Authorization",value = "身份认证Token")
    public ResponseResult aftersale(@RequestBody @Validated @ApiParam(value = "订单获取过滤条件") FindOrderTeaDTO findOrderTeaDTO){
        return ResponseResult.e(ResponseCode.OK,orderTeaService.getAfterSaleOrdersBySplitPage(findOrderTeaDTO));
    }

    @PostMapping(value = {"/queryTeasByChennel"})
    @ApiOperation(value = "分页获取客服订单数据")
    @SysLogs("分页获取客服订单数据")
    @ApiImplicitParam(paramType = "header",name = "Authorization",value = "身份认证Token")
    public ResponseResult queryTeasByChennel(@RequestBody @Validated @ApiParam(value = "订单获取过滤条件") FindOrderTeaDTO findOrderTeaDTO){
        return ResponseResult.e(ResponseCode.OK,orderTeaService.queryTeasByChennelOrdersBySplitPage(findOrderTeaDTO));
    }


    @PostMapping(value = {"/express"})
    @ApiOperation(value = "分页获取客服订单数据")
    @SysLogs("分页获取客服订单数据")
    @ApiImplicitParam(paramType = "header",name = "Authorization",value = "身份认证Token")
    public ResponseResult express(@RequestBody @Validated @ApiParam(value = "订单获取过滤条件") FindOrderTeaDTO findOrderTeaDTO){
        return ResponseResult.e(ResponseCode.OK,orderTeaService.getAllOrdersBySplitPage(findOrderTeaDTO));
    }


    @PostMapping(value = {"/add"})
    @ApiOperation(value = "添加订单")
    @SysLogs("添加订单")
    public ResponseResult add(@RequestBody @Validated @ApiParam(value = "订单数据") OrderTeaAddDTO addDTO){
        if( checkInfo(addDTO)==false){
            return ResponseResult.e(ResponseCode.FAIL,"提交内容不完整,请仔细检查再提交 谢谢!");
        }

        if(addDTO.getAmount().intValue()==0 && isUsedFree (saleLimitService,addDTO)==true ){
            return ResponseResult.e(ResponseCode.FAIL,"免费试喝产品,您已领用成功,不可重复领用! ");
        }

        if(addDTO.getAmount().intValue()==0 && isEffictive (saleLimitService)==true ){
            return ResponseResult.e(ResponseCode.FAIL,"免费试喝活动已结束, 欢迎继续关注公众号参与! ");
        }

        OrderTea orderTea = orderTeaService.add(addDTO);
        SmsUtilCR sms = new SmsUtilCR();
        sms.sendsms(orderTea.getMobile(),orderTea.getId(),"");
        return ResponseResult.e(ResponseCode.OK,orderTea);
    }

    private boolean checkInfo(OrderTeaAddDTO addDTO) {
        if("".equals(addDTO.getName()) || addDTO.getName()==null){
            return false;
        }
        if("".equals(addDTO.getMobile()) || addDTO.getMobile()==null){
            return false;
        }
        if("".equals(addDTO.getCity()) || addDTO.getCity()==null || "-1".equals(addDTO.getCity()) ){
            return false;
        }

        if("".equals(addDTO.getProvince()) || addDTO.getProvince()==null || "-1".equals(addDTO.getProvince()) ){
            return false;
        }

        if("".equals(addDTO.getAddress()) || addDTO.getAddress()==null  ){
            return false;
        }
        return true;
    }


    public synchronized boolean isUsedFree(SaleLimitService saleLimitService, OrderTeaAddDTO addDTO){
        EntityWrapper<OrderTea> wrapper = new EntityWrapper<>();
        wrapper.eq("mobile",addDTO.getMobile());
        wrapper.eq("amount", BigDecimal.valueOf(0));
        List<OrderTea> orderTeas = orderTeaService.selectList(wrapper);

        if(orderTeas.size()>0 ){
            return true;
        }
        return false;
    }

    public synchronized boolean isEffictive(SaleLimitService saleLimitService){
        EntityWrapper<SaleLimit> wrapper = new EntityWrapper<>();
        wrapper.eq("statuss","0000");
        SaleLimit saleLimit = saleLimitService.selectOne(wrapper);
        saleLimit.setTempCount(saleLimit.getTempCount()+1);

        if(saleLimit.getTempCount()> saleLimit.getLimitCount() ){
            return true;
        }
        saleLimitService.insertOrUpdate(saleLimit);
        return false;
    }

    @PostMapping(value = {"/update"})
    @ApiOperation(value = "更新订单")
    @SysLogs("更新订单")
    public ResponseResult update(@RequestBody @Validated @ApiParam(value = "订单数据") OrderTeaUpdateDTO updateDTO){
        orderTeaService.update(updateDTO);
        return ResponseResult.e(ResponseCode.OK);
    }

    @PostMapping(value = {"/update/{id}"})
    @ApiOperation(value = "更新订单")
    @SysLogs("更新订单")
    public ResponseResult updateById(@PathVariable("id") @ApiParam(value = "Order ID") String id,
            @RequestBody @Validated @ApiParam(value = "订单数据") OrderTeaUpdateDTO updateDTO){
        OrderTea orderTea = orderTeaService.selectById(id);
        orderTea.setAddress(updateDTO.getAddress());
        orderTea.setArea(updateDTO.getArea());
        orderTea.setCity(updateDTO.getCity());
        orderTea.setProvince(updateDTO.getProvince());
        orderTea.setPaytype(updateDTO.getPaytype());

        if("货到付款".equals(orderTea.getPaytype())){
            orderTea.setPayStatus(null);
        }
        orderTeaService.insertOrUpdate(orderTea);
        return ResponseResult.e(ResponseCode.OK);
    }

    @PostMapping("/remove")
    @ApiOperation("批量删除")
    @ApiImplicitParam(paramType = "header",name = "Authorization",value = "身份认证Token")
    public ResponseResult removeList(@RequestBody @ApiParam("ID集合") List<String> logList){
        orderTeaService.remove(logList);
        return ResponseResult.e(ResponseCode.OK);
    }


    @PostMapping(value = {"/get/{id}"})
    @ApiOperation(value = "根据ID获取订单信息")
    public ResponseResult getById(@PathVariable("id") @ApiParam(value = "Order ID") String id){
        OrderTea orderTea =orderTeaService.selectById(id);
        return ResponseResult.e(ResponseCode.OK,orderTea);
    }

    @PostMapping("/isUpperLimit")
    @ApiOperation("isUpperLimit")
    public ResponseResult isUpperLimit(){
        EntityWrapper<SaleLimit> wrapper = new EntityWrapper<>();
        wrapper.eq("statuss","0000");
        Map<String,Object> mapV = saleLimitService.selectMap(wrapper);
        Integer temp_count = Integer.valueOf(mapV.get("tempCount").toString());
        Integer limit_count = Integer.valueOf(mapV.get("limitCount").toString());
        boolean isTrue = true;
        if( limit_count.intValue() > temp_count.intValue()){
            isTrue = false;
        }
        return ResponseResult.e(ResponseCode.OK,isTrue);
    }

    @PostMapping("/getSaleLimit")
    @ApiOperation("getSaleLimit")
    public ResponseResult getSaleLimit(){
        EntityWrapper<SaleLimit> wrapper = new EntityWrapper<>();
        wrapper.eq("statuss","0000");
        SaleLimit saleLimit = saleLimitService.selectOne(wrapper);
        return ResponseResult.e(ResponseCode.OK,saleLimit);
    }

    @PostMapping("/grabsetting")
    @ApiOperation("grabsetting")
    public ResponseResult grabsetting(@RequestBody @Validated @ApiParam(value = "grabsetting") SalelimitUpdateDTO salelimitUpdateDTO){
        EntityWrapper<SaleLimit> wrapper = new EntityWrapper<>();
        wrapper.eq("statuss","0000");
        SaleLimit saleLimit = saleLimitService.selectOne(wrapper);

        if(salelimitUpdateDTO.getTempCount()!=null)
        saleLimit.setTempCount(salelimitUpdateDTO.getTempCount());

        if(salelimitUpdateDTO.getLimitCount()!=null)
        saleLimit.setLimitCount(salelimitUpdateDTO.getLimitCount());

        saleLimitService.insertOrUpdate(saleLimit);
        return ResponseResult.e(ResponseCode.OK,saleLimit);
    }

}
