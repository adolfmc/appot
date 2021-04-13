package cn.licoy.wdog.core.service.appot;import cn.licoy.wdog.common.service.QueryService;import cn.licoy.wdog.core.dto.appot.MallorderdetailsAddDTO;import cn.licoy.wdog.core.dto.appot.FindMallorderdetailsDTO;import cn.licoy.wdog.core.vo.appot.MallorderdetailsVO;import cn.licoy.wdog.core.dto.appot.MallorderdetailsUpdateDTO;import cn.licoy.wdog.core.entity.appot.Mallorderdetails;import com.alibaba.fastjson.JSONObject;import com.baomidou.mybatisplus.plugins.Page;import com.baomidou.mybatisplus.service.IService;/** * @author mc * @version Mon Apr 12 01:16:55 2021 */public interface MallorderdetailsService extends IService<Mallorderdetails>{    /**
     * 根据ID查找Mallorderdetails
     * @param id ID
     * @return Mallorderdetails
     */
    Mallorderdetails findMallorderdetailsById(String id);


    /**
     * 获取所有Mallorderdetails（分页）
     * @param findMallorderdetailsDTO 过滤条件
     * @return RequestResult
     */
    Page<MallorderdetailsVO> getAllMallorderdetailsBySplitPage(FindMallorderdetailsDTO findMallorderdetailsDTO);

    /**
     * Mallorderdetails状态改变
     * @param userId MallorderdetailsID
     * @param status 状态码
     */
    void statusChange(String userId, Integer status);

    /**
     * 删除Mallorderdetails
     * @param userId MallorderdetailsID
     */
    void removeMallorderdetails(String userId);

    /**
     * 添加Mallorderdetails
     * @param addDTO Mallorderdetails数据DTO
     */
    void add(MallorderdetailsAddDTO addDTO);

    /**
     * 更新Mallorderdetails数据
     * @param id Mallorderdetailsid
     * @param updateDTO Mallorderdetails数据DTO
     */
    void update(String id, MallorderdetailsUpdateDTO updateDTO);
};