package com.yitong.yoga.utils;

import android.app.Activity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * activity管理
 *
 * @Description
 * @Author lewis(lgs@yitong.com.cn) 2014-4-29 下午2:43:59
 * @Class ActivityTack Copyright (c) 2014 Shanghai P&C Information Technology
 * Co.,Ltd. All rights reserved.
 */
public class YTActivityTack {

    public List<Activity> activityList = new ArrayList<Activity>();

    public static YTActivityTack instance;

    public static YTActivityTack getInstanse() {
        if (null == instance) {
            instance = new YTActivityTack();
        }
        return instance;
    }

    private YTActivityTack() {

    }

    public void addActivity(Activity activity) {
        activityList.add(activity);
    }

    public void removeActivity(Activity activity) {
        if (activity != null) {
            activityList.remove(activity);
            activity.finish();
            activity = null;
        }
    }

    /**
     * 完全退出
     */
    public void exit() {
        while (activityList.size() > 0) {
            popActivity(activityList.get(activityList.size() - 1));
        }
        System.exit(0);
    }

    /**
     * 退出到顶级页面
     *
     * @Description
     * @Author Lewis(lgs@yitong.com.cn) 2014年6月22日 下午10:00:48
     */
    public void exitOnlyOne() {
        while (activityList.size() > 1) {
            popActivity(activityList.get(activityList.size() - 1));
        }
    }

    /**
     * 根据class name获取activity
     *
     * @param name
     * @return
     */
    public Activity getActivityByClassName(String name) {
        for (Activity ac : activityList) {
            if (ac.getClass().getName().indexOf(name) >= 0) {
                return ac;
            }
        }
        return null;
    }

    @SuppressWarnings("rawtypes")
    public Activity getActivityByClass(Class cs) {
        for (Activity ac : activityList) {
            if (ac.getClass().equals(cs)) {
                return ac;
            }
        }
        return null;
    }

    /**
     * 弹出activity
     *
     * @param activity
     */
    public void popActivity(Activity activity) {
        removeActivity(activity);
        activity.finish();
        activity = null;
    }

    /**
     * 弹出activity到
     *
     * @param cs
     */
    @SuppressWarnings("rawtypes")
    public void popUntilActivity(Class... cs) {
        List<Activity> list = new ArrayList<Activity>();
        for (int i = activityList.size() - 1; i >= 0; i--) {
            Activity ac = activityList.get(i);
            boolean isTop = false;
            for (int j = 0; j < cs.length; j++) {
                if (ac.getClass().equals(cs[j])) {
                    isTop = true;
                    break;
                }
            }
            if (!isTop) {
                list.add(ac);
            } else
                break;
        }
        for (Iterator<Activity> iterator = list.iterator(); iterator.hasNext(); ) {
            Activity activity = iterator.next();
            popActivity(activity);
        }
    }
}
