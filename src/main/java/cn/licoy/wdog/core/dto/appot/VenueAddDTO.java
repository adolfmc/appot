package cn.licoy.wdog.core.dto.appot;import org.hibernate.validator.constraints.NotBlank;import lombok.Data;import java.util.Date;/** * @author mc * @version Thu Oct 08 19:59:41 2020 */@Datapublic class VenueAddDTO{   @NotBlank(message = "id")   private String id;   @NotBlank(message = "venueNo")   private String venueNo;   @NotBlank(message = "venueName")   private String venueName;   @NotBlank(message = "venueNameSimple")   private String venueNameSimple;   @NotBlank(message = "sportType")   private String sportType;   @NotBlank(message = "addres")   private String addres;   @NotBlank(message = "tel")   private String tel;   @NotBlank(message = "longitude")   private String longitude;   @NotBlank(message = "latitude")   private String latitude;   @NotBlank(message = "proDesc")   private String proDesc;   @NotBlank(message = "createDate")   private Date createDate;   @NotBlank(message = "wxGzh")   private String wxGzh;   @NotBlank(message = "url")   private String url;   @NotBlank(message = "businTime")   private String businTime;   @NotBlank(message = "price")   private String price;   @NotBlank(message = "salesPrice")   private String salesPrice;   @NotBlank(message = "img")   private String img;   @NotBlank(message = "province")   private String province;   @NotBlank(message = "city")   private String city;   @NotBlank(message = "area")   private String area;}