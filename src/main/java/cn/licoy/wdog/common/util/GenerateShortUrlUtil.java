package cn.licoy.wdog.common.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

/**
 * 生成短网址并返回
 * @author: Zhusw
     * @date: 2015年10月19日上午9:58:54
 */
public class GenerateShortUrlUtil {
    public static DefaultHttpClient httpclient;
    static {
        httpclient = new DefaultHttpClient();
    }

    public static void main(String[] args) throws Exception {
        String url_in = "http://tea.adparticle.com:8001/order_sms.html?id=1161905570414432257";
        String url = "https://api.weibo.com/2/short_url/shorten.json?source=3818214747&url_long="+url_in; //source=209678993
        URL aURL = new URL(url);
        HttpURLConnection aConnection = (java.net.HttpURLConnection) aURL.openConnection();
        InputStream resultStream = aConnection.getInputStream();

        String responseData = streamToString(resultStream);
        JSONObject.parseObject(responseData);
        String urls = JSONObject.parseObject(responseData).getString("urls");
        String url_short = "";
        if( !JSONObject.parseArray(urls).isEmpty() ){
            url_short = JSONObject.parseObject(JSONObject.parseArray(urls).get(0).toString()).getString("url_short");
        }
        GenerateShortUrlUtil t =new GenerateShortUrlUtil();
        System.out.println(t.getShortUrl(url_in));
    }


    public  String getShortUrl(String url_in) {
        String url_short = "";
        try{
            String url = "https://api.weibo.com/2/short_url/shorten.json?source=3818214747&url_long="+url_in; //source=209678993
            URL aURL = new URL(url);
            HttpURLConnection aConnection = (java.net.HttpURLConnection) aURL.openConnection();
            InputStream resultStream = aConnection.getInputStream();
            String responseData = streamToString(resultStream);
            String urls = JSONObject.parseObject(responseData).getString("urls");
            if( !JSONObject.parseArray(urls).isEmpty() ){
                url_short = JSONObject.parseObject(JSONObject.parseArray(urls).get(0).toString()).getString("url_short");
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return url_short;
    }

    private static String streamToString(InputStream resultStream)
            throws IOException {
        BufferedReader aReader = new java.io.BufferedReader(
                new java.io.InputStreamReader(resultStream));
        StringBuffer aResponse = new StringBuffer();
        String aLine = aReader.readLine();
        while (aLine != null) {
            aResponse.append(aLine + "\n");
            aLine = aReader.readLine();
        }
        return aResponse.toString();

    }
}
