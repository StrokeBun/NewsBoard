package com.example.newsboard.base;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 *     author : Zhong DeFeng
 *     e-mail : 1756809298@qq.com
 *     time   : 2020/11/16 14:21
 *     desc   : 活动控制器
 *     version: 1.0
 * </pre>
 */
public class ActivityController {

    private static final List<Activity> activityList = new ArrayList<>();

    public static void addActivity(Activity activity) {
        activityList.add(activity);
    }

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
