package com.github.util;

import com.github.pagehelper.Page;

import java.util.List;

public final class DataTableJson<T> {

	private Integer code;
	private Integer count;
	private String msg;
	private List<T> data;

	public DataTableJson() {
	}

	public DataTableJson(List<T> data) {
		this.code = 0;
		this.msg = "请求正确响应";
		this.data = data;

		Page<T> page = (Page<T>) data;
		this.count = new Long(page.getTotal()).intValue();
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}
}
