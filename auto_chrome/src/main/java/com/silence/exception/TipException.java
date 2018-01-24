package com.silence.exception;

/**
 * 提示异常
 *   
 * TipException  
 *   
 * silence  
 * silence  
 * 2016年3月5日 上午10:38:04  
 *   
 * @version 1.0.0  
 *
 */
public class TipException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;

	public TipException(String msg){
		super(msg);
	}
}
