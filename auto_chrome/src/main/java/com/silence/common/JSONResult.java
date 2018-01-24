package com.silence.common;

public class JSONResult {
	
	/*消息*/
	private String msg;
	
	/*结果集*/
	private Object result;
	
	/*是否成功*/
	private boolean success=true;

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	
}
