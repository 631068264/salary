package com.web.utils;

public class StringUtils {

	public static final String EMPTY = "";

	public static String trimToEmpty(String str) {
		if (str == null || "".equals(str)) {
			str = "";
		} else {
			str = str.trim();
		}
		return str;
	}

	public static boolean isEmpty(String str) {
		if (str == null || "".equals(str)) {
			return true;
		}
		return false;
	}

}
