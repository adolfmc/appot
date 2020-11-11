package cn.licoy.wdog.core.controller.wxpay; /**
 *
 */

import cn.licoy.wdog.common.bean.ResponseCode;
import cn.licoy.wdog.common.bean.ResponseResult;
import cn.licoy.wdog.common.controller.AppotBaseController;
import cn.licoy.wdog.common.util.Encrypt;
import cn.licoy.wdog.core.entity.appot.Order;
import cn.licoy.wdog.core.service.appot.OrderService;
import cn.licoy.wdog.core.service.appot.WechatUserService;
import com.alibaba.fastjson.JSONObject;
import com.jfinal.weixin.sdk.utils.IOUtils;
import com.jfinal.weixin.sdk.utils.JsonUtils;
import com.jpay.ext.kit.PaymentKit;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;


/**
 * @author chy
 *
 * 2019年1月15日-下午7:35:06
 */
@SuppressWarnings("ALL")
@Controller
@RequestMapping("wxpay")
public class WXJSAPIPayController extends AppotBaseController {// 公众号id
    @Autowired
    private WechatUserService wechatUserService;
    @Autowired
    private OrderService orderService;
    @Autowired
    RabbitTemplate rabbitTemplate;  //使用RabbitTemplate,这提供了接收/发送等等方法

    final static String APPID = "wx98188fbf500bdf33";
    // 公众号秘钥
    final static String SECRET = "2649e902a7c54b11dc9e4049716e392a";
    // 商户号
    final static String MATCH_ID = "1602617281"; //商户号(财务)
    // 商户key
    final static String PATERNER_KEY = "u7yhlCsbYNAoH957ufvV1aujEou3Nh46";//商户key(财务)
    // 微信通知的URL
    final static String W_NOTIFY_URL = "http://www.yushangcc.com/wxpay/pay_notify.do";
    // 获取openID的URL(微信)
    final static String GETOPENID_URL = "https://api.weixin.qq.com/sns/oauth2/access_token";
    // 获取预付款ID的URL(微信)
    final static String UNIFIEDORDER_URL = "https://api.mch.weixin.qq.com/pay/unifiedorder";
    // 获取用户信息URL
    final static String GETUSERINFO_URL = "https://api.weixin.qq.com/sns/userinfo";
    public String paternerKey="u7yhlCsbYNAoH957ufvV1aujEou3Nh46";

    /**
     * 第一步:获取access_token(需要在服务器上 )
     *
     * 微信小程序获取accessToken
     *
     * @author Mr.Wen
     * @time 2017年8月28日
     */
    public static String getAccessToken(String appid, String appsecret) {
        String GetPageAccessTokenUrl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=SECRET";
        String requestUrl = GetPageAccessTokenUrl.replace("APPID", appid).replace("SECRET", appsecret);
        HttpClient client = null;
        Map<String, String> result = new HashMap<String, String>();
        String accessToken = null;
        try {
            client = new DefaultHttpClient();
            HttpGet httpget = new HttpGet(requestUrl);
            ResponseHandler<String> responseHandler = new BasicResponseHandler();
            String response = client.execute(httpget, responseHandler);
            JSONObject userInfoJsonObject = JSONObject.parseObject(response);
            accessToken = String.valueOf(userInfoJsonObject.get("access_token"));
            System.out.println( "accessToken = "+accessToken);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            client.getConnectionManager().shutdown();
        }
        return accessToken;
    }

    /**
     * 第二步:获取jsapi_ticket
     * @author Mr.Wen
     * @description 获取ticket
     * @date 2018/3/29
     */

    public static Map<String, String> JsapiTicket(String accessToken) {
        String GetPageAccessTokenUrl = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=ACCESS_TOKEN&type=jsapi";
        String requestUrl = GetPageAccessTokenUrl.replace("ACCESS_TOKEN", accessToken);
        HttpClient client = null;
        Map<String, String> result = new HashMap<String, String>();
        try {
            client = new DefaultHttpClient();
            HttpGet httpget = new HttpGet(requestUrl);
            ResponseHandler<String> responseHandler = new BasicResponseHandler();
            String response = client.execute(httpget, responseHandler);
            JSONObject OpenidJSONO = JSONObject.parseObject(response);
            String errcode = String.valueOf(OpenidJSONO.get("errcode"));
            String errmsg = String.valueOf(OpenidJSONO.get("errmsg"));
            String ticket = String.valueOf(OpenidJSONO.get("ticket"));
            String expires_in = String.valueOf(OpenidJSONO.get("expires_in"));
            result.put("errcode", errcode);
            result.put("errmsg", errmsg);
            result.put("ticket", ticket);
            result.put("expires_in", expires_in);

            System.out.println( "ticket = "+ticket );
            System.out.println( "errmsg = "+errmsg );
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            client.getConnectionManager().shutdown();
        }
        return result;
    }



    public static String SHA1(String decript) {
        try {
            MessageDigest digest = java.security.MessageDigest.getInstance("SHA-1");
            digest.update(decript.getBytes());
            byte messageDigest[] = digest.digest();
            // Create Hex String
            StringBuffer hexString = new StringBuffer();
            // 字节数组转换为 十六进制 数
            for (int i = 0; i < messageDigest.length; i++) {
                String shaHex = Integer.toHexString(messageDigest[i] & 0xFF);
                if (shaHex.length() < 2) {
                    hexString.append(0);
                }
                hexString.append(shaHex);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    @RequestMapping("getSignature")
    @ResponseBody
    public ResponseResult getSignature(HttpServletRequest request, HttpServletResponse response,String url) throws Exception {
        String accessToken = getAccessToken(APPID ,SECRET ) ;
        Map<String, String> result = JsapiTicket(accessToken) ;


//        第三部:用时间戳、随机数、jsapi_ticket和要访问的url按照签名算法拼接字符串
        String noncestr = WXH5PayController.getRandomString(32);//随机字符串
        String timestamp = String.valueOf(System.currentTimeMillis() / 1000);//时间戳
        //4获取url
        //5、将参数排序并拼接字符串
        String ticket  = result.get("ticket") ;
        String str = "jsapi_ticket="+ticket+"&noncestr="+noncestr+"&timestamp="+timestamp+"&url="+url;
        //6、将字符串进行sha1加密
        String signature =SHA1(str);
        Map<String,String> map=new HashMap();
        map.put("timestamp",timestamp);
        map.put("accessToken",accessToken);
        map.put("ticket",ticket);
        map.put("nonceStr",noncestr);
        map.put("signature",signature);

        System.out.println(str);
        System.out.println("url = "+url);
        System.out.println("ticket = "+ticket);
        System.out.println("timestamp = "+timestamp);
        System.out.println("accessToken = "+accessToken);
        System.out.println("nonceStr = "+noncestr);
        System.out.println("signature = "+signature);
        return ResponseResult.e(ResponseCode.OK, map);
    }

    @RequestMapping("getWechatUser.do")
    public void getWechatUser(HttpServletRequest request, HttpServletResponse response, String code , ModelMap retMap) throws  Exception{
        String getOpenIdparam= "appid="+APPID+"&secret="+SECRET+"&code="+code+"&grant_type=authorization_code";
        String getOpenIdUrl = GETOPENID_URL+"?"+getOpenIdparam;
        System.out.println("***getOpenId:"+getOpenIdUrl);
        RestTemplate rest = new RestTemplate();
        rest.getMessageConverters().add(0, new StringHttpMessageConverter(Charset.forName("UTF-8")));
        String resString = rest.getForObject(new URI(getOpenIdUrl), String.class);
        JSONObject opidJsonObject = JSONObject.parseObject(resString);
        System.out.println("***opidJsonObject:"+opidJsonObject);
        String openid = opidJsonObject.get("openid").toString();//获取到了openid
        String access_token = opidJsonObject.get("access_token").toString();//获取到了access_token

        // 获取用户信息
//            https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN
        String userInfoString = rest.getForObject(new URI(GETUSERINFO_URL+"?access_token="+access_token+"&openid="+ openid+"&lang=zh_CN"), String.class);
        JSONObject userInfoJsonObject = JSONObject.parseObject(userInfoString);
        System.out.println("***userInfoJsonObject:"+userInfoJsonObject);
        wechatUserService.saveWechatUser(userInfoJsonObject);

        String headimgurl =  userInfoJsonObject.get("headimgurl").toString() ;
        String nickname =  userInfoJsonObject.get("nickname").toString() ;
        response.sendRedirect(base_url+"/static/home.html?headimgurl="+headimgurl+"&openid="+openid+"&nickname="+nickname);
    }



    /**
     * 微信
     * @author chy
     * 2019年1月17日-下午2:14:31
     * @param code            微信必须的code码，需要用code码换取openid，之后需要openid来换取prepay_id。
     * @return
     * @throws Exception
     */
    @RequestMapping("pay.do")
    public void order( HttpServletRequest request, HttpServletResponse response, String code , ModelMap retMap,String order_id) throws  Exception{
        Order order = orderService.selectById(order_id);
        String total_fee = Double.valueOf(Math.random()*1000+"").toString().substring(0,2);
               total_fee = order.getTotalAmount().intValue()+"";

        System.out.println("***WxPayController.order(),total_fee = "+total_fee+" ,order_id = "+order_id);

        Map<String, String> packageParams = new HashMap<String, String>();
        String prepay_id = "";//预支付id

        String orderNo = order_id;// 生成订单id
        String getOpenIdparam= "appid="+APPID+"&secret="+SECRET+"&code="+code+"&grant_type=authorization_code";
        String getOpenIdUrl = GETOPENID_URL+"?"+getOpenIdparam;
        System.out.println("***getOpenId:"+getOpenIdUrl);
        RestTemplate rest = new RestTemplate();
        rest.getMessageConverters().add(0, new StringHttpMessageConverter(Charset.forName("UTF-8")));

        try {

            String resString = rest.getForObject(new URI(getOpenIdUrl), String.class);
            JSONObject opidJsonObject = JSONObject.parseObject(resString);
            System.out.println("***opidJsonObject:"+opidJsonObject);
            String openid = opidJsonObject.get("openid").toString();//获取到了openid
            String access_token = opidJsonObject.get("access_token").toString();//获取到了access_token
            Map<String, String> paramMap = new HashMap<String, String>();
            paramMap.put("appid", APPID);            //公众账号ID
            paramMap.put("mch_id", MATCH_ID);             //商户号
            paramMap.put("nonce_str",  WXH5PayController.getRandomString(32) );        //随机字符串
            paramMap.put("body", "自动预约SAAS平台：Dc羽毛球场地");            //商品描述
            paramMap.put("out_trade_no", orderNo);    //商户订单号
            paramMap.put("total_fee", total_fee);        //标价金额
            paramMap.put("spbill_create_ip", getIpAddress(request));//终端IP
            paramMap.put("notify_url", W_NOTIFY_URL);        //通知地址
            paramMap.put("trade_type", "JSAPI");    //交易类型
            paramMap.put("openid", openid);
            paramMap.put("sign", PaymentKit.createSign( paramMap, PATERNER_KEY));



            // 获取用户信息
//            https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN
            String userInfoString = rest.getForObject(new URI(GETUSERINFO_URL+"?access_token="+access_token+"&openid="+ openid+"&lang=zh_CN"), String.class);
            JSONObject userInfoJsonObject = JSONObject.parseObject(userInfoString);
            System.out.println("***userInfoJsonObject:"+userInfoJsonObject);
            wechatUserService.saveWechatUser(userInfoJsonObject);


            String mapToXml =PaymentKit.toXml(paramMap);

            System.out.println(mapToXml);
            System.out.println("----------------------------------------------------------");
            String postForObject = rest.postForObject(new URI(UNIFIEDORDER_URL), mapToXml, String.class);
            System.out.println("***postForObject:"+postForObject);



            //支付成功，待消费状态
            if (postForObject.indexOf("SUCCESS") != -1) {
                Map<String, String> map = PaymentKit.xmlToMap(postForObject);
                prepay_id = (String) map.get("prepay_id");
                order.setStatus("0");
            }else{
                //支付失败
                order.setStatus("3");
            }

            packageParams.put("appId", APPID);
            packageParams.put("timeStamp", System.currentTimeMillis() / 1000 + "");
            packageParams.put("nonceStr", System.currentTimeMillis() + "");
            packageParams.put("package", "prepay_id=" + prepay_id);
            packageParams.put("signType", "MD5");
            String packageSign = com.jfinal.weixin.sdk.kit.PaymentKit.createSign(packageParams, paternerKey);
            packageParams.put("paySign", packageSign);

            String jsonStr = JsonUtils.toJson(packageParams);
            System.out.println(jsonStr);

            //保存订单
            order.setTradeNo(order_id);
            order.setOpenid(openid);
            order.setNonceStr(paramMap.get("nonce_str"));
            order.setSpbillCreateIp(  paramMap.get("spbill_create_ip" ));
            order.setTradeType(  paramMap.get("trade_type" ));

            //order.setTotalAmount( new BigDecimal( total_fee ).divide(new BigDecimal(100) ,2, BigDecimal.ROUND_HALF_UP) );
            order.setCreateDate(new Date());
            orderService.updateById(order);


        } catch (Exception e) {
            retMap.put("code", "500");
            retMap.put("msg", e.getStackTrace());
            e.printStackTrace();
        }
        String timeStamp=packageParams.get("timeStamp") ;
        String paySign=packageParams.get("paySign") ;
        String appId=APPID;
        String nonceStr=packageParams.get("nonceStr") ;
        System.out.println(">>>codeDemo.html");
        response.sendRedirect("http://www.yushangcc.com/static/wxpay.html?timeStamp="+timeStamp+"&package_="+prepay_id+"&paySign="+paySign+"&appId="+appId+"&nonceStr="+nonceStr);

    }


    /**
     * 微信异步回调通知
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping("/pay_notify.do")
    public String notifyUrl(HttpServletRequest request,HttpServletResponse response){
        System.out.println("**WxPayController.notifyUrl()");
        Map<String, Object> retMap = new HashMap<String, Object>();
        retMap.put("code", 200);
        InputStream is = null;
        try {
            is = request.getInputStream();//获取请求的流信息(这里是微信发的xml格式所有只能使用流来读)
            String xml = IOUtils.toString(is);
            System.out.println("***xml:"+xml);
            Map<String, String> notifyMap = PaymentKit.xmlToMap(xml);//将微信发的xml转map
            System.out.println("***notifyMap:"+notifyMap);
            String nonce_str = notifyMap.get("nonce_str") ;
            String openid  = notifyMap.get("openid") ;
            Order order = orderService.getOrderByPay(openid,nonce_str);

            if(notifyMap.get("return_code").equals("SUCCESS")){
                if(notifyMap.get("result_code").equals("SUCCESS")){
                    String ordersSn = notifyMap.get("out_trade_no");//商户订单号
                    String amountpaid = notifyMap.get("total_fee");//实际支付的订单金额:单位 分
                    BigDecimal amountPay = (new BigDecimal(amountpaid).divide(new BigDecimal("100"))).setScale(2);//将分转换成元-实际支付金额:元
                    System.out.println("***notifyUrl.htm data:"+ordersSn+"---"+amountPay);
                    order.setStatus("2");

                    //添加任务队列
                    sendDirectMessage(order.getId()) ;
                }else{
                    order.setStatus("3");
                }
            }


            order.setPayStatus(notifyMap.get("result_code"));
            order.setPayUpdatetime(new Date());
            order.setPayInfo(notifyMap.get("time_end"));
            orderService.updateOrderPay(order);
            //告诉微信服务器收到信息了，不要在调用回调action了========这里很重要回复微信服务器信息用流发送一个xml即可
//            response.getWriter().write("<xml><return_code><![CDATA[SUCCESS]]></return_code></xml>");
//            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "<xml><return_code><![CDATA[SUCCESS]]></return_code></xml>";
    }



    @ResponseBody
    @RequestMapping("anotifyUrl.htm")
    public Map<String, Object> anotifyUrl(HttpServletRequest request){
        System.out.println("***WxPayController.anotifyUrl()");
        Map<String, Object> retMap = new HashMap<String, Object>();
        retMap.put("code", 200);
        //进行验证操作
        Map<String, String> params = new HashMap<String, String>();
        Map requestParams = request.getParameterMap();
        System.out.println("***requestParams:"+requestParams);
        for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = (String) iter.next();
            System.out.println("___________name:"+name);
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++)
                valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
            params.put(name, valueStr);
        }
        String trade_status = request.getParameter("trade_status");

        return retMap;
    }



    // 获取请求主机IP地址,如果通过代理进来，则透过防火墙获取真实IP地址
    public final static String getIpAddress(HttpServletRequest request){
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("Proxy-Client-IP");
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("WL-Proxy-Client-IP");
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("HTTP_CLIENT_IP");
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("HTTP_X_FORWARDED_FOR");
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getRemoteAddr();
            }
        } else if (ip.length() > 15) {
            String[] ips = ip.split(",");
            for (int index = 0; index < ips.length; index++) {
                String strIp = (String) ips[index];
                if (!("unknown".equalsIgnoreCase(strIp))) {
                    ip = strIp;
                    break;
                }
            }
        }
        return ip;
    }


    public void sendDirectMessage(String orderId) {
        String messageId = String.valueOf(UUID.randomUUID());
        String messageData = "test message, hello!";
        String createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        Map<String,Object> map=new HashMap<>();
        map.put("orderId",orderId);
        map.put("messageData",messageData);
        map.put("createTime",createTime);

        //将消息携带绑定键值：TestDirectRouting 发送到交换机TestDirectExchange
        rabbitTemplate.convertAndSend("TestDirectExchange", "TestDirectRouting", JSONObject.toJSONString(JSONObject.toJSON(map)));
    }

}
