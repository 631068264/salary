package com.web.base;

import javax.annotation.Resource;

import com.web.service.DepartmentService;
import com.web.service.PrivilegeService;
import com.web.service.RoleService;
import com.web.service.SalaryService;
import com.web.service.UserService;

public class BaseAction {
	@Resource
	protected UserService userService;
	@Resource
	protected SalaryService salaryService;
	@Resource
	protected DepartmentService departmentService;
	@Resource
	protected RoleService roleService;
	@Resource
	protected PrivilegeService privilegeService;
}
