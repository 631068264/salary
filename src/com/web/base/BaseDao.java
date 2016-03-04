package com.web.base;

import java.util.List;

import com.web.page.HqlHelper;
import com.web.page.Pager;

public interface BaseDao<T> {

	void save(T entity);

	void delete(Long id);

	void update(T entity);

	T getById(Long id);

	List<T> getByIds(Long[] ids);

	List<T> findAll();

	/**
	 * 按照HQL语句批处理实体
	 */
	void batchEntityByHQL(String hql, Object... objects);

	List<T> batchResultsByHQL(String hql, Object... objects);

	T batchUniqueResultByHQL(String hql, Object... objects);

	void deleteAll(Long[] ids);

	// 分页
	Pager getPager(Integer currentPage, HqlHelper hqlHelper);
}
