package com.web.service.impl;

import org.springframework.stereotype.Service;

import com.web.base.BaseDaoImpl;
import com.web.entity.Salary;
import com.web.service.SalaryService;

@Service("salaryService")
public class SalaryServiceImpl extends BaseDaoImpl<Salary> implements SalaryService {

	@Override
	public Double avgSalary(Long did) {

		String hql = "SELECT AVG(s.basePay)+AVG(s.welfare)+AVG(s.bonus)-AVG(s.deduct)  FROM Salary s ,User u,Department d "
				+ "where d.id = u.department.id and u.salary.id = s.id and d.id = ? ";

		Double result = (Double) getEm().createQuery(hql).setParameter(1, did).getSingleResult();

		return result;
	}
}
