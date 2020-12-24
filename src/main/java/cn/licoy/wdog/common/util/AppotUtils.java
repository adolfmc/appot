package cn.licoy.wdog.common.util;

import cn.licoy.wdog.core.vo.appot.json.Body;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.toolkit.ReflectionKit;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.crazycake.shiro.RedisManager;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class AppotUtils {
    final public static String LOGIN_CODE="LOGIN_CODE";
    final public static String WX_TOKEN_KEY="_WX_TOKEN_KEY";
    final public static String WX_OPPENID_KEY="_WX_OPPENID_KEY";

    public static String getToday(){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(new Date()) ;
    }

    public static String getRandomNO(){
        return  String.valueOf(Math.random()*100000).substring(0,4);
    }



    public static ArrayList get40Days(String datee) throws  Exception{
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        ArrayList result = new ArrayList();
        int size=0;
        for(int i=-6;i<=6;i++){
            ArrayList dee = new ArrayList();

            Calendar c = Calendar.getInstance();
            if(datee==null){
                c.setTime(new Date());
            }else{
                c.setTime(format.parse(datee));
            }

            c.add(Calendar.DATE, i);
            Date start = c.getTime();
            String qyt= format.format(start);//前一天
//            System.out.println(i+"   "+qyt +"  "+getWeek(start) );

            //过滤 已过去时间
            if( start.after( DateUtils.addDays( new Date(),-1) ) ){
                dee.add(qyt);
                dee.add(getWeek(start));
                if(size<=6){
                    result.add(dee);
                }
                size++ ;
            }

        }
        return result;
    }

    public static String getWeek(Date date){
        String[] weeks = {"日","一","二","三","四","五","六"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int week_index = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if(week_index<0){
            week_index = 0;
        }
        return weeks[week_index];
    }

    public static ArrayList getCalendar(String yyyyMM) throws  Exception {
        Map<String,String[][]> v = new HashMap<String,String[][]>();

        String[][] cc = new String[][]{};
        int week_month = 1; // 一个月第几周
        int week_day = 1;   // 星期几
        String datee ="2020-10-26";
        ArrayList<String> week_days = new ArrayList<>();
        ArrayList<ArrayList<String>> week_months = new ArrayList<>();
        Map<String, ArrayList<ArrayList<String>>> monthMap = new HashMap< String, ArrayList<ArrayList<String>>>();
        int m=0 ;

        do {
            String lastDatee = "";
            int lastday = 0;
            for (int i = 0; i < 42; i++) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(sdf.parse(datee));
                calendar.add(Calendar.DATE, i);
//                System.out.println(new SimpleDateFormat("yyyy-MM").format(calendar.getTime()) + " | " + week_month + " | " + week_day + " | " + sdf.format(calendar.getTime()));

                //月份计算
                Calendar calendar2 = Calendar.getInstance();
                calendar2.setTime(sdf.parse(datee));



                week_days.add(sdf.format(calendar.getTime()) ) ;
                if (week_day > 6) {
                    week_day = 0;
                    week_month = week_month + 1;
//                    System.out.println("------------------------------");
                    week_months.add( week_days) ;
                    week_days = new ArrayList<>();
                }

                if (week_month > 6) {
                    week_month = 1;
                }

                week_day = week_day + 1;
                lastDatee = sdf.format(calendar.getTime()) ;
                lastday = Integer.valueOf( new SimpleDateFormat("dd").format(calendar.getTime()) ) ;
            }


//            System.out.println(">>>>>>>>>>>> "+lastday);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(sdf.parse(lastDatee));

            SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM");
            Calendar calendar2 = Calendar.getInstance();
            calendar2.setTime(sdf2.parse( "2020-11" ));
            calendar2.add(Calendar.MONTH,m);

            String yyyyMM2= sdf2.format( calendar2.getTime() ) ;;

            if(lastday<=7 ){
                calendar.add(Calendar.DATE, -6);
                monthMap.put( yyyyMM2,week_months ) ;
                week_months = new ArrayList<>();
            }else if( lastday >=8 && lastday<=14){
                calendar.add(Calendar.DATE, -13);
                monthMap.put( yyyyMM2,week_months ) ;
                week_months = new ArrayList<>();
            }

            datee  = new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime() ) ;

            m++ ;
            if (yyyyMM2.equals( yyyyMM)){
                return monthMap.get(yyyyMM2) ;
            }

        } while(m <1000);
        return null;
    }


    public static String getPIdFromUUID(){
        return UUID.randomUUID().toString().replace("-","");
    }



    /**
     * 根据日期字符串判断当月第几周
     * @param str
     * @return
     * @throws Exception
     */
    public static int getWeek(String str) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = sdf.parse(str);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        //第几周
        int week = calendar.get(Calendar.WEEK_OF_MONTH);
        //第几天，从周日开始
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        return week;
    }


    public static String getRedisV(RedisManager redisManager, String key) {
        //获取缓存
        byte[] redistemp =  redisManager.get(key.getBytes());
        String dbCode = null;
        try{
            if(redistemp!=null && redistemp.length!=0){
                dbCode = new String(redistemp , "utf-8");
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }

        return dbCode;
    }

    public static String getHeader(HttpServletRequest request , String headerName) {
        return request.getHeader(headerName);
    }


    public static void setRedisV(RedisManager redisManager, String key,String verCode,int exipreTime) throws IOException {
        redisManager.set(key.getBytes(),verCode.getBytes(),36000);
    }




        public static void main(String[] args) throws  Exception{
//        Body b = new Body();
//        b.setStime("ccccccccccc");
//        System.out.println(ReflectionKit.getMethodValue( b,"stime"));
//        System.out.println( getWeek ( new Date()));
//        String s1 =  DateFormatUtils.format(new Date() ,"YYYY-MM-dd");
//        String s2 = DateFormatUtils.format(DateUtils.addDays(new Date() ,-3) ,"YYYY-MM-dd");
//
//        System.out.println( DateUtils.parseDate( s2 ,new String[]{"yyyy-MM-dd"}).before(  DateUtils.parseDate( s1 ,new String[]{"yyyy-MM-dd"})   )  );
//        System.out.println(    );


//        HttpGet httpget = new HttpGet("http://api.map.baidu.com/ag/coord/convert?from=0&to=4&x=121.534218&y=31.195534");
//        ResponseHandler<String> responseHandler = new BasicResponseHandler();
//        HttpClient client = new DefaultHttpClient();;;
//        String response = client.execute(httpget, responseHandler);
//        JSONObject OpenidJSONO = JSONObject.parseObject(response);
//        String x = OpenidJSONO.get("x").toString();
//        String y = OpenidJSONO.get("y").toString();
//
//        System.out.println(Encrypt.base64Decode(x));
//        System.out.println(Encrypt.base64Decode(y));

//        System.out.println( get40Days("2020-12-10"));


//            System.out.println( getCalendar("2020-11") );

//            System.out.println("2020-11-21".substring(8,10));
        //本周起始加1
            System.out.println( DateFormatUtils.format(new Date(),"HH") );
            System.out.println( Integer.valueOf( "08"));
            System.out.println("2020-11-11 12:00".substring(11,16));
        }
}
