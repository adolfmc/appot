package cn.licoy.wdog.core.controller.appot;import cn.licoy.wdog.common.annotation.SysLogs;import io.swagger.annotations.ApiImplicitParam;import cn.licoy.wdog.common.bean.ResponseCode;import cn.licoy.wdog.common.bean.ResponseResult;import cn.licoy.wdog.common.controller.AppotBaseController;import cn.licoy.wdog.core.dto.appot.User2AddDTO;import cn.licoy.wdog.core.dto.appot.FindUser2DTO;import cn.licoy.wdog.core.dto.appot.User2UpdateDTO;import cn.licoy.wdog.core.service.appot.User2Service;import io.swagger.annotations.Api;import com.alibaba.fastjson.JSONObject;import io.swagger.annotations.ApiOperation;import io.swagger.annotations.ApiParam;import org.springframework.beans.factory.annotation.Autowired;import org.springframework.validation.annotation.Validated;import org.springframework.web.bind.annotation.*;/** * @author mc * @version Tue Dec 22 22:00:21 2020 */@RestControllerpublic class User2Controller  extends AppotBaseController{   @Autowired   private User2Service user2Service;   public User2Service getUser2Service() {      return user2Service;   }   @PostMapping(value = {"/list"})
    @ApiOperation(value = "分页获取User2数据")
    @SysLogs("分页获取User2数据")
    public ResponseResult get(@RequestBody @Validated @ApiParam(value = "User2获取过滤条件") FindUser2DTO findUser2DTO){
        return ResponseResult.e(ResponseCode.OK,user2Service.getAllUser2BySplitPage(findUser2DTO));
    }

    @PostMapping(value = {"/get/id/{id}"})
    @ApiOperation(value = "根据ID获取User2信息")
    @SysLogs("根据ID获取User2信息")
    public ResponseResult getById(@PathVariable("id") @ApiParam(value = "User2ID") String id){
        return ResponseResult.e(ResponseCode.OK,user2Service.findUser2ById(id));
    }

    @PostMapping(value = {"/lock/{id}"})
    @ApiOperation(value = "锁定User2")
    @SysLogs("锁定User2")
    public ResponseResult lock(@PathVariable("id") @ApiParam(value = "User2标识ID") String id){
        user2Service.statusChange(id, 0);
        return ResponseResult.e(ResponseCode.OK);
    }

    @PostMapping(value = {"/remove/{id}"})
    @ApiOperation(value = "删除User2")
    @SysLogs("删除User2")
    public ResponseResult remove(@PathVariable("id") @ApiParam(value = "User2标识ID") String id){
        user2Service.removeUser2(id);
        return ResponseResult.e(ResponseCode.OK);
    }

    @PostMapping(value = {"/add"})
    @ApiOperation(value = "添加User2")
    @SysLogs("添加User2")
    public ResponseResult add(@RequestBody @Validated @ApiParam(value = "User2数据") User2AddDTO addDTO){
        user2Service.add(addDTO);
        return ResponseResult.e(ResponseCode.OK);
    }

    @PostMapping(value = {"/update/{id}"})
    @ApiOperation(value = "更新User2")
    @SysLogs("更新User2")
    public ResponseResult update(@PathVariable("id") @ApiParam(value = "User2标识ID") String id,
                                 @RequestBody @Validated @ApiParam(value = "User2数据") User2UpdateDTO updateDTO){
        user2Service.update(id,updateDTO);
        return ResponseResult.e(ResponseCode.OK);
    }
}