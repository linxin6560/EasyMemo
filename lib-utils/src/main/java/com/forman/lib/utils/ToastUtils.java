package com.forman.lib.utils;

import android.app.Application;
import android.widget.Toast;

public class ToastUtils {
    private static Toast mToast;
    private static Application mApplication;

    private ToastUtils() {
    }

    public static void initApplication(Application application) {
        mApplication = application;
    }

    // -----------------------------只有内容的Toast----------------------------------
    public static void makeShortToast(int msg) {
        makeToast(msg, true);
    }

    public static void makeShortToast(String msg) {
        makeToast(msg, true);
    }

    public static void makeLongToast(int msg) {
        makeToast(msg, true);
    }

    public static void makeLongToast(String msg) {
        makeToast(msg, true);
    }

    /**
     * 消息提示，用一个全局的mToast来控制重复一直提示
     *
     * @param msg
     * @param isShort
     */
    private static void makeToast(String msg, boolean isShort) {
        if (mToast == null) {
            mToast = Toast.makeText(mApplication, msg, isShort ? Toast.LENGTH_SHORT : Toast.LENGTH_LONG);
        } else {
            mToast.setText(msg);
        }
        mToast.setDuration(isShort ? Toast.LENGTH_SHORT : Toast.LENGTH_LONG);
        mToast.show();
    }

    /**
     * 消息提示，用一个全局的mToast来控制重复一直提示
     *
     * @param msg
     * @param isShort
     */
    private static void makeToast(int msg, boolean isShort) {
        if (mToast == null) {
            mToast = Toast.makeText(mApplication, msg, isShort ? Toast.LENGTH_SHORT : Toast.LENGTH_LONG);
        } else {
            mToast.setText(msg);
        }
        mToast.setDuration(isShort ? Toast.LENGTH_SHORT : Toast.LENGTH_LONG);
        mToast.show();
    }
}
