package com.web.schedule;

import org.springframework.scheduling.annotation.Scheduled;

import com.web.base.BaseAction;

/**
 * 使用石英调度
 * 
 * @author Administrator
 * 
 */
public class ClearDeduct extends BaseAction {
	// cron表达式 s m h d m w y (d与w互斥 ) 1-8 1,8 *任意 ?不设置 y可以不写

	@Scheduled(cron = "0 0 0 10 * ?")
	public void Clear() {
		String hql = "update Salary  set deduct = 0 ";
		salaryService.batchEntityByHQL(hql);

	}
}
