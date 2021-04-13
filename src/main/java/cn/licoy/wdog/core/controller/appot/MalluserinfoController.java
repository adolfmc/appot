package cn.licoy.wdog.core.controller.appot;

import cn.licoy.wdog.common.annotation.SysLogs;
import io.swagger.annotations.ApiImplicitParam;
import cn.licoy.wdog.common.bean.ResponseCode;
import cn.licoy.wdog.common.bean.ResponseResult;
import cn.licoy.wdog.common.controller.AppotBaseController;
import cn.licoy.wdog.core.dto.appot.MalluserinfoAddDTO;
import cn.licoy.wdog.core.dto.appot.FindMalluserinfoDTO;
import cn.licoy.wdog.core.dto.appot.MalluserinfoUpdateDTO;
import cn.licoy.wdog.core.service.appot.MalluserinfoService;
import io.swagger.annotations.Api;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author mc
 * @version Mon Apr 12 01:16:55 2021
 */
@RestController
@RequestMapping(value = {"malluserinfo"})
public class MalluserinfoController  extends AppotBaseController{

   @Autowired
   private MalluserinfoService malluserinfoService;
   public MalluserinfoService getMalluserinfoService() {
      return malluserinfoService;
   }
   @PostMapping(value = {"/list"})
    @ApiOperation(value = "分页获取Malluserinfo数据")
    @SysLogs("分页获取Malluserinfo数据")
    public ResponseResult get(@RequestBody @Validated @ApiParam(value = "Malluserinfo获取过滤条件") FindMalluserinfoDTO findMalluserinfoDTO){
        return ResponseResult.e(ResponseCode.OK,malluserinfoService.getAllMalluserinfoBySplitPage(findMalluserinfoDTO));
    }

    @PostMapping(value = {"/get/id/{id}"})
    @ApiOperation(value = "根据ID获取Malluserinfo信息")
    @SysLogs("根据ID获取Malluserinfo信息")
    public ResponseResult getById(@PathVariable("id") @ApiParam(value = "MalluserinfoID") String id){
        return ResponseResult.e(ResponseCode.OK,malluserinfoService.findMalluserinfoById(id));
    }

    @PostMapping(value = {"/lock/{id}"})
    @ApiOperation(value = "锁定Malluserinfo")
    @SysLogs("锁定Malluserinfo")
    public ResponseResult lock(@PathVariable("id") @ApiParam(value = "Malluserinfo标识ID") String id){
        malluserinfoService.statusChange(id, 0);
        return ResponseResult.e(ResponseCode.OK);
    }

    @PostMapping(value = {"/remove/{id}"})
    @ApiOperation(value = "删除Malluserinfo")
    @SysLogs("删除Malluserinfo")
    public ResponseResult remove(@PathVariable("id") @ApiParam(value = "Malluserinfo标识ID") String id){
        malluserinfoService.removeMalluserinfo(id);
        return ResponseResult.e(ResponseCode.OK);
    }

    @PostMapping(value = {"/add"})
    @ApiOperation(value = "添加Malluserinfo")
    @SysLogs("添加Malluserinfo")
    public ResponseResult add(@RequestBody @Validated @ApiParam(value = "Malluserinfo数据") MalluserinfoAddDTO addDTO){
        addDTO.setCreateDate(new Date());
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("mobile",addDTO.getMobile()) ;
        if(malluserinfoService.selectByMap(map).isEmpty() ){
            malluserinfoService.add(addDTO);
        }
        return ResponseResult.e(ResponseCode.OK);
    }

    @PostMapping(value = {"/update/{id}"})
    @ApiOperation(value = "更新Malluserinfo")
    @SysLogs("更新Malluserinfo")
    public ResponseResult update(@PathVariable("id") @ApiParam(value = "Malluserinfo标识ID") String id,
                                 @RequestBody @Validated @ApiParam(value = "Malluserinfo数据") MalluserinfoUpdateDTO updateDTO){
        malluserinfoService.update(id,updateDTO);
        return ResponseResult.e(ResponseCode.OK);
    }
}
