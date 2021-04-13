package cn.licoy.wdog.core.service.appot;
import cn.licoy.wdog.common.service.QueryService;
import cn.licoy.wdog.core.dto.appot.MallorderAddDTO;
import cn.licoy.wdog.core.dto.appot.FindMallorderDTO;
import cn.licoy.wdog.core.vo.appot.MallorderVO;
import cn.licoy.wdog.core.dto.appot.MallorderUpdateDTO;
import cn.licoy.wdog.core.entity.appot.Mallorder;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;

/**
 * @author mc
 * @version Mon Apr 12 01:16:55 2021
 */
public interface MallorderService extends IService<Mallorder>{
    /**
     * 根据ID查找Mallorder
     * @param id ID
     * @return Mallorder
     */
    Mallorder findMallorderById(String id);


    /**
     * 获取所有Mallorder（分页）
     * @param findMallorderDTO 过滤条件
     * @return RequestResult
     */
    Page<MallorderVO> getAllMallorderBySplitPage(FindMallorderDTO findMallorderDTO);

    /**
     * Mallorder状态改变
     * @param userId MallorderID
     * @param status 状态码
     */
    void statusChange(String userId, Integer status);

    /**
     * 删除Mallorder
     * @param userId MallorderID
     */
    void removeMallorder(String userId);

    /**
     * 添加Mallorder
     * @param addDTO Mallorder数据DTO
     */
    Mallorder add(MallorderAddDTO addDTO);

    /**
     * 更新Mallorder数据
     * @param id Mallorderid
     * @param updateDTO Mallorder数据DTO
     */
    void update(String id, MallorderUpdateDTO updateDTO);
};
