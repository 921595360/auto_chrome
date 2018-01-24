package com.silence.util;

import java.util.UUID;

public class PKUtils {
	public static String getPrimarykeyStr() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
}
