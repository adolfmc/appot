package cn.licoy.wdog.core.vo.appot;import lombok.Data;import java.util.Date;/** * @author mc * @version Mon Oct 05 07:43:27 2020 */@Datapublic class TimerVo{   private String id;   private Date createTime;   private Date startTime;   private Date endTime;   private Integer minutes;   private String type;}