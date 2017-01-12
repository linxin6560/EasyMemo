package com.forman.lib.utils;

import android.app.Activity;

import java.util.ArrayList;

/**
 * Activity栈工具类
 * Created by LinXin on 2016/9/13 17:29.
 */
public class ActivityStack {

    private static ArrayList<Activity> ACTIVITY_LIST = new ArrayList<>();//因Activity在onDestroy的时候，会调用remove方法，所以不会存在强引用而造成内存泄露的情况，故不需要用弱引用

    public static void addActivity(Activity activity) {
        ACTIVITY_LIST.add(0, activity);
    }

    public static void removeActivity(Activity activity) {
        ACTIVITY_LIST.remove(activity);
    }

    /**
     * 获取当前页面上一级的Activity
     *
     * @return 上一级的activity
     */
    public static Activity getSuperiorActivity() {
        if (ACTIVITY_LIST.size() > 1) {
            return ACTIVITY_LIST.get(1);
        }
        return null;
    }
}
