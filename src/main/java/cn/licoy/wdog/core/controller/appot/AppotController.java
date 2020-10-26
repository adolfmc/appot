package cn.licoy.wdog.core.controller.appot;

import cn.licoy.wdog.common.bean.ResponseCode;
import cn.licoy.wdog.common.bean.ResponseResult;
import cn.licoy.wdog.common.controller.AppotBaseController;
import cn.licoy.wdog.common.util.AppotUtils;
import cn.licoy.wdog.common.util.SmsUtilTX;
import cn.licoy.wdog.core.entity.appot.WechatUser;
import cn.licoy.wdog.core.service.appot.WechatUserService;
import org.crazycake.shiro.RedisManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

/**
 * @author mc
 * @version 2020/09/17
 */
@RestController
public class AppotController extends AppotBaseController {
    @Autowired
    private RedisManager redisManager;
    @Autowired
    private WechatUserService wechatUserService;


    @RequestMapping(value = {"/MP_verify_JhO2WS095phwvOSc.txt"})
    public String MP_verify_JhO2WS095phwvOSc(HttpServletRequest request, HttpServletResponse response,String datee) throws IOException {
        return "JhO2WS095phwvOSc" ;
    }


    @RequestMapping(value = {"/getVerCode"})
    public ResponseResult getVerCode(HttpServletRequest request, HttpServletResponse response, String mobile) throws IOException {
        String key = mobile + AppotUtils.getToday()+AppotUtils.LOGIN_CODE;
        String verCode = AppotUtils.getRandomNO();
        //发送注册登录短信
        SmsUtilTX.sendSMS(mobile,verCode,"715954");
        //缓存
        redisManager.set(key.getBytes(),verCode.getBytes(),36000);
        return ResponseResult.e(ResponseCode.OK,new String[]{"发送成功" , mobile});
    }

    @RequestMapping(value = {"/smsLogin"})
    public ResponseResult smsLogin(HttpServletRequest request, HttpServletResponse response, String mobile,String code,String openid) throws IOException {
        String key = mobile + AppotUtils.getToday()+AppotUtils.LOGIN_CODE;
        //获取缓存
        byte[] redistemp =  redisManager.get(key.getBytes());
        String dbCode = null;
        if(redistemp!=null && redistemp.length!=0){
            dbCode = new String(redistemp , "utf-8");
        }

        if ( code!=null && code.equals(dbCode) ){
            //关联微信公众号与手机号
            WechatUser wechatUser = wechatUserService.getWechatUserByOpenId(openid);
            if(wechatUser==null ){
                return ResponseResult.e(ResponseCode.OK,new String[]{"未关注公众号" , mobile}) ;
            }else{
                wechatUser.setMobile(mobile);
                wechatUserService.updateById(wechatUser);
                return ResponseResult.e(ResponseCode.OK,new String[]{"公众号关联成功." , mobile}) ;
            }
        }

        return ResponseResult.e(ResponseCode.FAIL,new String[]{"验证码不正确!" , mobile});
    }


    @RequestMapping(value = {"/getInfoByDateFromDCYumaoqiu"})
    public void getInfoByDateFromDCYumaoqiu(HttpServletRequest request, HttpServletResponse response,String datee) throws IOException {
        try {
            response.sendRedirect(base_url+":8010/getInfoByDateFromDCYumaoqiu?datee="+datee);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = {"/getCalendar"})
    public void getCalendar(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            response.sendRedirect(base_url+":8010/getCalendar");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @ResponseBody
    @RequestMapping(value = {"/get7Days"})
    public Object get7Days(String datee) throws Exception {
        return AppotUtils.get40Days(datee);
    }



    public static void main(String[] args) throws Exception {

    }

}