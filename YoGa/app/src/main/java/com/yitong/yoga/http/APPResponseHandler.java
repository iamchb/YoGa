package com.yitong.yoga.http;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.yitong.http.HttpResponseException;
import com.yitong.http.TextHttpResponseHandler;
import com.yitong.yoga.utils.Logs;
import com.yitong.yoga.utils.StringTools;

import javax.net.ssl.SSLPeerUnverifiedException;

import okhttp3.Headers;

/**
 * 处理网络请求回调，可根据指定的对象泛型，进行反射填充对象。
 * @Description
 * @Author lewis(lgs@yitong.com.cn) 2014-1-17 下午1:44:43
 * @Class APPResponseHandler
 * Copyright (c) 2014 Shanghai P&C Information Technology Co.,Ltd. All rights reserved.
 */
public abstract class APPResponseHandler<T> extends TextHttpResponseHandler {
	private static final String LOG_TAG = "APPResponseHandler";
	
	// 指定结果集泛型
	private Class<T> classOfT;
	// 设置结果集是否为空
	private boolean isResultNull = true;		
	// 返回结果管理器
	private static ServiceResultManager mServiceResultManager = new DefaultServiceResultManager();
	// 如果返回接口是密文，设置解密代理
	private static APPResponseDecryptDelegate gDecryptDelegate = null;
	// 响应数据解密秘钥
	private String key = null;
	// 响应数据，如果响应数据是加密的，则是解密后的数据
	private String responseString;

	/**
	 * 无参构造函数
	 */
	public APPResponseHandler() {
		super();
		isResultNull = true;
	}
	
	/**
	 * 创建一个默认编码为utf-8对象,该对象的回调结果集为空
	 */
	public APPResponseHandler(String k) {
		super();
		this.key = k;
		isResultNull = true;
	}

	/**
	 * 创建一个默认编码为utf-8对象,该对象的回调结果集为指定泛型
	 */
	public APPResponseHandler(Class<T> classOfT) {
		this(classOfT, DEFAULT_CHARSET, null);
	}
	
	/**
	 * 创建一个默认编码为utf-8对象,该对象的回调结果集为指定泛型
	 */
	public APPResponseHandler(Class<T> classOfT, String k) {
		this(classOfT, DEFAULT_CHARSET, k);
	}

	/**
	 * 创建一个默认编码为指定编码的对象,该对象的回调结果集为指定泛型
	 */
	public APPResponseHandler(Class<T> classOfT, String encoding, String k) {
		super(encoding);
		this.key = k;
		this.classOfT = classOfT;
		isResultNull = false;
	}
	
	/**
	 * 请求成功回调
	 *
	 * @param result
	 */
	public abstract void onSuccess(T result);
	
	/**
	 * 请求失败回调
	 *
	 * @param errorCode
	 * @param errorMsg
	 */
	public abstract void onFailure(String errorCode, String errorMsg);
	
	@Override
	public void onSuccess(int statusCode, Headers headers, String responseString) {
		Logs.d(LOG_TAG, "接口返回"+responseString);
		
		// 返回结果解密
		if (null != key && gDecryptDelegate != null) {
			responseString = gDecryptDelegate.getDecryptString(responseString, key);
			Logs.d(LOG_TAG, "接口解密返回:"+responseString);
		}
		
		this.responseString = responseString;
		
		String status = "";
		String message = "";
		
		try {
			Gson gson = new Gson();
			JsonObject jsonObject = new JsonParser().parse(responseString).getAsJsonObject();
			
			// 获取业务响应状态
			String keyStatus = mServiceResultManager.getStatusKey();
			if (jsonObject.has(keyStatus)) {
				JsonElement errorCodeJsonElement = jsonObject.get(keyStatus);
				status = errorCodeJsonElement.getAsString();			
			}
			
			// 获取业务响应信息
			String keyMessage = mServiceResultManager.getMessgaeKey();
			if (jsonObject.has(keyMessage)) {
				JsonElement errorMsgJsonElement = jsonObject.get(keyMessage);
				if (errorMsgJsonElement.isJsonNull()) {
					message = "";
				} else {
					message = errorMsgJsonElement.getAsString();	
				}
			}
			
			// 获取业务数据
			if (isResultNull) {
				if (status.equals(mServiceResultManager.getSuccessStatus())) {
					sendSuccessWithResult(null);
				} else {
					onFailure(status, message);
				}			
			} else {
				String keyResult = mServiceResultManager.getResultKey();
				if (status.equals(mServiceResultManager.getSuccessStatus())) {
					//key常量不为空
					if (!StringTools.isEmpty(keyResult)) {
						if(jsonObject.has(keyResult)) { //json结果中包含结果key
							JsonElement resultJsonElement = jsonObject.get(keyResult);
							try {
								T result = gson.fromJson(resultJsonElement, classOfT);
								sendSuccessWithResult(result);
							} catch (JsonSyntaxException e) {
								sendFailureWithCode(APPResponseError.ERROR_CODE_FROM_JSON_TO_OBJECT);
								return;
							}	
						} else { //json结果中包含结果不匹配
							sendFailureWithCode(APPResponseError.ERROR_CODE_RESULT_KEY_NOT_CORRECT);
						}
					} else {
						try {
							T result = gson.fromJson(jsonObject, classOfT);
							sendSuccessWithResult(result);
						} catch (JsonSyntaxException e) {
							sendFailureWithCode(APPResponseError.ERROR_CODE_FROM_JSON_TO_OBJECT);
							return;
						}
					}	
				} else {
					onFailure(status, message);
				}
			}
		} catch (OnSuccessException e) {
			throw new IllegalArgumentException(e);
		}  catch (JsonSyntaxException e) {
			sendFailureWithCode(APPResponseError.ERROR_CODE_JSON_PARSE);
		} catch (IllegalStateException e) {
			sendFailureWithCode(APPResponseError.ERROR_CODE_JSON_PARSE);
		} catch (Exception e) {
			sendFailureWithCode(APPResponseError.ERROR_CODE_JSON_PARSE);
		}
	}

	@Override
	public void onFailure(int statusCode, Headers headers, String responseString, Throwable throwable) {
		throwable.printStackTrace();
		if (throwable instanceof HttpResponseException) {
			HttpResponseException e = (HttpResponseException)throwable;
			onFailure(String.valueOf(e.getStatusCode()), e.getMessage());
		} else {
			if (throwable instanceof SSLPeerUnverifiedException) {
				sendFailureWithCode(APPResponseError.ERROR_CODE_NO_PEER_CER);
			} else {
				sendFailureWithCode(APPResponseError.ERROR_CODE_NET);
			}
		}
	}
	
	private void sendSuccessWithResult(T result) {
		try {
			onSuccess(result);
		} catch (Exception e) {
			throw new OnSuccessException(e);
		}
	}
	
	private void sendFailureWithCode(int errorCode) {
    	onFailure(String.valueOf(errorCode), APPResponseError.getCustomErrorMsg(errorCode));
	}
	
    public String getResponseString() {
    	return this.responseString;
    }

	public static void setServiceResultManager (ServiceResultManager manager) {
		APPResponseHandler.mServiceResultManager = manager;
	}

	public static void setDecryptDelegate (APPResponseDecryptDelegate delegate) {
		APPResponseHandler.gDecryptDelegate = delegate;
	}

	/**
	 * 服务器响应字段设置
	 * @author tongxu_li
	 * Copyright (c) 2014 Shanghai P&C Information Technology Co., Ltd.
	 */
	public interface ServiceResultManager {
		/**
		 * 设置业务响应状态key
		 */
		String getStatusKey();
		/**
		 * 设置业务响应信息key
		 */
		String getMessgaeKey();
		/**
		 * 设置业务数据key
		 */
		String getResultKey();
    	/**
    	 * 设置成功状态码
    	 */
		String getSuccessStatus();
	}
}
