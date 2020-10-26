//package cn.licoy.wdog.core.controller.wxpay;
//
//import org.apache.commons.lang3.RandomStringUtils;
//import org.apache.commons.lang3.StringUtils;
//import org.springframework.ui.ModelMap;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.util.HashMap;
//import java.util.Map;
//
//
//public class WeixinPay {
//
//
//    /**
//     * 微信统一下单
//     *
//     * @param trade_type 交易类型
//     * @param openId     用户的openId
//     * @param request
//     * @param wxReturn   微信支付异步回调地址
//     * @param orderNo    订单号
//     * @param price      订单金额
//     * @return
//     */
//    public static Map<String, String> weixinUniformOrder(String openId, HttpServletRequest request, String wxReturn, String orderNo, Double price) {
//        Map<String, String> paramMap = new HashMap<String, String>();
//        // 微信分配的公众账号ID（企业号corpid即为此appId）[必填]
//        paramMap.put("appid", PaymentConfig.appid);
//        // 微信支付分配的商户号 [必填]
//        paramMap.put("mch_id", PaymentConfig.mch_id);
//        // 终端设备号(门店号或收银设备ID)，注意：PC网页或公众号内支付请传"WEB" [非必填]
//        paramMap.put("device_info", "WEB");
//        // 随机字符串，不长于32位。 [必填]
//        paramMap.put("nonce_str", RandomStringUtils.random(10, Num62.N62_CHARS));
//        // 商品或支付单简要描述 [必填]
//        paramMap.put("body", PaymentConfig.mch_id);
//        // 商户系统内部的订单号,32个字符内、可包含字母, [必填]
//        paramMap.put("out_trade_no", orderNo);
//        // 符合ISO 4217标准的三位字母代码，默认人民币：CNY. [非必填]
//        paramMap.put("fee_type", "CNY");
//
//        // 金额必须为整数 单位为分 [必填]
//        paramMap.put("total_fee", PayUtil.changeY2F(price));
//        // APP和网页支付提交用户端ip，Native支付填调用微信支付API的机器IP [必填]
//        paramMap.put("spbill_create_ip", request.getRemoteAddr());
//        // 接收微信支付异步通知回调地址，通知url必须为直接可访问的url，不能携带参数。 [必填]
//        paramMap.put("notify_url", wxReturn);
//        // 交易类型{取值如下：JSAPI，NATIVE，APP，(JSAPI--公众号支付、NATIVE--原生扫码支付、APP--app支付)}
//        // [必填]
//        paramMap.put("trade_type", "JSAPI");
//        //openid trade_type=JSAPI，此参数必传，用户在商户appid下的唯一标识
//        paramMap.put("openid", openId);
//        // 商品ID{trade_type=NATIVE，此参数必传。此id为二维码中包含的商品ID，商户自行定义。}
//        paramMap.put("product_id", "商品ID");
//        // 根据微信签名规则，生成签名
//        paramMap.put("sign", PayUtil.createSign(paramMap, PaymentConfig.appKey));
//        // 把参数转换成XML数据格式
//        String xmlWeChat = PayUtil.assembParamToXml(paramMap);
//        String resXml = HttpClientUtil.post(PaymentConfig.pay_url, xmlWeChat);
//        Map<String, String> map = new HashMap<String, String>();
//        try {
//            if (StringUtils.isNotBlank(resXml)) {
//                map = PayUtil.parseXMLToMap(resXml);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return map;
//    }
//
//
//    /**
//     * 微信公众号支付(手机端)
//     *
//     * @param wxReturn 微信支付异步回调地址
//     * @param openId   用户的openid
//     * @param orderNo  订单号
//     * @param price    订单金额
//     * @param request
//     * @param response
//     * @param model
//     * @return
//     */
//    public static String weixinPayByMobile(String wxReturn, String openId, String orderNo, Double price,
//                                           HttpServletRequest request, HttpServletResponse response,
//                                           ModelMap model) {
//        Map<String, String> map = weixinUniformOrder(openId,request, wxReturn, orderNo, price);
//        String returnCode = map.get("return_code");
//        if (returnCode.equalsIgnoreCase("FAIL")) {
//
//            //调用失败操作
//            //当return_code为FAIL时返回信息为错误原因 ，例如 签名失败、参数格式校验错误
//            map.get("return_msg");
//
//        } else if (returnCode.equalsIgnoreCase("SUCCESS")) {
//            if (map.get("err_code") != null) {
//
//                //支付失败操作
//                //错误代码描述
//                map.get("err_code_des");
//
//
//            } else if (map.get("result_code").equalsIgnoreCase(
//                    "SUCCESS")) {
//                String prepay_id = map.get("prepay_id");
//                Long time = System.currentTimeMillis() / 1000;
//                String nonceStr = RandomStringUtils.random(16, Num62.N10_CHARS);
//                //公众号appid
//                model.addAttribute("appId", PaymentConfig.appid);
//                //时间戳 当前的时间 需要转换成秒
//                model.addAttribute("timeStamp", time);
//                //随机字符串  不长于32位
//                model.addAttribute("nonceStr", nonceStr);
//                //订单详情扩展字符串 统一下单接口返回的prepay_id参数值，提交格式如：prepay_id=***
//                model.addAttribute("package", "prepay_id=" + prepay_id);
//                //签名方式 签名算法，暂支持MD5
//                model.addAttribute("signType", "MD5");
//                Map<String, String> paramMap = new HashMap<String, String>();
//                paramMap.put("appId", PaymentConfig.appid);
//                paramMap.put("timeStamp", time.toString());
//                paramMap.put("nonceStr", nonceStr);
//                paramMap.put("package", "prepay_id=" + prepay_id);
//                paramMap.put("signType", "MD5");
//                //签名
//                model.addAttribute("paySign", PayUtil.createSign(paramMap, PaymentConfig.appKey));
//
//                //跳转到 weixin_prepay.html
//                return "weixin_prepay.html";
//            }
//        }
//        // 失败提示跳转页面，自己设计
//        return "error.html";
//    }
//
//
//    //通知微信正确接收
//    public static String getSuccessXml() {
//        String xml = "<xml>" +
//                "<return_code><![CDATA[SUCCESS]]></return_code>" +
//                "<return_msg><![CDATA[OK]]></return_msg>" +
//                "</xml>";
//        return xml;
//    }
//
//}
