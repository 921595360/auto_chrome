package com.silence.common;

import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 常用工具类
 * @author YANGSONG

 */
public class CommonUtils {
    /**
     * 判断集合是否为NULL或空
     * @param collection 集合
     * @return 如果此collection为null或不包含元素，则返回 true
     */
    public static boolean isNullOrEmpty(Collection collection) {
        return collection == null || collection.isEmpty();
    }

    /**
     * 判断MAP是否为NULL或空
     * @param map MAP
     * @return 如果此map为null或不包含元素，则返回 true
     */
    public static boolean isNullOrEmpty(Map map) {
        return map == null || map.isEmpty();
    }

    /**
     * 判断字符串是否为NULL或空
     * @param s 字符串
     * @return 如果此map为null或空，则返回 true
     */
    public static boolean isNullOrEmpty(String s) {
        return StringUtils.isEmpty(s);
    }

    /**
     * 判断Object是否为NULL
     * @param obj Object
     * @return true/false
     */
    public static boolean isNull(Object obj) {
        return obj == null;
    }

    /**
     * 判断字符串数组是否为NULL或空
     * @param s 字符串数组
     * @return 如果此map为null或空，则返回 true
     */
    public static boolean isNullOrEmptys(String[] s) {
        return s == null || s.length == 0;
    }

   

    /**
     * 值空处理
     * @param value 原始值
     * @return 处理值
     */
    public static String fixNull(String value) {
        return value == null || value.toUpperCase().equals("NULL") ? "" : value;
    }

    /**
     * 是否数字
     * @param str 字符串
     * @return true/false
     */
    public static boolean isDigit(String str) {
        Pattern pattern = Pattern.compile("[0-9]+(.[0-9]+)?");
        Matcher isNum = pattern.matcher(str.trim());
        return isNum.matches();
    }
    
    /**
     * 
     * @param request
     * @return
     */
    public static String getIpByRequest(HttpServletRequest request){
    	String ip = request.getHeader("x-forwarded-for");  
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
			ip = request.getHeader("Proxy-Client-IP");  
		}  
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
			ip = request.getHeader("WL-Proxy-Client-IP");  
		}  
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
			ip = request.getRemoteAddr();  
		}
    	return ip;
    }
}
