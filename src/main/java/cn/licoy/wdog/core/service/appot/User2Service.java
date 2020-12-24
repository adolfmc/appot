package cn.licoy.wdog.core.service.appot;import cn.licoy.wdog.common.service.QueryService;import cn.licoy.wdog.core.dto.appot.User2AddDTO;import cn.licoy.wdog.core.dto.appot.FindUser2DTO;import cn.licoy.wdog.core.vo.appot.User2VO;import cn.licoy.wdog.core.dto.appot.User2UpdateDTO;import cn.licoy.wdog.core.entity.appot.User2;import com.alibaba.fastjson.JSONObject;import com.baomidou.mybatisplus.plugins.Page;import com.baomidou.mybatisplus.service.IService;/** * @author mc * @version Tue Dec 22 22:00:21 2020 */public interface User2Service extends IService<User2>{    /**
     * 根据ID查找User2
     * @param id ID
     * @return User2
     */
    User2 findUser2ById(String id);


    /**
     * 获取所有User2（分页）
     * @param findUser2DTO 过滤条件
     * @return RequestResult
     */
    Page<User2VO> getAllUser2BySplitPage(FindUser2DTO findUser2DTO);

    /**
     * User2状态改变
     * @param userId User2ID
     * @param status 状态码
     */
    void statusChange(String userId, Integer status);

    /**
     * 删除User2
     * @param userId User2ID
     */
    void removeUser2(String userId);

    /**
     * 添加User2
     * @param addDTO User2数据DTO
     */
    void add(User2AddDTO addDTO);

    /**
     * 更新User2数据
     * @param id User2id
     * @param updateDTO User2数据DTO
     */
    void update(String id, User2UpdateDTO updateDTO);
};