package cn.licoy.wdog.core.service.appot.impl;
import cn.licoy.wdog.core.dto.appot.FindMallorderDTO;
import cn.licoy.wdog.core.dto.appot.MallorderAddDTO;
import cn.licoy.wdog.core.dto.appot.MallorderUpdateDTO;
import cn.licoy.wdog.core.vo.appot.MallorderVO;
import org.apache.commons.beanutils.BeanUtils;
import cn.licoy.wdog.common.exception.RequestException;
import java.util.ArrayList;
import java.util.List;
import cn.licoy.wdog.core.entity.appot.Mallorder;
import cn.licoy.wdog.core.mapper.appot.MallorderMapper;
import cn.licoy.wdog.core.service.appot.MallorderService;
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
public class MallorderServiceImpl extends ServiceImpl<MallorderMapper, Mallorder> implements MallorderService {

    @Override
    public Mallorder findMallorderById(String id) {
        Mallorder user = this.selectById(id);
        if(user == null){
            return null;
        }
        return user;
    }



    @Override
    public Page<MallorderVO> getAllMallorderBySplitPage(FindMallorderDTO findMallorderDTO) {
        EntityWrapper<Mallorder> wrapper = new EntityWrapper<>();
        wrapper.orderBy("create_date",findMallorderDTO.getAsc());
        Page<Mallorder> userPage = this.selectPage(new Page<>(findMallorderDTO.getPage(),findMallorderDTO.getPageSize()), wrapper);
        Page<MallorderVO> userVOPage = new Page<>();
            try {
               BeanUtils.copyProperties(userPage,userVOPage);
            } catch (Exception e) {
               e.printStackTrace();
            }
        List<MallorderVO> userVOS = new ArrayList<>();
        userPage.getRecords().forEach(v->{
            MallorderVO vo = new MallorderVO();
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
        Mallorder user = this.selectById(userId);
        if(user==null){
            throw RequestException.fail("Mallorder不存在");
        }
        Mallorder Mallorder = new Mallorder();
    }

    @Override
    public void removeMallorder(String userId) {
        Mallorder user = this.selectById(userId);
        if(user==null){
            throw RequestException.fail("Mallorder不存在！");
        }
        Mallorder Mallorder = new Mallorder();
        try {
            this.deleteById(userId);
        }catch (Exception e){
            throw RequestException.fail("删除失败",e);
        }
    }

    @Override
    public Mallorder add(MallorderAddDTO addDTO) {
        try {
            Mallorder mallorder = new Mallorder();
            BeanUtils.copyProperties(mallorder,addDTO);
            mallorder.setCreateDate(new Date());
            this.insert(mallorder);
            return mallorder;
        }catch (Exception e){
            throw RequestException.fail("添加Mallorder失败",e);
        }
    }

    @Override
    public void update(String id, MallorderUpdateDTO updateDTO) {
        Mallorder user = this.selectById(id);
        if(user==null){
            throw RequestException.fail(
                    String.format("更新失败，不存在ID为 %s 的Mallorder",id));
        }
        try {
            BeanUtils.copyProperties(user,updateDTO);
            this.updateById(user);
        }catch (Exception e){
            throw RequestException.fail("Mallorder信息更新失败",e);
        }
    }
}
