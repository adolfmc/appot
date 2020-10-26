package cn.licoy.wdog.common.util;

import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;

import com.tencentcloudapi.sms.v20190711.SmsClient;
import com.tencentcloudapi.sms.v20190711.models.*;;

public class SmsUtilTX
{
    public static String  sendSMS(String mobile ,String info,String templateId){
        SendSmsResponse resp = null ;
        try{
            Credential cred = new Credential("AKIDPr8BzFgF6nGfuvrT0c2ILVSAdstgbq8C", "ykKR4xX8TZP1Ncy74gGcliFd2H5dmgK5");
            HttpProfile httpProfile = new HttpProfile();
            httpProfile.setEndpoint("sms.tencentcloudapi.com");

            ClientProfile clientProfile = new ClientProfile();
            clientProfile.setHttpProfile(httpProfile);

            SmsClient client = new SmsClient(cred, "", clientProfile);

            SendSmsRequest req = new SendSmsRequest();
            String[] phoneNumberSet1 = {"86"+mobile};
            req.setPhoneNumberSet(phoneNumberSet1);

            req.setTemplateID(templateId);
            req.setSign("自动预约SAAS平台");

            String[] templateParamSet1 = {info};
            req.setTemplateParamSet(templateParamSet1);
            req.setSmsSdkAppid("1400423246");
            resp = client.SendSms(req);
        } catch (TencentCloudSDKException e) {
            System.out.println(e.toString());
        }

        return SendSmsResponse.toJsonString(resp);
    }
    public static void main(String [] args) {
//        System.out.println(SmsUtilTX.sendSMS("15021008580","2342cc","715954") );
        String verCode = AppotUtils.getRandomNO();
        System.out.println( verCode );
        String mobile = "15021008580";
        System.out.println(SmsUtilTX.sendSMS(mobile,verCode,"715954"));
//        System.out.println( String.valueOf(Math.random()*1000000000).substring(0,4) );
    }


}
