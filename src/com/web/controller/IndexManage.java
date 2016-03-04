package com.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.web.base.BaseAction;
import com.web.entity.Privilege;
import com.web.entity.User;

@Controller
@RequestMapping("/indexManage")
@SessionAttributes("user")
public class IndexManage extends BaseAction {
	/**
	 * 登录成功
	 * 
	 * @param user
	 * @param map
	 * @param result
	 * @return
	 */
	@RequestMapping(value = "/success")
	public String success(Model model) {
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		User user = userService.getAccount(userDetails.getUsername());
		model.addAttribute("user", user);
		return "/indexManage/index";

	}

	@RequestMapping("/index")
	public String index() {
		return "/indexManage/index";
	}

	@RequestMapping("/getUser")
	@ResponseBody
	public User getUser(HttpSession session) {
		return (User) session.getAttribute("user");
	}

	/**
	 * 菜单JSON
	 * 
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/getMenu")
	@ResponseBody
	public Map<String, Object> getMenu(HttpSession session) {

		Map<String, Object> map = new HashMap<String, Object>();

		List<Privilege> list = privilegeService.getTopList();

		User user = (User) session.getAttribute("user");
		String code = user.getRoles().get(0).getRoleCode();
		List<Privilege> privilegeList = new ArrayList<Privilege>();

		if (code.equals("COMMON")) {
			privilegeList.add(list.get(0));
		} else if (code.equals("MANAGER")) {
			privilegeList.add(list.get(0));
			privilegeList.add(list.get(1));
		} else if (code.equals("ADMIN")) {
			privilegeList.add(list.get(0));
			privilegeList.add(list.get(2));
		}

		map.put("user", user);
		map.put("privilegeList", privilegeList);
		return map;

	}

}
