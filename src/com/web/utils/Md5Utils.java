package com.web.utils;

import org.springframework.security.authentication.encoding.Md5PasswordEncoder;

import com.web.entity.User;

public class Md5Utils {
	/**
	 * 默认新建用户用员工编号做密码
	 * 
	 * @param user
	 * @return
	 */
	public static String md5(User user) {
		Md5PasswordEncoder md5 = new Md5PasswordEncoder();
		md5.setEncodeHashAsBase64(false);

		return md5.encodePassword(user.getUserID(), user.getUserID());
	}

	/**
	 * 用于修改密码
	 * 
	 * @param password
	 * @param salt
	 * @return
	 */
	public static String md5(String password, String salt) {
		Md5PasswordEncoder md5 = new Md5PasswordEncoder();
		md5.setEncodeHashAsBase64(false);

		return md5.encodePassword(password, salt);
	}

}
