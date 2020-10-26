package cn.licoy.wdog.core.controller.wxpay;


import cn.licoy.wdog.core.entity.adtea.OrderTea;
import cn.licoy.wdog.core.service.adtea.OrderTeaService;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.jfinal.weixin.sdk.kit.IpKit;
import com.jpay.ext.kit.HttpKit;
import com.jpay.ext.kit.PaymentKit;
import com.jpay.weixin.api.WxPayApi;
import com.jpay.weixin.api.WxPayApiConfig;
import com.jpay.weixin.api.WxPayApiConfigKit;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.util.*;


@RestController
//@RequestMapping("/wxpay")
@Api
public class WXH5PayController {

    @Autowired
    private OrderTeaService orderTeaService;

    String mchID = "1602617281";
    String appID = "wx98188fbf500bdf33";
    String partnerKey = "u7yhlCsbYNAoH957ufvV1aujEou3Nh46";


    Logger log = LoggerFactory.getLogger(WXH5PayController.class);
    /**
     * 微信H5 支付--------------------好使
     * 注意：必须再web页面中发起支付且域名已添加到开发配置中
     */
    @RequestMapping(value ="/pay.do",method = {RequestMethod.POST,RequestMethod.GET})
    public String wapPay(HttpServletRequest request,HttpServletResponse response){
        System.out.println("--pay start--");
        String notify_url = "http://www.yushangcc.com/wxpay/pay_notify.do";//这是回调地址，方法在下面
        //获取ip

        String ip = IpKit.getRealIp(request);
        if (com.jpay.ext.kit.StrKit.isBlank(ip)) {
            ip = "127.0.0.1";
        }

        String orderId = request.getParameter("id");
        EntityWrapper<OrderTea> wrapper = new EntityWrapper<>();
        wrapper.eq("id",orderId);
        OrderTea orderTea = orderTeaService.selectOne(wrapper);

        H5ScencInfo sceneInfo = new H5ScencInfo();

        H5ScencInfo.H5 h5_info = new H5ScencInfo.H5();
        h5_info.setType("Wap");
        //此域名必须在商户平台--"产品中心"--"开发配置"中添加
        log.info("id >> "+ip);
        h5_info.setWap_url("http://www.yushangcc.com");
        h5_info.setWap_name("公司官网");
        sceneInfo.setH5_info(h5_info);
        WxPayApiConfig wxPayApiConfig=getApiConfig();
        String outTradeNo = String.valueOf(System.nanoTime());

        orderTea.setOutTradeNo(outTradeNo);
        orderTea.setStatuss("0008");
        orderTea.setPayStatus("待付款");
        orderTea.setTradeDate(new Date());
        orderTeaService.insertOrUpdate(orderTea);

        Map<String, String> params=WxPayApiConfig.New()
                .setAppId(appID)
                .setMchId(mchID)
                .setBody("自动预约SAAS平台:Dc羽毛球场地预定")
                .setSpbillCreateIp(ip)
                .setTotalFee("100"  )
                .setTradeType(WxPayApi.TradeType.MWEB)
                .setNotifyUrl(notify_url)
                .setPaternerKey(partnerKey)
                .setOutTradeNo(outTradeNo)
                .setSceneInfo("{\"h5_info\": {\"type\":\"IOS\",\"app_name\": \"mtgg\",\"package_name\": \"com.tencent.tmgp.sgame\"}}")
                .setAttach(orderTea.getSellpackage())
                .build();
        String xmlResult = WxPayApi.pushOrder(false,params);

        System.out.println(xmlResult);

        Map<String, String> result = PaymentKit.xmlToMap(xmlResult);




        //返回结果
        String return_code = result.get("return_code");
        String return_msg = result.get("return_msg");
        if (!PaymentKit.codeIsOK(return_code)) {
            log.error("return_code>"+return_code+" return_msg>"+return_msg);
            throw new RuntimeException(return_msg);
        }
        String result_code = result.get("result_code");
        if (!PaymentKit.codeIsOK(result_code)) {
            log.error("result_code>"+result_code+" return_msg>"+return_msg);
            throw new RuntimeException(return_msg);
        }
        // 以下字段在return_code 和result_code都为SUCCESS的时候有返回

        String prepay_id = result.get("prepay_id");
        String mweb_url = result.get("mweb_url");

        log.info("prepay_id:"+prepay_id+" mweb_url:"+mweb_url);
//        try {
//            //response.sendRedirect(mweb_url);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        //mweb_url="https://wx.tenpay.com/cgi-bin/mmpayweb-bin/checkmweb?prepay_id=wx01223123351793726cdf74141880093400&package=277271036";
        return mweb_url;
    }



    @RequestMapping(value = "/pay_notify.do",method={RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public void pay_notify(HttpServletRequest request, HttpServletResponse response) {
        // 支付结果通用通知文档: https://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_7
        System.out.println("---------------支付回调----------------");
        String xmlMsg = HttpKit.readData(request);
        System.out.println("pay notice---------"+xmlMsg);
        Map<String, String> params = PaymentKit.xmlToMap(xmlMsg);
//		String appid  = params.get("appid");
//		//商户号
//		String mch_id  = params.get("mch_id");
        String result_code  = params.get("result_code");
//		String openId      = params.get("openid");
//		//交易类型
//		String trade_type      = params.get("trade_type");
//		//付款银行
//		String bank_type      = params.get("bank_type");
//		// 总金额
        String total_fee     = params.get("total_fee");
        total_fee = "100";
//		//现金支付金额
//		String cash_fee     = params.get("cash_fee");
//		// 微信支付订单号
//		String transaction_id      = params.get("transaction_id");
//		// 商户订单号
//		String out_trade_no      = params.get("out_trade_no");
//		// 支付完成时间，格式为yyyyMMddHHmmss
//		String time_end      = params.get("time_end");

        /////////////////////////////以下是附加参数///////////////////////////////////

        String account      = params.get("attach");
        System.out.println("回调total_fee-->"+total_fee);
        System.out.println("回调account-->"+account);
//		String fee_type      = params.get("fee_type");
//		String is_subscribe      = params.get("is_subscribe");
//		String err_code      = params.get("err_code");
//		String err_code_des      = params.get("err_code_des");
        // 注意重复通知的情况，同一订单号可能收到多次通知，请注意一定先判断订单状态
        // 避免已经成功、关闭、退款的订单被再次更新
//		Order order = Order.dao.getOrderByTransactionId(transaction_id);
//		if (order==null) {
        String resXml = "";
        String  outTradeNo=params.get("out_trade_no");
        WxPayApiConfigKit.setThreadLocalWxPayApiConfig(getApiConfig());
        if(PaymentKit.verifyNotify(params, WxPayApiConfigKit.getWxPayApiConfig().getPaternerKey())){
            String statuss ="0010";
            String payStatus ="付款失败";
            if (("SUCCESS").equals(result_code)) {
                // TODO 根据商户订单号更改押金状态
                System.out.println("成功，存储");
                statuss = "0009";
                payStatus = "付款成功";
                resXml = "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>"
                        + "<return_msg><![CDATA[OK]]></return_msg>" + "</xml> ";
            } else {
                //TODO FAIL支付失败
                log.debug("支付失败的回调消息");
                resXml = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>"
                        + "<return_msg><![CDATA[报文为空]]></return_msg>" + "</xml> ";
            }

            EntityWrapper<OrderTea> wrapper = new EntityWrapper<>();
            wrapper.eq("out_trade_no",outTradeNo);
            OrderTea orderTea = orderTeaService.selectOne(wrapper);
            orderTea.setStatuss(statuss);
            orderTea.setPayStatus(payStatus);
            orderTeaService.insertOrUpdate(orderTea);
            BufferedOutputStream out = null;
            try {
                out = new BufferedOutputStream(
                        response.getOutputStream());
                out.write(resXml.getBytes());
                out.flush();
                out.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
//		}

    }

    public  WxPayApiConfig getApiConfig() {
        return WxPayApiConfig.New()
                .setAppId(appID)
                .setMchId(mchID)
                .setPaternerKey(partnerKey)
                .setPayModel(WxPayApiConfig.PayModel.BUSINESSMODEL);
    }


    /**
     * 微信支付签名算法sign
     * @param characterEncoding
     * @param parameters
     * @return
     */
    public static String createSign(String characterEncoding, SortedMap<Object,Object> parameters){
        StringBuffer sb = new StringBuffer();
        Set es = parameters.entrySet();//所有参与传参的参数按照accsii排序（升序）
        Iterator it = es.iterator();
        while(it.hasNext()) {
            Map.Entry entry = (Map.Entry)it.next();
            String k = (String)entry.getKey();
            Object v = entry.getValue();
            if(null != v && !"".equals(v)
                    && !"sign".equals(k) && !"key".equals(k)) {
                sb.append(k + "=" + v + "&");
            }
        }

        System.out.println("签名字符串:"+sb.toString());
        System.out.println("签名MD5未变大写：" + MD5Util.MD5Encode(sb.toString(), characterEncoding));
        String sign = MD5Util.MD5Encode(sb.toString(), characterEncoding).toUpperCase();
        return sign;
    }


    public static String getRandomString(int length){
        String str="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random=new Random();
        StringBuffer sb=new StringBuffer();
        for(int i=0;i<length;i++){
            int number=random.nextInt(62);
            sb.append(str.charAt(number));
        }
        return sb.toString().toUpperCase();
    }

    public static void main(String[] args){
        Map<String, String> params = new HashMap<>();
        String partnerKey="u7yhlCsbYNAoH957ufvV1aujEou3Nh46";
        params.put("appId","wx98188fbf500bdf33");
        params.put("timeStamp",(new Date().getTime()+"").substring(0,10) );
        params.put("nonceStr",getRandomString(32).toUpperCase() );
        params.put("appId","");
        params.put("appId","");
        params.put("appId","");
        params.put("appId","");


        String sign = PaymentKit.createSign(params ,partnerKey);
        System.out.println((new Date().getTime()+"").substring(0,10));
        System.out.println(getRandomString(32).toUpperCase() ) ;
        System.out.println(sign);
        System.out.println(Double.valueOf(Math.random()*100+"").toString().substring(0,2));
    }
}
