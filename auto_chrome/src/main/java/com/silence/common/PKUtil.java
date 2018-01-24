package com.silence.common;

import java.util.UUID;

/**
 * 生成主键
 *   
 * PKUtil  
 *   
 * 2016年1月4日 下午2:51:34  
 *   
 * @version 1.0.0  
 *
 */
public class PKUtil {
	public static String getPrimarykeyStr() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
	/** 
     * 获得一个UUID 
     * @return String UUID 
     */ 
    public static String getUUID(){ 
    	String s = UUID.randomUUID().toString();
        //去掉“-”符号 
        return s.replaceAll("-", ""); 
    } 
    /** 
     * 获得指定数目的UUID 
     * @param number int 需要获得的UUID数量 
     * @return String[] UUID数组 
     */ 
    public static String[] getUUID(int number){ 
        if(number < 1){ 
            return null; 
        } 
        String[] ss = new String[number]; 
        for(int i=0;i<number;i++){ 
            ss[i] = getUUID(); 
        } 
        return ss; 
    } 
}
