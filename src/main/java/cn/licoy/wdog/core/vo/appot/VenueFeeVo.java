package cn.licoy.wdog.core.vo.appot;import com.fasterxml.jackson.annotation.JsonFormat;import lombok.Data;import java.util.Date;/** * @author mc * @version Sun Dec 06 09:49:43 2020 */@Datapublic class VenueFeeVo{   private Integer id;   private String venueId;   private String sportType;   private String siteNo;   private String siteName;   private String xq;   private String t00;   private String t01;   private String t02;   private String t03;   private String t04;   private String t05;   private String t06;   private String t07;   private String t08;   private String t09;   private String t10;   private String t11;   private String t12;   private String t13;   private String t14;   private String t15;   private String t16;   private String t17;   private String t18;   private String t19;   private String t20;   private String t21;   private String t22;   private String t23;   @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")   private Date createDate;   private String ysdays;   private String productId;}