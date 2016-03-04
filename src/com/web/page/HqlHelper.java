package com.web.page;

import java.util.ArrayList;
import java.util.List;

import org.springframework.ui.Model;

import com.web.base.BaseDao;

/**
 * 拼接生成HQL的工具类 使用方法链
 * 
 * @author Administrator
 * 
 */
public class HqlHelper {
	private String fromClause; // From子句，必须
	private String whereClause = ""; // Where子句，可选
	private String orderByClause = ""; // OrderBy子句，可选

	private List<Object> parameters = new ArrayList<Object>(); // 参数列表

	/**
	 * 生成From子句，默认的别名为'o'
	 * 
	 * @param clazz
	 *            填入类名.class
	 */
	public HqlHelper(Class clazz) {
		// From子句，必须要有使用构造函数限定
		this.fromClause = "FROM " + clazz.getSimpleName() + " o";
	}

	/**
	 * 使用指定的别名,生成From子句
	 * 
	 * @param clazz
	 *            类名.class
	 * @param alias
	 *            别名
	 */
	public HqlHelper(Class clazz, String alias) {
		this.fromClause = "FROM " + clazz.getSimpleName() + " " + alias;
	}

	// 使用方法链 返回类对象
	/**
	 * 无条件Where条件子句 使用AND连接
	 * 
	 * @param condition
	 * @param params
	 * @return
	 */
	public HqlHelper addCondition(String condition, Object... params) {
		// AND拼接
		if (whereClause.length() == 0) {
			whereClause = " WHERE " + condition;
		} else {
			whereClause += " AND " + condition;
		}
		// 保存参数
		if (params != null && params.length > 0) {
			for (Object obj : params) {
				parameters.add(obj);
			}
		}
		return this;
	}

	/**
	 * 无条件OrderBy子句(order by 属性 asc/desc ,属性asc/desc ... )
	 * 
	 * @param propertyName
	 *            属性名
	 * @param isAsc
	 *            true表示升序，false表示降序
	 * 
	 */
	public HqlHelper addOrder(String propertyName, boolean isAsc) {
		if (orderByClause.length() == 0) {
			orderByClause = " ORDER BY " + propertyName + (isAsc ? " ASC" : " DESC");
		} else {
			orderByClause += ", " + propertyName + (isAsc ? " ASC" : " DESC");
		}
		return this;
	}

	// 筛选条件append
	/**
	 * 特殊条件拼接: if(append){拼接Where}
	 * 
	 * @param append
	 *            拼接条件
	 * @param condition
	 * @param params
	 */
	public HqlHelper addCondition(boolean append, String condition, Object... params) {
		if (append) {
			addCondition(condition, params);
		}
		return this;
	}

	/**
	 * if(append){拼接OrderBy}
	 * 
	 * @param append
	 *            拼接条件
	 * @param propertyName
	 *            属性名
	 * @param isAsc
	 *            true表示升序，false表示降序
	 */
	public HqlHelper addOrder(boolean append, String propertyName, boolean isAsc) {
		if (append) {
			addOrder(propertyName, isAsc);
		}
		return this;
	}

	// =================================================================
	/**
	 * 获取生成的查询总记录数的HQL语句（没有OrderBy子句）
	 * 
	 * @return
	 */
	public String getCountHQL() {
		return "SELECT COUNT(*) " + fromClause + whereClause;
	}

	/**
	 * 获取生成的查询数据列表的HQL语句
	 * 
	 * @return
	 */
	public String getHQL() {
		return fromClause + whereClause + orderByClause;
	}

	/**
	 * 获取参数列表 与where子句的 ? 对应
	 * 
	 * @return
	 */
	public List<Object> getParameters() {
		return parameters;
	}

	public void setParameters(List<Object> parameters) {
		this.parameters = parameters;
	}

	// ========================================================================

	public HqlHelper pushToModel(Integer currentPage, BaseDao<?> service, Model model) {
		if (currentPage == null) {
			currentPage = 1;// 默认第一页
		}
		Pager pager = service.getPager(currentPage, this);
		model.addAttribute("pager", pager);
		return this;
	}

	/**
	 * 返回分页信息用于JSON
	 * 
	 * @param currentPage
	 * @param service
	 * @return
	 */
	public Pager pushToJSON(Integer currentPage, BaseDao<?> service) {
		if (currentPage == null) {
			currentPage = 1;
		}

		return service.getPager(currentPage, this);

	}

}
