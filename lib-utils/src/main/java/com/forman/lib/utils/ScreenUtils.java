package com.forman.lib.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

/**
 * 屏幕相关工具类
 * Created by LinXin on 2016/5/27 15:27.
 */
public class ScreenUtils {

    public static int dp2px(Context context, float dip) {
        return (int) (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip, context.getResources()
                .getDisplayMetrics()) + 0.5f);
    }

    public static int px2sp(Context context, int px) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, px, context.getResources().getDisplayMetrics());
    }

    public static int getScreenHeight(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return dm.heightPixels;
    }

    public static int getScreenWidth(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return dm.widthPixels;
    }

    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    // below status bar,include actionbar, above softkeyboard
    public static int getAppHeight(Activity activity) {
        Rect localRect = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(localRect);
        return localRect.height();
    }

    public static int getKeyboardHeight(Activity activity) {
        return getScreenHeight(activity) - getStatusBarHeight(activity) - getAppHeight(activity);
    }

    public static FrameLayout getRootFrame(Activity activity) {
        View re = activity.findViewById(android.R.id.content);
        if (re != null && re instanceof FrameLayout) {
            return (FrameLayout) re;
        }
        ViewGroup viewGroup = (ViewGroup) activity.getWindow().getDecorView();
        re = viewGroup.getChildAt(viewGroup.getChildCount() - 1);
        if (re != null && re instanceof FrameLayout) {
            return (FrameLayout) re;
        } else {
            re = new FrameLayout(activity);
            ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            viewGroup.addView(re, lp);
            return (FrameLayout) re;
        }
    }
}
