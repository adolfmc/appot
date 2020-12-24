package cn.licoy.wdog.core.controller;

import cn.licoy.wdog.common.controller.AppotBaseController;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Licoy
 * @version 2018/4/13/14:02
 */
@RestController
@RequestMapping(value = {"/"})
@Api(tags = {"home"})
public class IndexController extends AppotBaseController {
    /**
     * 微信H5 支付--------------------好使
     * 注意：必须再web页面中发起支付且域名已添加到开发配置中
     */
    @RequestMapping(value ="",method = {RequestMethod.POST, RequestMethod.GET})
    public void wapPay(HttpServletRequest request, HttpServletResponse response){
        try {
            response.sendRedirect(base_url+"/static/index.html");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value ="payfiles",method = {RequestMethod.POST, RequestMethod.GET})
    public void payfiles(HttpServletRequest request, HttpServletResponse response){
        try {
            response.sendRedirect(base_url+"/staticc/payfiles");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
