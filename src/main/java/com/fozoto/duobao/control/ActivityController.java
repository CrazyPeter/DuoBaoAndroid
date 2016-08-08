package com.fozoto.duobao.control;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;

import java.util.LinkedList;

/**
 * Created by qingyan on 16-7-4.
 */

public class ActivityController {

    private static LinkedList<Activity> activities = new LinkedList<>();

    /**
     * 加入Activity控制
     * @param activity 当前Activity
     */
    public static void addActivity(Activity activity) {
        activities.add(activity);
    }

    /**
     * 从Activity控制中移除
     * @param activity 当前Activity
     */
    public static void removeActivity(Activity activity) {
        activities.remove(activity);
    }

    /**
     * 关闭所有Activity,退出App,未杀死后台服务.
     */
    public static void finishAll() {
        if (activities!=null) {
            for (Activity activity : activities) {
                if (!activity.isFinishing()) {
                    activity.finish();
                }
            }
        }
    }

    /**
     * 完全退出App,杀死后台服务
     * @param context 上下文
     */
    public static void AppExit(Context context) {
        ActivityController.finishAll();
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        activityManager.killBackgroundProcesses(context.getPackageName());
        System.exit(0);
    }
}
