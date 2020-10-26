package cn.licoy.wdog.common.util;
import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;


import java.net.URLEncoder;

public class SmsUtilCR {
    //普通短信
    public void sendsms(String mobile,String orderId,String url)  {
        try{
            HttpClient httpClient = new HttpClient();
            PostMethod postMethod = new PostMethod("http://api.1cloudsp.com/api/v2/single_send");
            postMethod.getParams().setContentCharset("UTF-8");
            postMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,new DefaultHttpMethodRetryHandler());

            String accesskey = "jgRrycT627XTXgOG"; //用户开发key
            String accessSecret = "bxpyi5oQ6N0QnMgRBbmAYljqEwK8EeVz"; //用户开发秘钥

            NameValuePair[] data = {
                    new NameValuePair("accesskey", accesskey),
                    new NameValuePair("secret", accessSecret),
                    new NameValuePair("sign", "【溪芷汀兰】"),
                    new NameValuePair("templateId", "152848"),
                    new NameValuePair("mobile", mobile),
                    new NameValuePair("content", URLEncoder.encode(orderId, "utf-8"))//（示例模板：{1}您好，您的订单于{2}已通过{3}发货，运单号{4}）
            };
            postMethod.setRequestBody(data);
            postMethod.setRequestHeader("Connection", "close");

            int statusCode = httpClient.executeMethod(postMethod);
            System.out.println("statusCode: " + statusCode + ", body: "
                    + postMethod.getResponseBodyAsString());
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        SmsUtilCR t = new SmsUtilCR();
        //普通短信
        t.sendsms("18881613116","232345353252","http://t.cn/AiQ5LkM1");
    }
}
