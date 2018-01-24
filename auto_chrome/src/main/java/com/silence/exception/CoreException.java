package com.silence.exception;


/**
 * 核心异常
 *   
 * CoreException  
 *   
 * silence  
 * silence  
 * 2016年1月25日 上午8:19:39  
 *   
 * @version 1.0.0  
 *
 */
 public class CoreException extends RuntimeException {

	/**  
	 * serialVersionUID
	 *  
	 * @since 1.0.0  
	 */  
	private static final long serialVersionUID = 1L;
	
	public CoreException(String errMsg){
		super(errMsg);
	}
	
	
}
