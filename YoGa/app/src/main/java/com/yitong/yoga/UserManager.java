package com.yitong.yoga;

import com.yitong.yoga.bean.LoginUserInfo;


/**
 * 用户信息管理帮助类
 */
public class UserManager {
	/**
	 * session超时标志
	 */
	public static final String FLAG_SESSION_TIME_OUT = "005";
	
	private boolean isLogin = false;// 记录用户的状态，登录为true，否则为false
//	private UserInfoVo userInfo;
	private LoginUserInfo userInfo;
//	private DynamicMenuVo clickedMenu;	//记录用户上次点击菜单
	private static final String TAG="UserManager";
	public synchronized static UserManager getInstance() {
		if (null == manager) {
			manager = new UserManager();
		}
		return manager;
	}

	private static UserManager manager = null;
	
	private UserManager() {
		
	}

	public boolean isLogin() {
		return isLogin;
	}

	public void setLogin(boolean isLogin) {
		this.isLogin = isLogin;
		if (!isLogin) {
			userInfo = null;
		}
	}

//	public UserInfoVo getUserInfo() {
//		return userInfo;
//	}
	public LoginUserInfo getUserInfo() {
		return userInfo;
	}

//	public void setUserInfo(UserInfoVo userInfo) {
		public void setUserInfo(LoginUserInfo userInfo) {

		if (userInfo == null) {
			this.userInfo = null;
			isLogin = false;
		} else {
//			isLogin = true;
			this.userInfo = userInfo;
		}
	}

//	public DynamicMenuVo getClickedMenu() {
//		return clickedMenu;
//	}

//	public void setClickedMenu(DynamicMenuVo clickedMenu) {
//		this.clickedMenu = clickedMenu;
//	}

//	public void loginOut(final boolean flag, final Activity context) {
//		// 显示等待层
//		Log.v(TAG, "等待层开始");
//		// 发送登录请求
//		Log.v(TAG, "发送请求开始");
//		if(null==UserManager.getInstance().getUserInfo()|| StringUtil.isEmpty(UserManager.getInstance().getUserInfo().getPhone_number()))return;
//		YTRequestParams params = new YTBaseRequestParams(YTBaseRequestParams.PARAM_TYPE_JSON);
//		params.put("phone_number",UserManager.getInstance().getUserInfo().getPhone_number());//电话号码
//		params.put("area_code", UserManager.getInstance().getUserInfo().getArea_code());//账户
//		params.put("DEVICE_UUID", AndroidUtil.getDeviceUUID(context));//设备id
//		params.put("CLIENT_OS_TYPE", "android");//设备类型
//		String key = CryptoUtil.genRandomKey();
//		Logs.e(TAG, ServiceUrlManager.getServiceAbsUrl(ServiceCode.LOG_OUT,context));
//		Logs.e(TAG, params.getParamsString());
//		APPRestClient.post(ServiceUrlManager.getServiceAbsUrl(ServiceCode.LOG_OUT,context), params,
//				new APPResponseHandler<LogOutBean>(LogOutBean.class, key) {
//					@Override
//					public void onSuccess(LogOutBean result) {
//						Logs.e(TAG, "onSuccess");
//						Logs.e(TAG, result.toString());
//						APPRestClient.clearCookies();
//						UserManager.getInstance().setLogin(false);
//						if(flag){
////							YTActivityTack.getInstanse().exit();
//							YTActivityTack.getInstanse().exitOnlyOne();
//							context.finish();
//						}
//					}
//
//					@Override
//					public void onFailure(String errorCode, String errorMsg) {
//						Logs.e(TAG, "onFailure");
//						// 登录失败超过三次需要输入验证码
////						UserManager.getInstance().setLogin(false);
//						if(flag){
//							YTActivityTack.getInstanse().exit();
//						}
//
//						Logs.e(TAG, errorMsg);
//					}
//
//					@Override
//					public void onFinish() {
//						Logs.e(TAG, "onFinish");
//					}
//
//				}, key);
//	}

//	public  void gotoLogin(final Activity activity, final Dialog waitDialog) {
//		// 显示等待层
//		waitDialog.show();
//		YTRequestParams params = new YTBaseRequestParams(YTBaseRequestParams.PARAM_TYPE_JSON);
////		params.put("phone_number", "62075169");//设备类型
//		params.put("DEVICE_UUID", AndroidUtil.getDeviceUUID(activity));//设备id
//		params.put("CLIENT_OS_TYPE", "android");//设备类型
//		Logs.e(TAG, ServiceUrlManager.getServiceAbsUrl(ServiceCode.IS_LOGING,activity));
//		APPRestClient.post(ServiceUrlManager.getServiceAbsUrl(ServiceCode.IS_LOGING,activity), params,
//				new APPResponseHandler<DoLogin>(DoLogin.class, null) {
//					@Override
//					public void onSuccess(DoLogin result) {
//						Logs.v(TAG, "onSuccess");
//						Logs.v(TAG, result.toString());
//
//						if (result.getSTATUS().equals("1")) {
////
//							Intent intent = new Intent(activity, LoginActivity.class);
//							activity.startActivity(intent);
//
//						}
//
//
//					}
//
//					@Override
//					public void onFailure(String errorCode, String errorMsg) {
//						Logs.e(TAG, "onFailure");
//						// 登录失败超过三次需要输入验证码
////						if (failCount.incrementAndGet() >= MAX_ERROR_COUNT) {
//////                            llCheckCode.setVisibility(View.VISIBLE);
//////                            getCheckCode();
////						}
//						ToastTools.showShort(activity,errorMsg);
//						if(errorCode.equals("PHONE_NOT_LOGIN")){
//							Intent intent = new Intent(activity, LoginActivity.class);
//							activity.startActivity(intent);
//						}else if(errorCode.equals("006")){
//							Intent intent = new Intent(activity, LoginActivity.class);
//							activity.startActivity(intent);
//						}
//
////						}
//					}
//
//					@Override
//					public void onFinish() {
//						Logs.e(TAG, "onFinish");
////						if (waitDialog!=null) {
//							waitDialog.dismiss();
////						}
//					}
//
//				}, null);
//		Logs.v(TAG, "发送请求结束");
//	}
}
