package com.silence.util;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by tanxy on 2016/8/8.
 * 读取properties文件
 */
public class Properties {



    private java.util.Properties properties;

    public Properties(String fileName){
        InputStream inputStream = Properties.class.getClassLoader().getResourceAsStream(fileName);
        this.properties=new java.util.Properties();
        try {
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 获取属性值
     * @param property 属性名称
     * @param defValue 默认值
     * @return
     */
    public String get(String property,String defValue){
        Object result=properties.get(property);
        if(result==null) return defValue;
        return result.toString();
    }


    public String get(String key) {
        return properties.get(key).toString();
    }
}
