package cn.licoy.wdog.core.controller.appot;

import cn.licoy.wdog.common.bean.ResponseCode;
import cn.licoy.wdog.common.bean.ResponseResult;
import cn.licoy.wdog.common.controller.AppotBaseController;
import cn.licoy.wdog.common.util.AppotUtils;
import cn.licoy.wdog.common.util.HttpTools;
import cn.licoy.wdog.common.util.SmsUtilTX;
import cn.licoy.wdog.core.entity.appot.VenueFee;
import cn.licoy.wdog.core.entity.appot.WechatUser;
import cn.licoy.wdog.core.service.appot.VenueFeeService;
import cn.licoy.wdog.core.service.appot.WechatUserService;
import cn.licoy.wdog.core.vo.appot.CalenderVo;
import cn.licoy.wdog.core.vo.appot.json.Body;
import cn.licoy.wdog.core.vo.appot.json.BookPriceInfo;
import cn.licoy.wdog.core.vo.appot.json.ExampleClass;
import cn.licoy.wdog.core.vo.appot.json.SiteInfo;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.toolkit.ReflectionKit;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author mc
 * @version 2020/09/17
 */
@RestController
public class AppotController extends AppotBaseController {

    @Autowired
    private WechatUserService wechatUserService;
    @Autowired
    private VenueFeeService venueFeeService;


    @RequestMapping(value = {"/MP_verify_JhO2WS095phwvOSc.txt"})
    @ResponseBody
    public String MP_verify_JhO2WS095phwvOSc(HttpServletRequest request, HttpServletResponse response,String datee) throws IOException {
        return "JhO2WS095phwvOSc" ;
    }

    @RequestMapping(value = {"/77VoHeFOUA.txt"})
    @ResponseBody
    public String MP77VoHeFOUA(HttpServletRequest request, HttpServletResponse response,String datee) throws IOException {
        return "632acd96596a745885676632e8306a34" ;
    }


    @RequestMapping(value = {"/getVerCode"})
    public ResponseResult getVerCode(HttpServletRequest request, HttpServletResponse response, String mobile) throws IOException {
        String key = mobile + AppotUtils.getToday()+AppotUtils.LOGIN_CODE;
        String verCode = AppotUtils.getRandomNO();
        //发送注册登录短信
        SmsUtilTX.sendSMS(mobile,verCode,"792899");
        //缓存
        AppotUtils.setRedisV(redisManager,key,verCode,3600*10) ;
        return ResponseResult.e(ResponseCode.OK,new String[]{"发送成功" , mobile});
    }

    @RequestMapping(value = {"/smsLogin"})
    public ResponseResult smsLogin(HttpServletRequest request, HttpServletResponse response, String mobile,String code,String openid,String venueId) throws IOException {
        String key = mobile + AppotUtils.getToday()+AppotUtils.LOGIN_CODE;
        //获取缓存
        String dbCode = AppotUtils.getRedisV(redisManager,key);

        if ( code!=null && code.equals(dbCode) ){
            WechatUser wechatUser = wechatUserService.getWechatUserByMobile(mobile);
            if(wechatUser==null){
                WechatUser wechatUser1 = new WechatUser();
                wechatUser1.setMobile( mobile);
                wechatUser1.setCreateDate(new Date());
                wechatUser1.setFromVenueId(venueId);
                wechatUserService.insertOrUpdate(wechatUser1);
                wechatUser = wechatUser1 ;
            }

            return ResponseResult.e(ResponseCode.OK,wechatUser) ;
            //关联微信公众号与手机号
//            WechatUser wechatUser = wechatUserService.getWechatUserByOpenId(openid);
//            if(wechatUser==null ){
//                return ResponseResult.e(ResponseCode.OK,new String[]{"未关注公众号" , mobile}) ;
//            }else{
//                wechatUser.setMobile(mobile);
//                wechatUserService.updateById(wechatUser);
//                return ResponseResult.e(ResponseCode.OK,new String[]{"公众号关联成功." , mobile}) ;
//            }
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
                response.sendRedirect(base_url+"/pyDCYumaoqiu?datee="+datee+"&venue_id="+venue_id+"&sports_type="+sports_type);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @RequestMapping(value = {"/pyDCYumaoqiu"})
    @ResponseBody
    public ExampleClass pyDCYumaoqiu(HttpServletRequest request, HttpServletResponse response, String datee, String venue_id, String siteId, String sports_type){
        ExampleClass exampleClass =null;
        try {
            String resultjson = HttpTools.doGet(py_base_url+"/getInfoByDateFromDCYumaoqiu?datee="+datee+"&venue_id="+venue_id);
            exampleClass =  JSONObject.parseObject(resultjson,ExampleClass.class);

            for (SiteInfo siteInfo :exampleClass.getBody().getSiteInfos() ){
                ArrayList<BookPriceInfo>  bi1 = new ArrayList<BookPriceInfo>();

                //采集回来的数据
                for (BookPriceInfo bookPriceInfo :siteInfo.getBookPriceInfos() ){
                    BookPriceInfo bookPriceInfo1 = bookPriceInfo.clone();
                    bi1.add(bookPriceInfo1);
                }

                siteInfo.getBookPriceInfos().clear();
                siteInfo.getBookPriceInfos().addAll(bi1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return exampleClass;
    }

    /**
     * 模拟数据
     * @param
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
            siteInfo.setSiteNo( venueFee.getSiteNo());
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



    @ResponseBody
    @RequestMapping(value = {"/get7Days"})
    public Object get7Days(String datee) throws Exception {
        return AppotUtils.get40Days(datee);
    }
    @ResponseBody
    @RequestMapping(value = {"/getCalender"})
    public ResponseResult getCalender(String yyyyMM,String venueId) throws Exception {
        //判断是否是当月日期
        SimpleDateFormat sdfcc = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendarcc = Calendar.getInstance();
        calendarcc.setTime(new Date());
        calendarcc.add(Calendar.DAY_OF_MONTH,-1);

        //自动查询当月和下个月
        yyyyMM = new SimpleDateFormat("yyyy-MM").format(new Date());
        ArrayList<ArrayList> result = new ArrayList<>();
        result.add( AppotUtils.getCalendar(yyyyMM) );
        //自动查询当月的下一个月
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM");
        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTime(sdf2.parse( yyyyMM));
        calendar2.add(Calendar.MONTH,1);
        result.add( AppotUtils.getCalendar(sdf2.format(calendar2.getTime()) ) );

        //TODO SELECT TAG
        Map<String,String> tags = new HashMap();
        tags.put("2020-12-26","火爆");
        tags.put("2020-12-27","火爆");
        tags.put("2020-12-25","较多");
        tags.put("2020-12-24","较多");
        tags.put("2020-12-23","较多");

        ArrayList<CalenderVo> calenderVos =new ArrayList<>();

        //month
        for(int monthindex=0;monthindex<result.size();monthindex++){

            CalenderVo calenderVo=new CalenderVo();
            if(monthindex==1){
                calenderVo.month=sdf2.format(calendar2.getTime()).substring(5,7) ;
            }else{
                calenderVo.month=yyyyMM.toString().substring(5,7) ;
            }

            ArrayList<ArrayList<ArrayList>> weeks = result.get(monthindex);

            //weeks
            for(int weekindex=0;weekindex<weeks.size();weekindex++){
               ArrayList week =  weeks.get(weekindex) ;

               CalenderVo.Week weekvo = calenderVo.new Week();
               //days
               for(int dayindex=0;dayindex<week.size() ;dayindex++){
                    String day = (String)week.get(dayindex);
                    CalenderVo.Day dayvo = calenderVo.new Day();
                    dayvo.lDay = day;
                    dayvo.sDay = day.substring(8,10);
                    if( calenderVo.month.equals( day.substring(5,7)) ){
                        dayvo.isCurMonthDay="True";
                    }else{
                        dayvo.isCurMonthDay="False";
                    }

                   if(sdfcc.parse(day).before(calendarcc.getTime()) ){
                       dayvo.isPastDay="True";
                   }else{
                       dayvo.isPastDay="False" ;
                   }

                    dayvo.tag = tags.get(day)==null?"可约":tags.get(day);
                    weekvo.days.add(dayvo) ;
               }
                calenderVo.weeks.add(weekvo) ;
            }

            calenderVos.add(calenderVo);
        }
        return ResponseResult.e(ResponseCode.OK,calenderVos);
    }


    @ResponseBody
    @RequestMapping(value = {"/show_info_test"})
    public Object show_info_test(HttpServletRequest request,String info) throws Exception {
        String notime = System.nanoTime()+"";
        setRedisV(notime,info,3600);

        String mobile = getMobile(request);
        setRedisV(notime+"_mobile",mobile,3600);

        return null;
    }

    public static void main(String[] args) throws Exception {

    }

}
