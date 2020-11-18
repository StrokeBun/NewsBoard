package com.example.newsboard.base;

import android.app.Activity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ActivityController {

    private static final List<Activity> activityList = Collections.synchronizedList(new ArrayList<>());

    public static void addActivity(Activity activity) {
        activityList.add(activity);
    }

    public static void removeActivity(Activity activity) {
        activityList.remove(activity);
    }

    public static void finishAll() {
        activityList.stream()
                .filter(activity -> !activity.isFinishing())
                .forEach(Activity::finish);

    }
}
