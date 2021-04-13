package cn.licoy.wdog.core.controller.appot;

import cn.licoy.wdog.common.annotation.SysLogs;
import cn.licoy.wdog.core.dto.appot.MallorderAddDTO;
import cn.licoy.wdog.core.entity.appot.Mallorder;
import cn.licoy.wdog.core.service.appot.MallorderService;
import com.alibaba.fastjson.JSONArray;
import io.swagger.annotations.ApiImplicitParam;
import cn.licoy.wdog.common.bean.ResponseCode;
import cn.licoy.wdog.common.bean.ResponseResult;
import cn.licoy.wdog.common.controller.AppotBaseController;
import cn.licoy.wdog.core.dto.appot.MallorderdetailsAddDTO;
import cn.licoy.wdog.core.dto.appot.FindMallorderdetailsDTO;
import cn.licoy.wdog.core.dto.appot.MallorderdetailsUpdateDTO;
import cn.licoy.wdog.core.service.appot.MallorderdetailsService;
import io.swagger.annotations.Api;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

/**
 * @author mc
 * @version Mon Apr 12 01:16:55 2021
 */
@RestController
@RequestMapping(value = {"mallorderdetails"})
public class MallorderdetailsController  extends AppotBaseController{

   @Autowired
   private MallorderdetailsService mallorderdetailsService;
   public MallorderdetailsService getMallorderdetailsService() {
      return mallorderdetailsService;
   }

    @Autowired
    private MallorderService mallorderService;
    public MallorderService getMallorderService() {
        return mallorderService;
    }

   @PostMapping(value = {"/list"})
    @ApiOperation(value = "分页获取Mallorderdetails数据")
    @SysLogs("分页获取Mallorderdetails数据")
    public ResponseResult get(@RequestBody @Validated @ApiParam(value = "Mallorderdetails获取过滤条件") FindMallorderdetailsDTO findMallorderdetailsDTO){
        return ResponseResult.e(ResponseCode.OK,mallorderdetailsService.getAllMallorderdetailsBySplitPage(findMallorderdetailsDTO));
    }

    @PostMapping(value = {"/get/id/{id}"})
    @ApiOperation(value = "根据ID获取Mallorderdetails信息")
    @SysLogs("根据ID获取Mallorderdetails信息")
    public ResponseResult getById(@PathVariable("id") @ApiParam(value = "MallorderdetailsID") String id){
        return ResponseResult.e(ResponseCode.OK,mallorderdetailsService.findMallorderdetailsById(id));
    }

    @PostMapping(value = {"/lock/{id}"})
    @ApiOperation(value = "锁定Mallorderdetails")
    @SysLogs("锁定Mallorderdetails")
    public ResponseResult lock(@PathVariable("id") @ApiParam(value = "Mallorderdetails标识ID") String id){
        mallorderdetailsService.statusChange(id, 0);
        return ResponseResult.e(ResponseCode.OK);
    }

    @PostMapping(value = {"/remove/{id}"})
    @ApiOperation(value = "删除Mallorderdetails")
    @SysLogs("删除Mallorderdetails")
    public ResponseResult remove(@PathVariable("id") @ApiParam(value = "Mallorderdetails标识ID") String id){
        mallorderdetailsService.removeMallorderdetails(id);
        return ResponseResult.e(ResponseCode.OK);
    }



    @PostMapping(value = {"/add"})
    @ApiOperation(value = "添加Mallorderdetails")
    @SysLogs("添加Mallorderdetails")
    public ResponseResult add(@RequestBody @Validated @ApiParam(value = "Mallorderdetails数据") MallorderdetailsAddDTO addDTO){
        Map map = (Map) JSONObject.parse(addDTO.getData());
        JSONArray jsonArray = (JSONArray) map.get("data");
        JSONArray jsonArray2 = (JSONArray) map.get("data");
        Iterator iterator2  = jsonArray2.iterator();
        BigDecimal bamount = new BigDecimal(0.0);
        String uuid = UUID.randomUUID().toString();
        while (iterator2.hasNext()){
            JSONObject mapv = (JSONObject)iterator2.next();
            String price = mapv.getString("price");
            String name = mapv.getString("name");
            String value = mapv.getString("value");
            bamount = bamount.add(new BigDecimal(price));
        }

        MallorderAddDTO order = new MallorderAddDTO();
        order.setCreateDate(new Date());
        order.setMobile(addDTO.getMobile());
        order.setNumber(jsonArray2.size());
        order.setPayType(addDTO.getPayType());
        order.setStatus("付款确认中");
        order.setAmount(bamount);
        order.setId(uuid);
        Mallorder mallorder = mallorderService.add(order);



        Iterator iterator  = jsonArray.iterator();
        while (iterator.hasNext()){
            JSONObject mapv = (JSONObject)iterator.next();
            String price = mapv.getString("price");
            String name = mapv.getString("name");
            String value = mapv.getString("value");

            addDTO.setCreateDate(new Date());
            addDTO.setProductName(name);
            addDTO.setPrice(new BigDecimal( price));
            addDTO.setOrderId(uuid);
            addDTO.setProductDesc(value);
            mallorderdetailsService.add(addDTO);
        }


        return ResponseResult.e(ResponseCode.OK,uuid);
    }

    @PostMapping(value = {"/update/{id}"})
    @ApiOperation(value = "更新Mallorderdetails")
    @SysLogs("更新Mallorderdetails")
    public ResponseResult update(@PathVariable("id") @ApiParam(value = "Mallorderdetails标识ID") String id,
                                 @RequestBody @Validated @ApiParam(value = "Mallorderdetails数据") MallorderdetailsUpdateDTO updateDTO){
        mallorderdetailsService.update(id,updateDTO);
        return ResponseResult.e(ResponseCode.OK);
    }


}
