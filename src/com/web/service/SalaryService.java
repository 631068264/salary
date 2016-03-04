package com.web.service;

import com.web.base.BaseDao;
import com.web.entity.Salary;

public interface SalaryService extends BaseDao<Salary> {

	/**
	 * 计算部门平均工资
	 * 
	 * @param did
	 * @return
	 * @throws Exception
	 */
	Double avgSalary(Long did);

}
