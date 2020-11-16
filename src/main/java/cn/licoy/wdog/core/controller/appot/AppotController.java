package cn.licoy.wdog.core.controller.appot;

import cn.licoy.wdog.common.bean.ResponseCode;
import cn.licoy.wdog.common.bean.ResponseResult;
import cn.licoy.wdog.common.controller.AppotBaseController;
import cn.licoy.wdog.common.util.AppotUtils;
import cn.licoy.wdog.common.util.SmsUtilTX;
import cn.licoy.wdog.core.entity.appot.VenueFee;
import cn.licoy.wdog.core.entity.appot.WechatUser;
import cn.licoy.wdog.core.service.appot.VenueFeeService;
import cn.licoy.wdog.core.service.appot.WechatUserService;
import cn.licoy.wdog.core.vo.appot.json.Body;
import cn.licoy.wdog.core.vo.appot.json.BookPriceInfo;
import cn.licoy.wdog.core.vo.appot.json.ExampleClass;
import cn.licoy.wdog.core.vo.appot.json.SiteInfo;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.toolkit.ReflectionKit;
import com.google.common.reflect.Reflection;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.crazycake.shiro.RedisManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

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
    @Autowired
    private VenueFeeService venueFeeService;


    @RequestMapping(value = {"/MP_verify_JhO2WS095phwvOSc.txt"})
    @ResponseBody
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
    public void getInfoByDateFromDCYumaoqiu(HttpServletRequest request, HttpServletResponse response,String datee,String venue_id,String siteId,String sports_type) throws Exception {
        try {
            if (datee==null){
                return ;
            }
            Date pdate = DateUtils.parseDate(datee,"yyyy-MM-dd") ;
            Date _7Days = DateUtils.addDays(new Date() ,7);
            if(pdate.after(_7Days) ){
                response.sendRedirect(base_url+"/getInfoByDateFromDCYumaoqiu2?datee="+datee+"&venue_id="+venue_id+"&sports_type="+sports_type);
            }else{
                response.sendRedirect(base_url+":8010/getInfoByDateFromDCYumaoqiu?datee="+datee);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 模拟数据
     * @param request
     * @param response
     * @param datee
     * @return
     * @throws IOException
     */
    @RequestMapping(value = {"/getInfoByDateFromDCYumaoqiu2"})
    @ResponseBody
    public ExampleClass getInfoByDateFromDCYumaoqiu2(HttpServletRequest request, HttpServletResponse response, String datee ,String venue_id,String sports_type) throws Exception {
/*        String pathname = "E:\\Administor\\git\\appot_server\\src\\main\\resources\\static\\assistant\\js\\siteJson.json";
        pathname = "/opt/data/appot_server/siteJson.json";
        String jsonObject  = FileUtils.readFileToString(new File(pathname),"UTF-8");
        ExampleClass newPerson = JSON.parseObject(jsonObject, ExampleClass.class);*/


        String xq = "星期"+AppotUtils.getWeek(DateUtils.parseDate(datee,"yyyy-MM-dd"));
//        venue_id = "297881";
//        sports_type = "0";

        List<VenueFee> venueFees = venueFeeService.findFeeByVenueId(venue_id,sports_type,xq);
        ExampleClass exampleClass = new ExampleClass();
        Body body = new Body();
        String etime=null;
        String stime=null;

        List<SiteInfo> siteInfos = new ArrayList<SiteInfo>();
        SiteInfo siteInfo = null;
        for(VenueFee venueFee:venueFees){
            siteInfo = new SiteInfo();

            List<BookPriceInfo> bookPriceInfos = new ArrayList<BookPriceInfo>();

            for(int i=8;i<=22;i++){
                BookPriceInfo bookPriceInfo = new BookPriceInfo();
                bookPriceInfo.setBookStatus(1);//1 未预定  ；0 已预定
                bookPriceInfo.setIsGroup(0);
                String dd = ("00"+i ).substring( ("00"+i ).length()-2,("00"+i ).length()) ;
                bookPriceInfo.setBeginTime(datee +" "+ dd +":00");



                Object fee=ReflectionKit.getMethodValue( venueFee,"t"+dd);
                Double feeb = null;
                try{
                    feeb = Double.valueOf(  fee.toString() );
                }catch (Exception ex){
//                    feeb = Double.valueOf("0.0");
                    bookPriceInfo.setBookStatus(0);//用已预订 方式，让前端不显示
                }
                bookPriceInfo.setSalePrice(feeb);
                dd = ("00"+(i+1) ).substring( ("00"+(i+1) ).length()-2,("00"+(i+1) ).length()) ;
                bookPriceInfo.setEndTime(datee +" "+ dd +":00");


                bookPriceInfos.add(bookPriceInfo) ;
            }


            siteInfo.setBookPriceInfos(bookPriceInfos);
            siteInfo.setSiteNo( Integer.valueOf( venueFee.getSiteNo()) );
            siteInfo.setSiteName(venueFee.getSiteName());
            siteInfos.add(siteInfo);
        }

        body.setStime(datee+" 08:00");
        body.setEtime(datee+" 23:00");
        body.setMinHour(1);
        body.setCancelHour("0");
        body.setIsCancel(0);
        body.setIsConfirm(1);
        body.setBill(0);
        body.setSiteInfos(siteInfos);

        exampleClass.setBody(body);
        exampleClass.setCode(1);
        exampleClass.setTotal(0);
        exampleClass.setMsg("成功");

        return exampleClass;
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
