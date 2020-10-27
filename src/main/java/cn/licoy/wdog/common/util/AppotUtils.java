package cn.licoy.wdog.common.util;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

public class AppotUtils {
    final public static String LOGIN_CODE="LOGIN_CODE";

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
        for(int i=-20;i<=20;i++){
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
            dee.add(qyt);
            dee.add(getWeek(start));

            result.add(dee);
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

    public static String getPIdFromUUID(){
        return UUID.randomUUID().toString().replace("-","");
    }

    public static void main(String[] args) throws  Exception{
//        String s1 =  DateFormatUtils.format(new Date() ,"YYYY-MM-dd");
//        String s2 = DateFormatUtils.format(DateUtils.addDays(new Date() ,-3) ,"YYYY-MM-dd");
//
//        System.out.println( DateUtils.parseDate( s2 ,new String[]{"yyyy-MM-dd"}).before(  DateUtils.parseDate( s1 ,new String[]{"yyyy-MM-dd"})   )  );
//        System.out.println(    );


        String c1 = "";
        String c2 = "";

        if( !( c1==null || c2==null ) ){
            System.out.println(1111);
        }else{
            System.out.println(2222);
        }
    }
}
