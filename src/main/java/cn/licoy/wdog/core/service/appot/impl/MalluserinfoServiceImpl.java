package cn.licoy.wdog.core.service.appot.impl;
import cn.licoy.wdog.core.dto.appot.FindMalluserinfoDTO;
import cn.licoy.wdog.core.dto.appot.MalluserinfoAddDTO;
import cn.licoy.wdog.core.dto.appot.MalluserinfoUpdateDTO;
import cn.licoy.wdog.core.vo.appot.MalluserinfoVO;
import org.apache.commons.beanutils.BeanUtils;
import cn.licoy.wdog.common.exception.RequestException;
import java.util.ArrayList;
import java.util.List;
import cn.licoy.wdog.core.entity.appot.Malluserinfo;
import cn.licoy.wdog.core.mapper.appot.MalluserinfoMapper;
import cn.licoy.wdog.core.service.appot.MalluserinfoService;
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
public class MalluserinfoServiceImpl extends ServiceImpl<MalluserinfoMapper, Malluserinfo> implements MalluserinfoService {

    @Override
    public Malluserinfo findMalluserinfoById(String id) {
        Malluserinfo user = this.selectById(id);
        if(user == null){
            return null;
        }
        return user;
    }



    @Override
    public Page<MalluserinfoVO> getAllMalluserinfoBySplitPage(FindMalluserinfoDTO findMalluserinfoDTO) {
        EntityWrapper<Malluserinfo> wrapper = new EntityWrapper<>();
        wrapper.orderBy("create_date",findMalluserinfoDTO.getAsc());
        Page<Malluserinfo> userPage = this.selectPage(new Page<>(findMalluserinfoDTO.getPage(),findMalluserinfoDTO.getPageSize()), wrapper);
        Page<MalluserinfoVO> userVOPage = new Page<>();
            try {
               BeanUtils.copyProperties(userPage,userVOPage);
            } catch (Exception e) {
               e.printStackTrace();
            }
        List<MalluserinfoVO> userVOS = new ArrayList<>();
        userPage.getRecords().forEach(v->{
            MalluserinfoVO vo = new MalluserinfoVO();
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
        Malluserinfo user = this.selectById(userId);
        if(user==null){
            throw RequestException.fail("Malluserinfo?????????");
        }
        Malluserinfo Malluserinfo = new Malluserinfo();
    }

    @Override
    public void removeMalluserinfo(String userId) {
        Malluserinfo user = this.selectById(userId);
        if(user==null){
            throw RequestException.fail("Malluserinfo????????????");
        }
        Malluserinfo Malluserinfo = new Malluserinfo();
        try {
            this.deleteById(userId);
        }catch (Exception e){
            throw RequestException.fail("????????????",e);
        }
    }

    @Override
    public void add(MalluserinfoAddDTO addDTO) {
        try {
            Malluserinfo malluserinfo = new Malluserinfo();
            BeanUtils.copyProperties(malluserinfo,addDTO);
            malluserinfo.setCreateDate(new Date());
            this.insert(malluserinfo);
        }catch (Exception e){
            throw RequestException.fail("??????Malluserinfo??????",e);
        }
    }

    @Override
    public void update(String id, MalluserinfoUpdateDTO updateDTO) {
        Malluserinfo user = this.selectById(id);
        if(user==null){
            throw RequestException.fail(
                    String.format("????????????????????????ID??? %s ???Malluserinfo",id));
        }
        try {
            BeanUtils.copyProperties(updateDTO,user);
            this.updateById(user);
        }catch (Exception e){
            throw RequestException.fail("Malluserinfo??????????????????",e);
        }
    }
}
