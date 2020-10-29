package cn.licoy.wdog.core.vo.appot.json;

import com.alibaba.fastjson.JSON;
import org.apache.commons.io.FileUtils;

import java.io.File;

public class Main {
    public static void main(String[] args) throws  Exception{
        String jsonObject  = FileUtils.readFileToString(new File("E:\\Administor\\git\\appot_server\\src\\main\\resources\\static\\assistant\\js\\siteJson.json"),"UTF-8");
        ExampleClass newPerson = JSON.parseObject(jsonObject, ExampleClass.class);

        System.out.println(newPerson);
    }
}
