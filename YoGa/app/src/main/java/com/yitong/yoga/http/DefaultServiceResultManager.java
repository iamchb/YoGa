package com.yitong.yoga.http;



/**
 * 标准返回结果管理
 * @author tongxu_li
 * Copyright (c) 2014 Shanghai P&C Information Technology Co., Ltd.
 */
public class DefaultServiceResultManager implements APPResponseHandler.ServiceResultManager {
	    	
	// 业务响应状态
	private static final String KEY_STATUS = "STATUS";
	
	// 业务响应信息
	private static final String KEY_MESSAGE = "MSG";
	
	// 业务数据
//	private static final String KEY_RESULT = "RESULT";	
	private static final String KEY_RESULT = "";
	
	//成功标识
	private static final String STATUS_SUCCESS = "1";
	
	@Override
	public String getStatusKey() {
		return KEY_STATUS;
	}
	@Override
	public String getMessgaeKey() {
		return KEY_MESSAGE;
	}
	@Override
	public String getResultKey() {
		return KEY_RESULT;
	}
	
	@Override
	public String getSuccessStatus() {
		return STATUS_SUCCESS;
	}
	
}
