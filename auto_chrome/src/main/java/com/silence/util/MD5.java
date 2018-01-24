package com.silence.util;

import java.nio.charset.Charset;
import java.security.MessageDigest;

public class MD5 {
	/**
	 * md5
	 * @param b
	 * @return
	 */
	public static String byte2hexMd5(byte[] b) {
		String hs = "";
		String temp = "";
		for (int n = 0; n < b.length; n++) {
			temp = Integer.toHexString(b[n] & 0XFF);
			if (temp.length() == 1) {
				hs = hs + "0" + temp;
			} else {
				hs = hs + temp;
			}
		}
		return hs.toUpperCase();
	}
	/**
	 * 计算字符串MD5值
	 * @param content
	 * @return
	 */
	public static String getMD5Data(String content) {
		try {
			byte[] src = content.getBytes(Charset.forName("UTF-8"));
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			md5.update(src);
			return byte2hexMd5(md5.digest()).toLowerCase();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	

}
