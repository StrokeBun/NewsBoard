package com.example.newsboard.base;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

public class ActivityController {

    private static final List<Activity> activityList = new ArrayList<>();

    public static void addActivity(Activity activity) {
        activityList.add(activity);
    }

    public static void removeActivity(Activity activity) {
        activityList.remove(activity);
    }

    public static void finishAll() {
        activityList.parallelStream()
                .filter(activity -> !activity.isFinishing())
                .forEach(Activity::finish);
    }
}
