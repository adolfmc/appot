package cn.licoy.wdog.core.controller.wxpay;

public class PaymentConfig {
    /*******微信支付参数*********/
    //公众账号ID
    public static final String appid="wxd";
    //密钥
    public static final String appKey="h";
    //商户号
    public static final String mch_id="1584";
    //接口地址
    public static final String pay_url="https://api2.mch.weixin.qq.com/pay/unifiedorder";

//交易场景信息
    public static final String scene_info = "{\"store_info\" : {\"id\": \"zxbm\",\"name\": \"支付\",\"area_code\": \"430000\",\"address\": \"中国\" }}";


}
