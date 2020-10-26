//package cn.licoy.wdog.core.controller.wxpay;
//
//
//import org.apache.commons.lang3.StringUtils;
//import org.jdom.JDOMException;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.ModelMap;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.*;
//import java.util.*;
//
//
//@Controller
//public class WeixinPayAct {
//
//    /**
//     * 微信支付
//     *
//     * @param request
//     * @param response
//     * @param model
//     * @return
//     */
//    @RequestMapping(value = "weixin_Pay.jspx")
//    public String selectPay(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
//
//        String wxReturn = "微信支付异步回调地址";
//        String orderNo = "订单号";
//        String openId = "用户的openId";
//        //支付金额
//        Double price = 0.01;
//
//        return WeixinPay.weixinPayByMobile(wxReturn, openId, orderNo, price, request, response, model);
//
//    }
//
//
//    /**
//     * 微信支付通知
//     *
//     * @return
//     */
//    @ResponseBody
//    @RequestMapping(value = "/wechart_notice.jspx")
//    public String wechartNotice(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
//        String result = "";
//        try {
//            InputStream inStream = request.getInputStream();
//            ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
//            byte[] buffer = new byte[1024];
//            int len = 0;
//            while ((len = inStream.read(buffer)) != -1) {
//                outSteam.write(buffer, 0, len);
//            }
//            outSteam.close();
//            inStream.close();
//            result = new String(outSteam.toByteArray(), "utf-8");
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//        //判断返回报文是否为空
//        if (StringUtils.isNotBlank(result)) {
//            try {
//                System.out.println("");
////                Map<String, String> map = XMLUtil.doXMLParse(result);
////                //获取商家订单编号 对应orderID
////                String orderNo = map.get("out_trade_no");
////                //String orderNo = RequestUtils.getQueryParam(request,"orderNo");
////                //获取微信支付订单号
////                String transaction_id = map.get("transaction_id");
//
////                Order order = orderMng.findByOrderNo(orderNo);
////                //判断支付是否成功
////                if ("SUCCESS".equals(map.get("result_code"))) {
////                    //支付成功，这里之所以加了一个判断，是因为这个回调可能会有多次，所以我们只有当订单状态时未支付的情况下，才执行下面操作
////                    if (!Constants.ORDER_SUCCESS.equals(order.getStatus())) {
////                        //当微信支付成功后，把订单支付状态改为已支付
////                        order.setStatus(Constants.ORDER_SUCCESS);
////                    }
////                    //处理业务逻辑
////                } else {
////                    //支付失败
////                    order.setStatus(Constants.ORDER_FAIL);
////                }
////                orderMng.update(order);
//            } catch (Exception e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
//        }
//        return WeixinPay.getSuccessXml();
//    }
//
//
//}
