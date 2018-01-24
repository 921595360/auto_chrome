package com.silence.util;


import javax.servlet.http.Cookie;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;


/**
 * 
 * @author Administrator
 *
 */
public class CookieUtil {
	
	public static String[] getUserNameAndPwdFromCookies(Cookie[] cookies) {
		if(cookies == null){
			return new String[]{};
		}
		String username = "";
		String password = "";
		for(Cookie c :cookies){
			String cookieName = c.getName();
			if("username".equals(cookieName)){
				try {
					//支持中文用户名
					username = URLDecoder.decode(c.getValue(), "UTF-8");
				} catch (UnsupportedEncodingException e) {
					username =c.getValue();
					e.printStackTrace();
				}
			}
			if("password".equals(cookieName)){
				password = c.getValue();
			}
		}
		return new String[]{username,password};
	}

	/**
	 * Get the Cookie named <parameter>name</parameter> from Cookie array <parameter>cookies</parameter>
	 * @param name
	 * @param cookies
	 * @return
	 */
	public static Cookie get(String name, Cookie[] cookies) {
		Cookie resultCookie = null;
		//四大皆空，一切皆空;
		if(name == null||name==""||cookies ==null||cookies.length==0){
			return resultCookie;
		}else{
			for(Cookie c:cookies){
				String cName = c.getName();
				if(name.equals(cName)){
					resultCookie = c;
					break;
				}
			}
		}
		return resultCookie;
	}
}

