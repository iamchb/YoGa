package com.yitong.yoga;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;

import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;
import com.yitong.yoga.http.ServiceUrlManager;
import com.yitong.yoga.http.YTBaseRequestParams;
import com.yitong.yoga.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.yitong.yoga.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.yitong.yoga.universalimageloader.core.DisplayImageOptions;
import com.yitong.yoga.universalimageloader.core.ImageLoader;
import com.yitong.yoga.universalimageloader.core.ImageLoaderConfiguration;
import com.yitong.yoga.universalimageloader.core.assist.ImageScaleType;
import com.yitong.yoga.universalimageloader.core.download.BaseImageDownloader;
import com.yitong.yoga.utils.AndroidUtil;
import com.yitong.yoga.utils.LangeuageUtils;
import com.yitong.yoga.utils.Logs;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by chb on 2016/12/26
 */

public class MyApplication extends Application {

    public static String Language = "0";

    public static MyApplication instance;

    private MyActivityLifecycleCallbacks mActivityCallback;

    public static final String TAG = "MyApplication";



    public static RefWatcher getRefWatcher(Context context) {
        MyApplication application = (MyApplication) context.getApplicationContext();
        return application.refWatcher;
    }

    private RefWatcher refWatcher;


    public static MyApplication getInstance() {
        if (null == instance) {
            instance = new MyApplication();
        }
        return instance;

    }


    @Override
    public void onCreate() {
        super.onCreate();
        // 请求启动页
        ServiceUrlManager.setServiceBaseUrl("http://10.10.0.62/MEG_STAR_CRM");//MegStarCRM

//		CrashHandler.getInstance().init(this);
        LeakCanary.install(this);
        refWatcher = LeakCanary.install(this);

        instance = this;
        init();

        mActivityCallback = new MyActivityLifecycleCallbacks();

    }

    /**
     * 初始化
     */
    private void init() {
        // 根据调试模式选择关闭或打开日志
        if (Constans.isDebug) {
            Logs.setLogLevel(Logs.VERBOSE);
        } else {
            Logs.closeLogs();
        }

        // 初始化网络请求参数
        initRequestConfig();

        // 初始化图片下载配置
        initImageLoaderConfig();

        // 初始化地图设置
//		initMapConfig();
    }

    //	private void initMapConfig() {
//		LocactionGdUtil.getInstance(getApplicationContext()).startLocation();
//	}
    private void initImageLoaderConfig() {

        // 图片显示配置
        DisplayImageOptions.Builder displayConfig = new DisplayImageOptions.Builder();
        displayConfig.showImageOnLoading(R.drawable.default_icon);
        displayConfig.showImageForEmptyUri(R.drawable.default_icon);
        displayConfig.showImageOnFail(R.drawable.default_icon);
        displayConfig.resetViewBeforeLoading(false);
        displayConfig.cacheInMemory(true);
        displayConfig.cacheOnDisk(true);
        displayConfig.considerExifParams(true);
        displayConfig.imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2);
        displayConfig.bitmapConfig(Bitmap.Config.RGB_565);

        // 下载参数配置
        ImageLoaderConfiguration.Builder config = new ImageLoaderConfiguration.Builder(instance);
//        config.memoryCacheSize(2 * 1024 * 1024);
        config.memoryCache(new WeakMemoryCache());
//        config.memoryCache(new LruMemoryCache(2 * 1024 * 1024));
        config.diskCacheSize(50 * 1024 * 1024);
        config.diskCacheFileCount(100);
        config.threadPoolSize(2);
        config.diskCacheFileNameGenerator(new Md5FileNameGenerator());
        config.imageDownloader(new BaseImageDownloader(instance));
        config.defaultDisplayImageOptions(displayConfig.build());
        if (Constans.isDebug) {
            config.writeDebugLogs();
        }

        ImageLoader.getInstance().init(config.build());
    }


    private void initRequestConfig() {

        // 设置公共参数配置
        YTBaseRequestParams.setParamsInitListener(new YTBaseRequestParams.OnParamsInitListener() {

            @Override
            public Map<String, Object> onAddCommonParams() {
                return getCommonParams();
            }
        });

//		// 设置全局请求加密代理
//		APPRestClient.setEncryptDelegate(new EncryptDelegate() {
//
//			@Override
//			public String getEncryptString(String decryptString, String key) {
//				String encryptString = "";
//				encryptString = CryptoUtil.encryptData(mApp, decryptString, key);
//				return encryptString;
//			}
//		});
//
//		// 设置应用请求解密代理
//		APPResponseHandler.setDecryptDelegate(new APPResponseDecryptDelegate() {
//
//			@Override
//			public String getDecryptString(String encryptString, String key) {
//				String decryptString = "";
//				decryptString = CryptoUtil.decryptData(mApp, encryptString, key);
//				return decryptString;
//			}
//		});
//
//		// 设置JS代理请求解密代理
//		AppJSResponseHandler.setDecryptDelegate(new APPResponseDecryptDelegate() {
//
//			@Override
//			public String getDecryptString(String encryptString, String key) {
//				String decryptString = "";
//				decryptString = CryptoUtil.decryptData(mApp, encryptString, key);
//				return decryptString;
//			}
//		});

        // TODO: 设置ssl单证书认证，注意上线前开启认证，检查证书
//         APPRestClient.setTrustSingleCertificate(mApp, "xxxxx.cer");
    }

//	public static ILocation getLocationUtil(){
//		return  LocactionGdUtil.getInstance();
//	}

    private Map<String, Object> getCommonParams() {
//        String Language = SharedPreferenceUtil.getInfoFromShared("LANGUAGE", "0");
        boolean isCN = LangeuageUtils.isCn(this);

        Map<String, Object> params = new HashMap<>();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        Date currentDate = new Date(System.currentTimeMillis());
        params.put("REQ_TIME", formatter.format(currentDate));
        params.put("CLIENT_NO", AndroidUtil.getDeviceUUID(MyApplication.getInstance()));
        params.put("CLIENT_TYPE", "AD");
        params.put("CLIENT_OS", "A");
        params.put("CLIENT_INFO", "1");
        params.put("X_LINE", "2");
        params.put("Y_LINE", "3");
        if (!isCN) {//繁体
            params.put("local", "0");
        } else {//简体
            params.put("local", "1");
        }
        return params;
    }


    @Override
    public void onTerminate() {
        super.onTerminate();
        unregisterActivityLifecycleCallbacks(mActivityCallback);
//        YTActivityTack.getInstanse().exitOnlyOne();
    }


    private class MyActivityLifecycleCallbacks implements ActivityLifecycleCallbacks {

        public void onActivityCreated(Activity activity, Bundle bundle) {
            Logs.e(TAG, "onActivityCreated: " + activity.getLocalClassName());
        }

        public void onActivityDestroyed(Activity activity) {
            Logs.e(TAG, "onActivityDestroyed: " + activity.getLocalClassName());
        }

        public void onActivityPaused(Activity activity) {
            Logs.e(TAG, "onActivityPaused: " + activity.getLocalClassName());
        }

        public void onActivityResumed(Activity activity) {
            Logs.e(TAG, "onActivityResumed: " + activity.getLocalClassName());
        }

        public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
            Logs.e(TAG, "onActivitySaveInstanceState: " + activity.getLocalClassName());
        }

        public void onActivityStarted(Activity activity) {
            Logs.e(TAG, "onActivityStarted: " + activity.getLocalClassName());
        }

        public void onActivityStopped(Activity activity) {
            Logs.e(TAG, "onActivityStopped: " + activity.getLocalClassName());
        }
    }


    public boolean isAppInForeground() {
        boolean result = false;
        Context context = getApplicationContext();
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
        final String packageName = context.getPackageName();
        if (appProcesses != null) {
            for (ActivityManager.RunningAppProcessInfo process : appProcesses) {
                if (process.processName.equalsIgnoreCase(packageName)) {
                    switch (process.importance) {
                        case ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND:
                        case ActivityManager.RunningAppProcessInfo.IMPORTANCE_VISIBLE:
                            result = true;
                            break;
                        case ActivityManager.RunningAppProcessInfo.IMPORTANCE_BACKGROUND:
                        case ActivityManager.RunningAppProcessInfo.IMPORTANCE_SERVICE:
                            break;
                    }
                    break;
                }
            }
        } else {
            Logs.e(TAG, "App " + packageName + " NOT running");
        }
        return result;
    }



}
