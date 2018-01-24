package com.silence.util;

import java.util.Random;

/**
 * 生成随机数据的工具
 * @author Administrator
 *
 */
public class RandomUtil {
	 private static final char[] chars = {
				'0','1','2','3','4',
				'5','6','7','8','9',
				
				'A','B','C','D','E','F','G',
				'H','I','J','K','L','M','N',
				'O','P','Q','R','S','T',
				'U','V','W','X','Y','Z',
				
				'a','b','c','d','e','f','g',
				'h','i','j','k','l','m','n',
				'o','p','q','r','s','t',
				'u','v','w','x','y','z'
			};
	 /**
	  * 生成随机游客名字
	  * @param prefix
	  * @param len
	  * @return
	  */
	public static String getRandomGuestName(String prefix,int len) {
		String guestName = prefix;
		Random ran = new Random();
		for(int i = 0; i < len;i++){
			int index = ran.nextInt(chars.length);
			guestName+=chars[index];
		}
		return guestName;
	}
}
