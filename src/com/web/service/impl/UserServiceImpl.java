package com.web.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.web.base.BaseDaoImpl;
import com.web.entity.Salary;
import com.web.entity.User;
import com.web.service.SalaryService;
import com.web.service.UserService;

@Service
public class UserServiceImpl extends BaseDaoImpl<User> implements UserService {

	@Resource
	private SalaryService salaryService;

	@Override
	public Salary getSalaryList(Long uid) {
		String hql = "FROM Salary s where s.user.id = ?";
		return salaryService.batchUniqueResultByHQL(hql, uid);
	}

	@Override
	public User getAccount(String userID) {
		String hql = "FROM User u WHERE u.userID = ? ";
		return batchUniqueResultByHQL(hql, userID);
	}

	@Override
	public List<User> noFindAll() {
		String hql = "FROM User u WHERE u.id != ?";
		return batchResultsByHQL(hql, 1L);
	}

	@Override
	public List<User> getSalary(Long did) {
		String hql = "FROM User u WHERE u.department.id = ?";
		return batchResultsByHQL(hql, did);
	}

	@Override
	public List<User> getDepartmentSalary(User user) {
		String hql = "FROM User u WHERE u.department.id = ? AND u.id != ?";

		return batchResultsByHQL(hql, user.getDepartment().getId(), user.getId());
	}
}
