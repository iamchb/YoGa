package com.yitong.yoga.http;

/**
 * 网络请求响应错误对照码（客户端内部定义）
 * @author tongxu_li
 * Copyright (c) 2015 Shanghai P&C Information Technology Co., Ltd.
 */
public class APPResponseError {
	public static final String LOG_TAG = "APPResponseError";
	
	// 网络连接错误
	public static final int ERROR_CODE_NET = -900;
	// 无可信证书
	public static final int ERROR_CODE_NO_PEER_CER = -901;

	// Json字符串转对象错误
	public static final int ERROR_CODE_FROM_JSON_TO_OBJECT = -800;
	// Json字符串解析错误
	public static final int ERROR_CODE_JSON_PARSE = -801;
	// 结果字段key不匹配
	public static final int ERROR_CODE_RESULT_KEY_NOT_CORRECT = -803;
	// onSuccess回调处理异常
	public static final int ERROR_CODE_ONSUCCESS_EXCEPTION = -804;
	// 解密响应数据失败
	public static final int ERROR_CODE_DECRYPT_DATA = -805;
	
	// 业务操作错误
	public static final int ERROR_CODE_BUSINESS_OPER = -700;
	
	/**
	 * 获取客户端自定义错误信息，不包括业务操作错误信息
	 * @param errorCode
	 * @return
	 */
	public static String getCustomErrorMsg(int errorCode) {
		String errorMsg = "";

		if (errorCode == ERROR_CODE_NET) {
			errorMsg = "网络连接失败，请稍后重试";
		} else if (errorCode == ERROR_CODE_NO_PEER_CER) {
			errorMsg = "缺少可信证书";
		} else if (errorCode == ERROR_CODE_FROM_JSON_TO_OBJECT) {
			errorMsg = "对象转换失败";
		} else if (errorCode == ERROR_CODE_JSON_PARSE) {
			errorMsg = "对象解析失败";
		} else if (errorCode == ERROR_CODE_RESULT_KEY_NOT_CORRECT) {
			errorMsg = "结果字段key不匹配";
		} else if (errorCode == ERROR_CODE_ONSUCCESS_EXCEPTION) {
			errorMsg = "onSuccess回调处理异常";
		} else if (errorCode == ERROR_CODE_DECRYPT_DATA) {
			errorMsg = "解密响应数据失败";
		}  else {
			errorMsg = "未知错误";
		}
		return errorMsg;
	}
}
