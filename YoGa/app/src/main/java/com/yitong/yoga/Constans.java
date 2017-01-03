package com.yitong.yoga;


import com.yitong.yoga.http.ServiceUrlManager;
import com.yitong.yoga.utils.SharedPreferenceUtil;

/**
 * 应用配置常量
 */
public class Constans {
	public static final String EXTRA_BUNDLE = "launchBundle";
	// 应用类型：手机银行
//	public static final String APP_TYPE = "001";
	
	// 是否使用了F5
	public static final boolean F5 = false;
	
	// 是否开启调试模式(注：上线前请关闭)
	public static final boolean isDebug = true;
	
	// 是否启用Web资源缓存
	public static final boolean isUseWebCache = false;
	// 静态缓存资源压缩包版本号(如果更新压缩包，注意修改此版本号，否则不会解压新的压缩包)
	public static final int webCacheVersion = 1;

	// 服务地址(注：最后不加"/"，通过ServiceUrlManager.getServiceAbsUrl获取完整地址)
//	public static final String SERVICE_URL = "http://192.168.1.54:8090/mbank";
//	public static final String SERVICE_URL = "http://10.10.10.71:8000/MEG_STAR_CRM_APP/page/login";
	// 网络资源地址(注：最后不加"/"，通过ServiceUrlManager.getResourceAbsUrl获取完整地址)

//	public static final String RESOURCE_URL = "http://192.168.1.54:8090/resources";
	// 默认菜单的资源路径
//	public static final String DEFAULT_MENU_URL = RESOURCE_URL+"/default";
	
	/**
	 * 测试时候更新地址，上线时候请删除此方法
	 * @param serviceUrl 接口服务地址
	 * @param resourceUrl 资源服务地址
	 */
	public static void updateServerUrl(String serviceUrl, String resourceUrl) {

		if (isDebug) {
			SharedPreferenceUtil.setInfoToShared("SERVICE_URL", serviceUrl);
			SharedPreferenceUtil.setInfoToShared("RESOURCE_URL", resourceUrl);
			ServiceUrlManager.setServiceBaseUrl(serviceUrl);
			ServiceUrlManager.setResourceBaseUrl(resourceUrl);			
		}
	}	
	
	/**
	 * 配置应用地址，应用启动时候调用
	 */
//	public static void configServerUrl() {
//		String strServiceUrl = SERVICE_URL;
//		String strResourceUrl = RESOURCE_URL;
//
//		if (isDebug) {
//			strServiceUrl = SharedPreferenceUtil.getInfoFromShared("SERVICE_URL", SERVICE_URL);
//			strResourceUrl = SharedPreferenceUtil.getInfoFromShared("RESOURCE_URL", RESOURCE_URL);
//		}
//
//		ServiceUrlManager.setServiceBaseUrl(strServiceUrl);
//		ServiceUrlManager.setResourceBaseUrl(strResourceUrl);
//
//	}
}
