package cn.licoy.wdog.core.service.appot;
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