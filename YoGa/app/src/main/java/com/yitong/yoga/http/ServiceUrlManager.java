package com.yitong.yoga.http;

import android.content.Context;

import com.yitong.yoga.utils.Logs;

import java.io.File;
import java.util.Locale;

/**
 * 服务地址管理类，程序启动时进行设置
 * author tongxu_li
 * Copyright (c) 2016 Shanghai P&C Information Technology Co., Ltd.
 */
public class ServiceUrlManager {

	// 接口服务器请求基地址
	private static String SERVICE_BASE_URL = "http://10.10.10.71:8088/YogaApp";

	// 网络资源服务器请求基地址
	private static String RESOURCE_BASE_URL = "";
	
	public static String getServiceBaseUrl() {
		return SERVICE_BASE_URL;
	}
	
	public static void setServiceBaseUrl(String url) {
		String baseUrl = url;
		if (baseUrl.endsWith("/")) {
			baseUrl = baseUrl.substring(0, baseUrl.length()-1);
		}
		SERVICE_BASE_URL = baseUrl;
	}
	
	public static String getResourceBaseUrl() {
		return RESOURCE_BASE_URL;
	}
	
	public static void setResourceBaseUrl(String url) {
		String baseUrl = url;
		if (baseUrl.endsWith("/")) {
			baseUrl = baseUrl.substring(0, baseUrl.length()-1);
		}
		RESOURCE_BASE_URL = baseUrl;
	}

	/**
	 * 获取接口请求绝对路径
	 * param relativeUrl
	 * return
	 */
	public static String getServiceAbsUrl(String relativeUrl) {

		String absoluteUrl;
		if (relativeUrl.startsWith("/")) {
			absoluteUrl = (SERVICE_BASE_URL + relativeUrl);
		} else {
			absoluteUrl = (SERVICE_BASE_URL + File.separator + relativeUrl);
		}
		return absoluteUrl;
	}


	/**
	 * 获取接口请求绝对路径
	 * param relativeUrl
	 * return
	 */
	public static String getServiceAbsUrl(String relativeUrl, Context context) {

		String absoluteUrl;
		boolean tag;
		Locale locale = context.getResources().getConfiguration().locale;
		String country= locale.getCountry();
		Logs.e("TAG",country);
		tag=country.equals("CN");
		if (relativeUrl.startsWith("/")) {
			absoluteUrl = (SERVICE_BASE_URL + relativeUrl);

//			if (tag) {
////				absoluteUrl = (SERVICE_BASE_URL + relativeUrl+"?local=1");
//				absoluteUrl = (SERVICE_BASE_URL + relativeUrl);
//			} else {
////				absoluteUrl = (SERVICE_BASE_URL + relativeUrl+"?local=0");
//				absoluteUrl = (SERVICE_BASE_URL + relativeUrl);
//			}

		} else {

			absoluteUrl = (SERVICE_BASE_URL + File.separator + relativeUrl);
//			if (tag) {
//				absoluteUrl = (SERVICE_BASE_URL + File.separator + relativeUrl+"?local=1");
//			} else {
//				absoluteUrl = (SERVICE_BASE_URL + File.separator + relativeUrl+"?local=0");
//			}

		}
		return absoluteUrl;
	}

	/**
	 * 获取资源请求绝对路径
	 * param relativeUrl
	 * return
	 */
	public static String getResourceAbsUrl(String relativeUrl) {
		String absoluteUrl ;
		if (relativeUrl.startsWith("/")) {
			absoluteUrl = (RESOURCE_BASE_URL + relativeUrl);
		} else {
			absoluteUrl = (RESOURCE_BASE_URL + File.separator + relativeUrl);
		}
		return absoluteUrl;
	}
}
