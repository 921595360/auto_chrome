package com.silence.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by tanxy on 2016/8/8.
 * 读取properties文件
 */
public class PropertiesUtil {

    //文件名称
    private static final String fileName="application.properties";

    private static Properties properties;

    static{
        InputStream inputStream = PropertiesUtil.class.getClassLoader().getResourceAsStream("application.properties");
         properties = new Properties();
        try {
            properties.load(inputStream);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    /**
     * 获取属性值
     * @param property 属性名称
     * @param defValue 默认值
     * @return
     */
    public static String get(String property,String defValue){
        Object result=properties.get(property);
        if(result==null) return defValue;
        return result.toString();
    }

}
