package com.yitong.yoga.http;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

/**
 * 扩展请求参数类，配合最新后台框架使用
 * {"header":{"_t":"时间戳","service":"接口服务码"},"payload":{"key1":"value1","key2":"value2"}}
 * @author tongxu_li
 * Copyright (c) 2016 Shanghai P&C Information Technology Co., Ltd.
 */
public class YTExtendRequestParams implements YTRequestParams {
	
	private Map<String, Object> headerMap;
	private Map<String, Object> payloadMap;
    
    public YTExtendRequestParams(String strServiceCode) {
    	this.headerMap = new HashMap<String, Object>();
    	this.payloadMap = new HashMap<String, Object>();
    	setHeaderParams(strServiceCode);
    }
    
    private void setHeaderParams(String strServiceCode) {
		headerMap.put("_t", System.currentTimeMillis());
		headerMap.put("service", strServiceCode);    	
    }
    
    @Override
    public void put(String key, Object value) {
    	payloadMap.put(key, value);
    }

	@Override
	public String getContentType() {
		return "application/json";
	}

	@Override
	public String getParamsString() {
    	String paramsString = "";
    	
    	Map<String, Object> paramsMap = new HashMap<String, Object>();
    	paramsMap.put("header", headerMap);
    	paramsMap.put("payload", payloadMap);
    	
		StringBuilder sb = new StringBuilder();
    	Gson gson = new Gson();
    	sb.append(gson.toJson(paramsMap));
    	paramsString = sb.toString();
		
        return paramsString;
	}
}
