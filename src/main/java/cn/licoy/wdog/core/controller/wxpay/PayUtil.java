//package cn.licoy.wdog.core.controller.wxpay;
//
//import org.apache.commons.lang3.StringUtils;
//import org.jdom.Document;
//import org.jdom.Element;
//import org.jdom.JDOMException;
//import org.jdom.input.SAXBuilder;
//
//import java.io.*;
//import java.security.MessageDigest;
//import java.util.*;
//
//
//public class PayUtil {
//
//
//
//
//    /**
//    * 将需要传递给微信的参数转成xml格式
//    * @param parameters
//    * @return
//    */
//    public static String assembParamToXml(Map<String,String> parameters){
//        StringBuffer sb = new StringBuffer();
//        sb.append("<xml>");
//        Set<String> es = parameters.keySet();
//        List<Object> list=new ArrayList<Object>(es);
//        Object[] ary =list.toArray();
//        Arrays.sort(ary);
//        list=Arrays.asList(ary);
//        Iterator<Object> it = list.iterator();
//        while(it.hasNext()) {
//            String key =  (String) it.next();
//            String val=(String) parameters.get(key);
//            if ("attach".equalsIgnoreCase(key)||"body".equalsIgnoreCase(key)||"sign".equalsIgnoreCase(key)) {
//                sb.append("<"+key+">"+"<![CDATA["+val+"]]></"+key+">");
//            }else {
//                sb.append("<"+key+">"+val+"</"+key+">");
//            }
//        }
//        sb.append("</xml>");
//        return sb.toString();
//    }
//
//
//    /**
//    * 解析xml,返回第一级元素键值对。如果第一级元素有子节点，则此节点的值是子节点的xml数据。
//    * @param strxml
//    * @return
//    * @throws JDOMException
//    * @throws IOException
//    */
//    public static Map parseXMLToMap(String strxml) throws JDOMException, IOException {
//        strxml = strxml.replaceFirst("encoding=\".*\"", "encoding=\"UTF-8\"");
//        if(null == strxml || "".equals(strxml)) {
//            return null;
//        }
//        Map m = new HashMap();
//        InputStream in = new ByteArrayInputStream(strxml.getBytes("UTF-8"));
//        SAXBuilder builder = new SAXBuilder();
//        Document doc = builder.build(in);
//        Element root = doc.getRootElement();
//        List list = root.getChildren();
//        Iterator it = list.iterator();
//        while(it.hasNext()) {
//            Element e = (Element) it.next();
//            String k = e.getName();
//            String v = "";
//            List children = e.getChildren();
//            if(children.isEmpty()) {
//                v = e.getTextNormalize();
//            } else {
//                v =PayUtil.getChildrenText(children);
//            }
//            m.put(k, v);
//        }
//        //关闭流
//        in.close();
//        return m;
//    }
//
//    /**
//    * 获取子结点的xml
//    * @param children
//    * @return String
//    */
//    public static String getChildrenText(List children) {
//        StringBuffer sb = new StringBuffer();
//        if(!children.isEmpty()) {
//            Iterator it = children.iterator();
//            while(it.hasNext()) {
//                Element e = (Element) it.next();
//                String name = e.getName();
//                String value = e.getTextNormalize();
//                List list = e.getChildren();
//                sb.append("<" + name + ">");
//                if(!list.isEmpty()) {
//                    sb.append(getChildrenText(list));
//                }
//                sb.append(value);
//                sb.append("</" + name + ">");
//            }
//        }
//        return sb.toString();
//    }
//
//    /**
//    * 微信支付签名sign
//    * @param param
//    * @param key
//    * @return
//    */
//    @SuppressWarnings("unchecked")
//    public static String createSign(Map<String, String> param,String key){
//        //签名步骤一：按字典排序参数
//        List list=new ArrayList(param.keySet());
//        Object[] ary =list.toArray();
//        Arrays.sort(ary);
//        list=Arrays.asList(ary);
//        String str="";
//        for(int i=0;i<list.size();i++){
//            str+=list.get(i)+"="+param.get(list.get(i)+"")+"&";
//        }
//        //签名步骤二：加上key
//        str+="key="+key;
//        //步骤三：加密并大写
//        str=PayUtil.MD5Encode(str,"utf-8").toUpperCase();
//        return str;
//    }
//
//    public static String MD5Encode(String origin,String charsetName){
//        String resultString=null;
//        try{
//            resultString=new String(origin);
//            MessageDigest md=MessageDigest.getInstance("MD5");
//            if(StringUtils.isBlank(charsetName)){
//                resultString=byteArrayToHexString(md.digest(resultString.getBytes()));
//            }else{
//                resultString=byteArrayToHexString(md.digest(resultString.getBytes(charsetName)));
//            }
//        }catch(Exception e){
//
//        }
//        return resultString;
//    }
//
//    public static String byteArrayToHexString(byte b[]){
//        StringBuffer resultSb=new StringBuffer();
//        for(int i=0;i<b.length;i++){
//            resultSb.append(PayUtil.byteToHexString(b[i]));
//        }
//        return resultSb.toString();
//    }
//
//    public static String byteToHexString(byte b){
//        int n=b;
//        if(n<0){
//            n+=256;
//        }
//        int d1=n/16;
//        int d2=n%16;
//        return PayUtil.hexDigits[d1]+PayUtil.hexDigits[d2];
//    }
//
//    public static final String hexDigits[]={ "0", "1", "2", "3", "4", "5",
//    "6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };
//
//
//    /**
//     * 元转换为分
//     * @param amount
//     */
//    public static String changeY2F(Double amount){
//            String currency =  amount.toString();
//            int index = currency.indexOf(".");
//            int length = currency.length();
//            Long amLong = 0l;
//            if(index == -1){
//                amLong = Long.valueOf(currency+"00");
//            }else if(length - index >= 3){
//                amLong = Long.valueOf((currency.substring(0, index+3)).replace(".", ""));
//            }else if(length - index == 2){
//                amLong = Long.valueOf((currency.substring(0, index+2)).replace(".", "")+0);
//            }else{
//                amLong = Long.valueOf((currency.substring(0, index+1)).replace(".", "")+"00");
//            }
//            return amLong.toString();
//    }
//
//}
