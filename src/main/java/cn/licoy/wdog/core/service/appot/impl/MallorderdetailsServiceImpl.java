package cn.licoy.wdog.core.service.appot.impl;
import cn.licoy.wdog.core.dto.appot.FindMallorderdetailsDTO;
import cn.licoy.wdog.core.dto.appot.MallorderdetailsAddDTO;
import cn.licoy.wdog.core.dto.appot.MallorderdetailsUpdateDTO;
import cn.licoy.wdog.core.vo.appot.MallorderdetailsVO;
import org.apache.commons.beanutils.BeanUtils;
import cn.licoy.wdog.common.exception.RequestException;
import java.util.ArrayList;
import java.util.List;
import cn.licoy.wdog.core.entity.appot.Mallorderdetails;
import cn.licoy.wdog.core.mapper.appot.MallorderdetailsMapper;
import cn.licoy.wdog.core.service.appot.MallorderdetailsService;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;

/**
 * @author mc
 * @version Mon Apr 12 01:16:55 2021
 */
@Service
public class MallorderdetailsServiceImpl extends ServiceImpl<MallorderdetailsMapper, Mallorderdetails> implements MallorderdetailsService {

    @Override
    public Mallorderdetails findMallorderdetailsById(String id) {
        Mallorderdetails user = this.selectById(id);
        if(user == null){
            return null;
        }
        return user;
    }



    @Override
    public Page<MallorderdetailsVO> getAllMallorderdetailsBySplitPage(FindMallorderdetailsDTO findMallorderdetailsDTO) {
        EntityWrapper<Mallorderdetails> wrapper = new EntityWrapper<>();
        wrapper.orderBy("create_date",findMallorderdetailsDTO.getAsc());
        Page<Mallorderdetails> userPage = this.selectPage(new Page<>(findMallorderdetailsDTO.getPage(),findMallorderdetailsDTO.getPageSize()), wrapper);
        Page<MallorderdetailsVO> userVOPage = new Page<>();
            try {
               BeanUtils.copyProperties(userPage,userVOPage);
            } catch (Exception e) {
               e.printStackTrace();
            }
        List<MallorderdetailsVO> userVOS = new ArrayList<>();
        userPage.getRecords().forEach(v->{
            MallorderdetailsVO vo = new MallorderdetailsVO();
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
        Mallorderdetails user = this.selectById(userId);
        if(user==null){
            throw RequestException.fail("Mallorderdetails不存在");
        }
        Mallorderdetails Mallorderdetails = new Mallorderdetails();
    }

    @Override
    public void removeMallorderdetails(String userId) {
        Mallorderdetails user = this.selectById(userId);
        if(user==null){
            throw RequestException.fail("Mallorderdetails不存在！");
        }
        Mallorderdetails Mallorderdetails = new Mallorderdetails();
        try {
            this.deleteById(userId);
        }catch (Exception e){
            throw RequestException.fail("删除失败",e);
        }
    }

    @Override
    public void add(MallorderdetailsAddDTO addDTO) {
        try {
            Mallorderdetails mallorderdetails = new Mallorderdetails();
            BeanUtils.copyProperties(mallorderdetails,addDTO);
            mallorderdetails.setCreateDate(new Date());
            this.insert(mallorderdetails);
        }catch (Exception e){
            throw RequestException.fail("添加Mallorderdetails失败",e);
        }
    }

    @Override
    public void update(String id, MallorderdetailsUpdateDTO updateDTO) {
        Mallorderdetails user = this.selectById(id);
        if(user==null){
            throw RequestException.fail(
                    String.format("更新失败，不存在ID为 %s 的Mallorderdetails",id));
        }
        try {
            BeanUtils.copyProperties(updateDTO,user);
            this.updateById(user);
        }catch (Exception e){
            throw RequestException.fail("Mallorderdetails信息更新失败",e);
        }
    }
}
