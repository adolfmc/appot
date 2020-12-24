package cn.licoy.wdog.core.service.appot;
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