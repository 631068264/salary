package com.web.base;

import java.lang.reflect.ParameterizedType;
import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.web.page.Configuration;
import com.web.page.HqlHelper;
import com.web.page.Pager;

@SuppressWarnings("unchecked")
@Transactional
public class BaseDaoImpl<T> implements BaseDao<T> {

	@PersistenceContext
	private EntityManager em;
	private Class<T> clazz;// 类对象

	public EntityManager getEm() {
		return em;
	}

	public BaseDaoImpl() {
		// 通过发射获取T的真实类型
		// 通过反射获取当前类表示的实体的直接父类的Type
		ParameterizedType pt = (ParameterizedType) this.getClass().getGenericSuperclass();
		// 返回参数数组
		this.clazz = (Class<T>) pt.getActualTypeArguments()[0];

	}

	@Override
	public void save(T entity) {
		em.persist(entity);
	}

	@Override
	public void delete(Long id) {
		em.remove(getById(id));
	}

	@Override
	public void update(T entity) {
		em.merge(entity);
	}

	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
	public T getById(Long id) {
		if (id == null) {
			return null;
		}

		return em.find(clazz, id);
	}

	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
	public List<T> getByIds(Long[] ids) {
		if (ids == null || ids.length == 0) {
			return Collections.EMPTY_LIST;
		}

		return em.createQuery("from " + clazz.getSimpleName() + " where id in(:ids)")
				.setParameter("ids", ids).getResultList();
	}

	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
	public List<T> findAll() {
		return em.createQuery("from " + clazz.getSimpleName()).getResultList();
	}

	@Override
	/**
	 * 批处理
	 */
	public void batchEntityByHQL(String hql, Object... objects) {
		Query query = em.createQuery(hql);
		if (objects != null && objects.length > 0) {
			for (int i = 0; i < objects.length; i++) {
				query.setParameter(i + 1, objects[i]);
			}
		}
		query.executeUpdate();
	}

	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
	public List<T> batchResultsByHQL(String hql, Object... objects) {
		Query query = em.createQuery(hql);
		if (objects != null && objects.length > 0) {
			for (int i = 0; i < objects.length; i++) {
				query.setParameter(i + 1, objects[i]);
			}
		}

		try {
			return query.getResultList();
		} catch (Exception e) {

			return null;
		}
	}

	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
	public T batchUniqueResultByHQL(String hql, Object... objects) {
		Query query = em.createQuery(hql);
		if (objects != null && objects.length > 0) {
			for (int i = 0; i < objects.length; i++) {
				query.setParameter(i + 1, objects[i]);
			}
		}
		try {
			// 查不到数据会报错 而不会返回 null
			return (T) query.getSingleResult();
		} catch (Exception e) {
			return null;
		}

	}

	@Override
	public void deleteAll(Long[] ids) {
		if (ids == null || ids.length == 0) {
			return;
		}
		String hql = "DELETE FROM " + clazz.getSimpleName() + "WHERE id IN (:ids)";
		em.createQuery(hql).setParameter("ids", ids).executeUpdate();
	}

	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
	public Pager getPager(Integer currentPage, HqlHelper hqlHelper) {
		int pageSize = Configuration.getPageSize();
		List<Object> parameters = hqlHelper.getParameters();

		// 查询数据
		Query listQuery = em.createQuery(hqlHelper.getHQL());
		if (parameters != null && parameters.size() > 0) {
			for (int i = 0; i < parameters.size(); i++) {
				listQuery.setParameter(i + 1, parameters.get(i));
			}
		}
		// 分页查询
		List list = listQuery.setFirstResult((currentPage - 1) * pageSize).setMaxResults(pageSize)
				.getResultList();
		// 总记录数
		Query countQuery = em.createQuery(hqlHelper.getCountHQL());
		if (parameters != null && parameters.size() > 0) {
			for (int i = 0; i < parameters.size(); i++) {
				countQuery.setParameter(i + 1, parameters.get(i));
			}
		}

		Long count = (Long) countQuery.getSingleResult();
		return new Pager(currentPage, pageSize, list, count.intValue());
	}

}
