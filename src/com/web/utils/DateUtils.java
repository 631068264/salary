package com.web.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
	/**
	 * 打印表的年月
	 * 
	 * @return
	 */
	public static String getDateYM() {
		Date date = new Date();
		DateFormat df = new SimpleDateFormat("yyyy-MM");

		return df.format(date);
	}
}
