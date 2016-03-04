package com.web.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.web.base.BaseAction;
import com.web.entity.Department;
import com.web.entity.Role;
import com.web.entity.Salary;
import com.web.entity.User;
import com.web.entity.viewhelp.UDRList;
import com.web.page.HqlHelper;
import com.web.page.Pager;
import com.web.utils.Md5Utils;

@Controller
@RequestMapping("/adminManage")
@PreAuthorize("hasRole('ADMIN')")
public class AdminManage extends BaseAction {
	/**
	 * 获取用户列表JSON
	 * 
	 * @param currentPage
	 * @return
	 */
	@RequestMapping("/getUserList")
	@ResponseBody
	public Pager list(Integer currentPage) {
		Pager pager = new HqlHelper(User.class, "u").addCondition("u.id != ?", 1L).pushToJSON(
				currentPage, userService);

		List<UDRList> udrLists = new ArrayList<UDRList>();

		@SuppressWarnings("unchecked")
		List<User> users = pager.getRecordList();

		for (User user : users) {
			if (user != null) {
				UDRList udrList = new UDRList();
				udrList.setUser(user);
				udrList.setDepartment(user.getDepartment());
				udrList.setRole(user.getRoles().get(0));
				udrLists.add(udrList);
			}
		}

		pager.setRecordList(udrLists);

		return pager;
	}

	@RequestMapping("/list")
	public String list() {

		return "adminManage/list";
	}

	/**
	 * 新增页面JSON
	 * 
	 * @return
	 */
	@RequestMapping("/addInfo")
	@ResponseBody
	public Map<String, Object> addInfo() {

		Map<String, Object> map = new HashMap<String, Object>();
		List<Department> departmentList = departmentService.findAll();
		List<Role> roleList = roleService.findAll();

		map.put("departments", departmentList);
		map.put("roles", roleList);
		return map;
	}

	@RequestMapping(value = "/addUI")
	public String addUI() {

		return "adminManage/addUI";
	}

	@RequestMapping(value = "/add")
	public String add(Long departmentId, Long[] roleIds, User user, Salary salary) {
		System.out.println("add:" + departmentId);
		user.setPassword(Md5Utils.md5(user));

		user.setDepartment(departmentService.getById(departmentId));

		List<Role> roles = new ArrayList<Role>();
		roles.add(roleService.getById(roleIds[0]));
		user.setRoles(roles);

		salary.setDeduct(0.0);
		user.setSalary(salary);

		userService.save(user);

		return "redirect:/adminManage/list";
	}

	/**
	 * 编辑页面JSON
	 * 
	 * @param uid
	 * @return
	 */
	@RequestMapping("/{uid}/editInfo")
	@ResponseBody
	public Map<String, Object> editInfo(@PathVariable Long uid) {
		Map<String, Object> map = new HashMap<String, Object>();
		// 表单准备
		List<Department> departmentList = departmentService.findAll();
		map.put("departments", departmentList);
		List<Role> roleList = roleService.findAll();
		map.put("roles", roleList);

		// 数据回显
		User user = userService.getById(uid);

		Long departmentId = null;
		Long[] roleIds = null;
		Salary salary = null;

		map.put("user", user);

		try {

			departmentId = user.getDepartment().getId();

			roleIds = new Long[user.getRoles().size()];
			int index = 0;
			for (Role role : user.getRoles()) {
				roleIds[index++] = role.getId();
			}

			salary = salaryService.getById(user.getSalary().getId());

		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			map.put("departmentId", departmentId);
			map.put("roleIds", Arrays.asList(roleIds));
			map.put("salary", salary);

		}
		return map;
	}

	@RequestMapping(value = "/{uid}/editUI")
	public String editUI(@PathVariable Long uid) {

		return "adminManage/editUI";
	}

	@RequestMapping(value = "/edit")
	public String edit(Long sid, Long uid, Long departmentId, Long[] roleIds, Salary salary,
			User user) {

		System.out.println("edit:" + departmentId);

		User u = userService.getById(uid);
		u.setUserID(user.getUserID());
		u.setUserName(user.getUserName());

		Salary s = salaryService.getById(sid);
		s.setBasePay(salary.getBasePay());
		s.setWelfare(salary.getWelfare());
		s.setBonus(salary.getBonus());

		u.setSalary(s);

		u.setDepartment(departmentService.getById(departmentId));

		List<Role> roles = new ArrayList<Role>();

		roles.add(roleService.getById(roleIds[0]));
		u.setRoles(roles);

		userService.update(u);

		return "redirect:/adminManage/list";
	}

	// ======================

	@RequestMapping(value = "{uid}/del")
	public String del(@PathVariable Long uid) {
		userService.delete(uid);
		return "redirect:/adminManage/list";
	}

	/** 批量删除 */
	@RequestMapping(value = "/delAll")
	public String delAll(Long[] uids) {
		userService.deleteAll(uids);
		return "redirect:/adminManage/list";
	}

}
