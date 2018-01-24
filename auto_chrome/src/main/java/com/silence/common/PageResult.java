package com.silence.common;

import java.util.List;

/**
 * 分页
 *   
 * PageResult  
 *   
 * silence  
 * silence  
 * 2016年2月1日 上午7:57:36  
 *   
 * @version 1.0.0  
 *
 */
public class PageResult<T> {
	
	/*当前页*/
	private Integer pageNum;

	/*总记录*/
	private Integer totalCount;
	
	/*总页数*/
	private long totalPages;
	
	/*每页记录*/
	private Integer pageSize;
	
	/*数据*/
	private List<T> result;

	public Integer getPageNum() {
		return pageNum;
	}

	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}

	public Integer getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}


	public List<T> getResult() {
		return result;
	}

	public void setResult(List<T> result) {
		this.result = result;
	}

	public PageResult(Integer pageNum, Integer pageSize) {
		super();
		this.pageNum = pageNum;
		this.pageSize = pageSize;
	}
	
	

	
	public long getTotalPages() {
		if (totalCount < 0) {
			return -1;
		}

		long count = totalCount / pageSize;
		if (totalCount % pageSize > 0) {
			count++;
		}
		return count;
	}
	
	

	public PageResult(){}
}
