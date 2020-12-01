package com.example.newsboard.base;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 *     author : Zhong DeFeng
 *     e-mail : 1756809298@qq.com
 *     time   : 2020/11/16 14:21
 *     desc   : 活动控制器，负责增、删、停止所有活动
 *     version: 1.0
 * </pre>
 */
public class ActivityController {

    /**
     * 活动列表
     */
    private static final List<Activity> activityList = new ArrayList<>();

    /**
     * 添加活动，onCreate()时调用
     * @param activity 活动
     */
    public static void addActivity(Activity activity) {
        activityList.add(activity);
    }

    /**
     * 移除活动，onDestroy()时调用
     * @param activity 活动
     */
    public static void removeActivity(Activity activity) {
        activityList.remove(activity);
    }

    /**
     * 关闭所有活动，在退出登录时调用
     */
    public static void finishAll() {
        activityList.parallelStream()
                .filter(activity -> !activity.isFinishing())
                .forEach(Activity::finish);
    }
}
