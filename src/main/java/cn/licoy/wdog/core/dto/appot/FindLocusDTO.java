package cn.licoy.wdog.core.dto.appot;import cn.licoy.wdog.core.dto.SplitPageDTO;import lombok.Data;import java.util.Date;/** * @author mc * @version Mon Nov 16 16:29:53 2020 */@Datapublic class FindLocusDTO extends SplitPageDTO  {   private String id;   private String openId;   private Date createDate;}