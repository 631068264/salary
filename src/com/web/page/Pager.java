package com.web.page;

import java.util.List;

/**
 * 通用分页 前4 后5
 * 
 * @author Administrator
 * 
 */
@SuppressWarnings("rawtypes")
public class Pager {
	// 页面传参或者配置参数
	private int currentPage;// 当前页
	private int pageSize;// 每页记录数
	// 查询数据库
	private List recordList; // 当前页数据列表
	private int recordCount;// 记录总数
	// 计算得到
	private int pageCount;// 总页数
	private int beginPageIndex;// 页码 列表开始索引 闭区间
	private int endPageIndex;// 页码 列表结束索引

	/**
	 * 知道前四个 其他计算可得
	 * 
	 * @param currentPage
	 * @param pageSize
	 * @param recordList
	 * @param recordCount
	 */
	public Pager(int currentPage, int pageSize, List recordList, int recordCount) {

		this.currentPage = currentPage;
		this.pageSize = pageSize;
		this.recordList = recordList;
		this.recordCount = recordCount;
		// 总页数===============================
		pageCount = (recordCount + pageSize - 1) / pageSize;

		// 每页显示的总页码
		int size = Configuration.getFrontPage() + 1 + Configuration.getAfterPage();
		//
		if (pageCount < size) {// 页码总数<10 全显
			beginPageIndex = 1;
			endPageIndex = pageCount;
		} else {// 大于10 附近共10个前4后5
			beginPageIndex = currentPage - Configuration.getFrontPage();
			endPageIndex = currentPage + Configuration.getAfterPage();
			if (beginPageIndex < 1) {// 前4不足 显示前10页
				beginPageIndex = 1;
				endPageIndex = size;
			} else if (endPageIndex > pageCount) {// 后不足 5 个 显示后10页
				beginPageIndex = pageCount - size + 1;
				endPageIndex = pageCount;
			}
		}

	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public List getRecordList() {
		return recordList;
	}

	public void setRecordList(List recordList) {
		this.recordList = recordList;
	}

	public int getRecordCount() {
		return recordCount;
	}

	public void setRecordCount(int recordCount) {
		this.recordCount = recordCount;
	}

	public int getPageCount() {
		return pageCount;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

	public int getBeginPageIndex() {
		return beginPageIndex;
	}

	public void setBeginPageIndex(int beginPageIndex) {
		this.beginPageIndex = beginPageIndex;
	}

	public int getEndPageIndex() {
		return endPageIndex;
	}

	public void setEndPageIndex(int endPageIndex) {
		this.endPageIndex = endPageIndex;
	}

}
