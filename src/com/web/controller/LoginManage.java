package com.web.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.web.base.BaseAction;
import com.web.entity.User;
import com.web.utils.CodeUtils;
import com.web.utils.Md5Utils;

@Controller
@RequestMapping("/loginManage")
public class LoginManage extends BaseAction {

	private String imageCode;

	/**
	 * 验证码检验
	 * 
	 * @return
	 */
	@RequestMapping(value = "/validCode")
	@ResponseBody
	public String code() {
		return imageCode;
	}

	@RequestMapping(value = "/validUser")
	@ResponseBody
	public boolean validUser(String userID, String password) {
		User user = userService.getAccount(userID);
		if (user == null) {
			return false;
		} else if (!Md5Utils.md5(password, userID).equals(user.getPassword())) {
			return false;
		}

		return true;
	}

	@RequestMapping(value = "/code")
	public String code(HttpSession session, HttpServletResponse response) throws Exception {
		CodeUtils.createImage();
		imageCode = CodeUtils.code;
		// 保存在Session
		if (session.getAttribute("imageCode") != null) {
			session.removeAttribute("imageCode");
		}
		session.setAttribute("imageCode", imageCode);

		response.setContentType("image/jpeg");
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", -1);

		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;

		try {
			bis = new BufferedInputStream(CodeUtils.imageStream);
			bos = new BufferedOutputStream(response.getOutputStream());

			byte[] buffer = new byte[1024];
			int len = 0;
			while ((len = bis.read(buffer)) != -1) {
				bos.write(buffer, 0, len);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (bis != null) {
				bis.close();
			}
			if (bos != null) {
				bos.close();
			}

		}
		return null;
	}

}
