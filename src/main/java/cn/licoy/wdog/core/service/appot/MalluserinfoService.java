package cn.licoy.wdog.core.service.appot;import cn.licoy.wdog.common.service.QueryService;import cn.licoy.wdog.core.dto.appot.MalluserinfoAddDTO;import cn.licoy.wdog.core.dto.appot.FindMalluserinfoDTO;import cn.licoy.wdog.core.vo.appot.MalluserinfoVO;import cn.licoy.wdog.core.dto.appot.MalluserinfoUpdateDTO;import cn.licoy.wdog.core.entity.appot.Malluserinfo;import com.alibaba.fastjson.JSONObject;import com.baomidou.mybatisplus.plugins.Page;import com.baomidou.mybatisplus.service.IService;/** * @author mc * @version Mon Apr 12 01:16:55 2021 */public interface MalluserinfoService extends IService<Malluserinfo>{    /**
     * 根据ID查找Malluserinfo
     * @param id ID
     * @return Malluserinfo
     */
    Malluserinfo findMalluserinfoById(String id);


    /**
     * 获取所有Malluserinfo（分页）
     * @param findMalluserinfoDTO 过滤条件
     * @return RequestResult
     */
    Page<MalluserinfoVO> getAllMalluserinfoBySplitPage(FindMalluserinfoDTO findMalluserinfoDTO);

    /**
     * Malluserinfo状态改变
     * @param userId MalluserinfoID
     * @param status 状态码
     */
    void statusChange(String userId, Integer status);

    /**
     * 删除Malluserinfo
     * @param userId MalluserinfoID
     */
    void removeMalluserinfo(String userId);

    /**
     * 添加Malluserinfo
     * @param addDTO Malluserinfo数据DTO
     */
    void add(MalluserinfoAddDTO addDTO);

    /**
     * 更新Malluserinfo数据
     * @param id Malluserinfoid
     * @param updateDTO Malluserinfo数据DTO
     */
    void update(String id, MalluserinfoUpdateDTO updateDTO);
};