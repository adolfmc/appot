package cn.licoy.wdog.core.controller;

import cn.licoy.wdog.core.entity.appot.Order;
import cn.licoy.wdog.core.service.appot.OrderService;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Configuration      //1.主要用于标记配置类，兼备Component的效果。
@EnableScheduling   // 2.开启定时任务
public class SaticScheduleTask {
    @Autowired
    private OrderService service;

    //3.添加定时任务
    @Scheduled(cron = "0 0/5 * * * ?")
    //或直接指定时间间隔，例如：5秒
    //@Scheduled(fixedRate=5000)
    private void checkPayOrderInvalid() {
        List<Order> orders = service.checkPayOrderInvalid();
        for(Order order :orders){
            if( (new Date()).getTime() -  order.getCreateDate().getTime() > 10*60*1000){
                //支付失效
                order.setStatus("1");
                service.updateById(order);
            }
        }
        System.err.println("checkPayOrderInvalid: " + LocalDateTime.now());
    }


    //3.添加定时任务
    @Scheduled(cron = "0 0/5 * * * ?")
    //或直接指定时间间隔，例如：5秒
    //@Scheduled(fixedRate=5000)
    private void checkPayOrderIsFinish() throws  Exception{
        List<Order> orders = service.getOrdersByStatus("2");
        for(Order order :orders){

            String s1 =  DateFormatUtils.format(new Date() ,"YYYY-MM-dd");
            String s2 = order.getAppotDate();

            if(  DateUtils.parseDate( s2 ,new String[]{"yyyy-MM-dd"}).before(  DateUtils.parseDate( s1 ,new String[]{"yyyy-MM-dd"})   )  ){
                //支付失效
                order.setStatus("4");
                service.updateById(order);
            }
        }

    }
}
