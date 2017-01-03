package com.yitong.yoga.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.telephony.TelephonyManager;

import java.io.File;
import java.util.List;
import java.util.UUID;

/**
 * android客户端工具类
 * 
 * @Description
 * @author 刘国山 lgs@yitong.com.cn
 * @version 1.0 2013年7月23日
 * @class com.yitong.zjrc.mbank.utils.yitong.AndroidUtil
 */
public class AndroidUtil {

	public static final String TAG = "AndroidUtil";
	
	/**
	 * 获取手机唯一序列号
	 * 注：如取不到设备号，则取UUID作为手机唯一序列号
	 */
	public static String getDeviceUUID(Context context) {
		String uuid = getDeviceId(context);
		if (StringTools.isEmpty(uuid)) {
			uuid = getUUID(context);
		}
		return uuid;
	}

	/**
	 * 获取手机序列号
	 */
	public static String getDeviceId(Context context) {
		String deviceID = null;
		
		TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		if (!StringTools.isEmpty(tm.getDeviceId())) {
			deviceID = tm.getDeviceId();
		}
		
		return deviceID;
	}
	
	/**
	 * 在取不到设备号时候，使用UUID作为手机唯一设备号
	 */
	public static final String MOBILE_SETTING = "MOBILE_SETTING";
	public static final String MOBILE_UUID = "MOBILE_UUID";
	private static String getUUID(Context context) {
		SharedPreferences mShare = context.getSharedPreferences(MOBILE_SETTING, Context.MODE_PRIVATE);
		String uuid = "";
		if (mShare != null && !StringTools.isEmpty(mShare.getString(MOBILE_UUID, ""))) {
			uuid = mShare.getString(MOBILE_UUID, "");
		}
		if (StringTools.isEmpty(uuid)) {
			uuid = UUID.randomUUID().toString();
			mShare.edit().putString(MOBILE_UUID, uuid).commit();
		}
		Logs.d("getUUID", "getUUID : " + uuid);
		return uuid;
	}

	/**
	 * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
	 */
	public static int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	/**
	 * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
	 */
	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	/**
	 * 取得操作系统版本号
	 */
	public static String getOSVersion(Context context) {
		return android.os.Build.VERSION.RELEASE;
	}
	
	/**
	 * 获取应用版本号
	 */
	public static String getAppVersion(Context context) {
		String strVersion = null;
		
		try {
			PackageInfo pi = null;
			pi = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
			if (pi != null) {
				strVersion = pi.versionName;
			}
		} catch (NameNotFoundException e) {
			Logs.e(TAG, e.getMessage(), e);
		}
		
		return strVersion;
	}
	
	/**
	 * 获取签名摘要
	 */
	public static String getSign(Context context) {
		String strSign = null;
		
		try {
			int flag = PackageManager.GET_SIGNATURES;
			PackageManager pm = context.getPackageManager();
			List<PackageInfo> apps = pm.getInstalledPackages(flag);
			Object[] objs = apps.toArray();
			for (int i = 0, j = objs.length; i < j; i++) {
				PackageInfo packageinfo = (PackageInfo) objs[i];
				String packageName = packageinfo.packageName;
				if (packageName.equals(context.getPackageName())) {
					Signature[] temps = packageinfo.signatures;
					Signature tmpSign = temps[0];
					strSign = tmpSign.toCharsString();
				}
			}
		} catch (Exception e) {
		}
		return strSign;
	}
	
	/**
	 * 判断手机是否ROOT
	 */	
	public static boolean isSystemRoot() {
		boolean isRoot = false;
		try {
			isRoot = !((!new File("/system/bin/su").exists())
					&& (!new File("/system/xbin/su").exists()));
			Logs.d("TAG", "isRoot  = " + isRoot);
		} catch (Exception e) {

		}
		return isRoot;
	}
}
