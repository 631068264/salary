package com.web.service;

import java.util.List;

import com.web.base.BaseDao;
import com.web.entity.Privilege;

public interface PrivilegeService extends BaseDao<Privilege> {
	/**
	 * 获取顶级菜单
	 * 
	 * @return
	 */
	List<Privilege> getTopList();

}
