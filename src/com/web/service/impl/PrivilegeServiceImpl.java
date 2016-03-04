package com.web.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.web.base.BaseDaoImpl;
import com.web.entity.Privilege;
import com.web.service.PrivilegeService;

@Service
public class PrivilegeServiceImpl extends BaseDaoImpl<Privilege> implements PrivilegeService {

	@Override
	public List<Privilege> getTopList() {
		String hql = "from Privilege p where p.parent is null";

		return batchResultsByHQL(hql);
	}
}
