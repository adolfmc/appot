package cn.licoy.wdog.core.dto.appot;import cn.licoy.wdog.core.dto.SplitPageDTO;import cn.licoy.wdog.core.entity.appot.Venue;import lombok.Data;import java.io.Serializable;import java.util.Date;/** * @author mc * @version Thu Oct 08 19:59:41 2020 */@Datapublic class FindVenueDTO  implements Serializable,Comparable<FindVenueDTO> {   private String id;   private String venueNo;   private String venueName;   private String venueNameSimple;   private String sportType;   private String addres;   private String tel;   private Double longitude;   private Double latitude;   private String proDesc;   private Date createDate;   private String wxGzh;   private String url;   private String businTime;   private String price;   private String salesPrice;   private String img;   private String province;   private String city;   private String area;   private Double distance;   private String siteId;   private String productId;   private String isHalf;   private String venue_id;   private int countt;   private Double tamount;   @Override   public int compareTo(FindVenueDTO o) {      return 0;   }}