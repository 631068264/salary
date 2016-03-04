package com.web.service;

import java.util.List;

import com.web.base.BaseDao;
import com.web.entity.Salary;
import com.web.entity.User;

public interface UserService extends BaseDao<User> {

	/**
	 * 获得本用户工资表
	 * 
	 * @param uid
	 * @return
	 */
	Salary getSalaryList(Long uid);

	/**
	 * 通过员工编号登录检查
	 * 
	 * @param userID
	 * @return
	 */
	User getAccount(String userID);

	/**
	 * 除部门经理
	 * 
	 * @return
	 */
	List<User> noFindAll();

	/**
	 * 获取部门所有人
	 * 
	 * @param did
	 * @return
	 */
	List<User> getSalary(Long did);

	/**
	 * 获取本部门工资(不包括个人)
	 * 
	 * @param user
	 * @return
	 * @throws Exception
	 */
	List<User> getDepartmentSalary(User user);

}
