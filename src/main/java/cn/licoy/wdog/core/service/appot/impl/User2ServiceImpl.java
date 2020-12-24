package cn.licoy.wdog.core.service.appot.impl;
    public User2 findUser2ById(String id) {
        User2 user = this.selectById(id);
        if(user == null){
            return null;
        }
        return user;
    }



    @Override
    public Page<User2VO> getAllUser2BySplitPage(FindUser2DTO findUser2DTO) {
        EntityWrapper<User2> wrapper = new EntityWrapper<>();
        wrapper.orderBy("create_date",findUser2DTO.getAsc());
        Page<User2> userPage = this.selectPage(new Page<>(findUser2DTO.getPage(),findUser2DTO.getPageSize()), wrapper);
        Page<User2VO> userVOPage = new Page<>();
            try {
               BeanUtils.copyProperties(userPage,userVOPage);
            } catch (Exception e) {
               e.printStackTrace();
            }
        List<User2VO> userVOS = new ArrayList<>();
        userPage.getRecords().forEach(v->{
            User2VO vo = new User2VO();
            try {
               BeanUtils.copyProperties(v,vo);
            } catch (Exception e) {
               e.printStackTrace();
            }
            userVOS.add(vo);
        });
        userVOPage.setRecords(userVOS);
        return userVOPage;
    }

    @Override
    public void statusChange(String userId, Integer status) {
        User2 user = this.selectById(userId);
        if(user==null){
            throw RequestException.fail("User2不存在");
        }
        User2 User2 = new User2();
        user.setStatus(status);
    }

    @Override
    public void removeUser2(String userId) {
        User2 user = this.selectById(userId);
        if(user==null){
            throw RequestException.fail("User2不存在！");
        }
        User2 User2 = new User2();
        try {
            this.deleteById(userId);
        }catch (Exception e){
            throw RequestException.fail("删除失败",e);
        }
    }

    @Override
    public void add(User2AddDTO addDTO) {
        try {
            User2 user2 = new User2();
            BeanUtils.copyProperties(addDTO,user2);
            user2.setCreateDate(new Date());
            this.insert(user2);
        }catch (Exception e){
            throw RequestException.fail("添加User2失败",e);
        }
    }

    @Override
    public void update(String id, User2UpdateDTO updateDTO) {
        User2 user = this.selectById(id);
        if(user==null){
            throw RequestException.fail(
                    String.format("更新失败，不存在ID为 %s 的User2",id));
        }
        try {
            BeanUtils.copyProperties(updateDTO,user);
            this.updateById(user);
        }catch (Exception e){
            throw RequestException.fail("User2信息更新失败",e);
        }
    }
}