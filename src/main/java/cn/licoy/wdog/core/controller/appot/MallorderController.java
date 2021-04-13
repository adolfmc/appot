package cn.licoy.wdog.core.controller.appot;

import cn.licoy.wdog.common.annotation.SysLogs;
import cn.licoy.wdog.core.entity.appot.Mallorder;
import cn.licoy.wdog.core.entity.appot.OrderDetail;
import cn.licoy.wdog.core.service.appot.MallorderdetailsService;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import io.swagger.annotations.ApiImplicitParam;
import cn.licoy.wdog.common.bean.ResponseCode;
import cn.licoy.wdog.common.bean.ResponseResult;
import cn.licoy.wdog.common.controller.AppotBaseController;
import cn.licoy.wdog.core.dto.appot.MallorderAddDTO;
import cn.licoy.wdog.core.dto.appot.FindMallorderDTO;
import cn.licoy.wdog.core.dto.appot.MallorderUpdateDTO;
import cn.licoy.wdog.core.service.appot.MallorderService;
import io.swagger.annotations.Api;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author mc
 * @version Mon Apr 12 01:16:55 2021
 */
@RestController
@RequestMapping(value = {"mallorder"})
public class MallorderController  extends AppotBaseController{
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
    @ApiOperation(value = "分页获取Mallorder数据")
    @SysLogs("分页获取Mallorder数据")
    public ResponseResult get(@RequestBody @Validated @ApiParam(value = "Mallorder获取过滤条件") FindMallorderDTO findMallorderDTO){
        return ResponseResult.e(ResponseCode.OK,mallorderService.getAllMallorderBySplitPage(findMallorderDTO));
    }

    @RequestMapping(value = {"/getList"})
    public ResponseResult getList(HttpServletRequest request, HttpServletResponse response ,String mobile){

        EntityWrapper<Mallorder> wrapper = new EntityWrapper<>();
        if(mobile!=null){
            wrapper.eq("mobile",mobile );
        }

        wrapper.orderBy("create_date",false);
        List<Mallorder> orders = mallorderService.selectList(wrapper);

        for(Mallorder mallorder:orders){
            Map<String,Object> map = new HashMap<String,Object>();
            map.put("order_id",mallorder.getId()) ;
            mallorder.setMallorderdetails(mallorderdetailsService.selectByMap(map) );
        }
        return ResponseResult.e(ResponseCode.OK,orders);
    }

    @PostMapping(value = {"/get/id/{id}"})
    @ApiOperation(value = "根据ID获取Mallorder信息")
    @SysLogs("根据ID获取Mallorder信息")
    public ResponseResult getById(@PathVariable("id") @ApiParam(value = "MallorderID") String id){
        return ResponseResult.e(ResponseCode.OK,mallorderService.findMallorderById(id));
    }

    @PostMapping(value = {"/lock/{id}"})
    @ApiOperation(value = "锁定Mallorder")
    @SysLogs("锁定Mallorder")
    public ResponseResult lock(@PathVariable("id") @ApiParam(value = "Mallorder标识ID") String id){
        mallorderService.statusChange(id, 0);
        return ResponseResult.e(ResponseCode.OK);
    }

    @PostMapping(value = {"/remove/{id}"})
    @ApiOperation(value = "删除Mallorder")
    @SysLogs("删除Mallorder")
    public ResponseResult remove(@PathVariable("id") @ApiParam(value = "Mallorder标识ID") String id){
        mallorderService.removeMallorder(id);
        return ResponseResult.e(ResponseCode.OK);
    }

    @PostMapping(value = {"/add"})
    @ApiOperation(value = "添加Mallorder")
    @SysLogs("添加Mallorder")
    public ResponseResult add(@RequestBody @Validated @ApiParam(value = "Mallorder数据") MallorderAddDTO addDTO){
        mallorderService.add(addDTO);
        return ResponseResult.e(ResponseCode.OK);
    }

    @PostMapping(value = {"/update/{id}"})
    @ApiOperation(value = "更新Mallorder")
    @SysLogs("更新Mallorder")
    public ResponseResult update(@PathVariable("id") @ApiParam(value = "Mallorder标识ID") String id,
                                 @RequestBody @Validated @ApiParam(value = "Mallorder数据") MallorderUpdateDTO updateDTO){


       mallorderService.update(id,updateDTO);

        return ResponseResult.e(ResponseCode.OK);
    }
}
