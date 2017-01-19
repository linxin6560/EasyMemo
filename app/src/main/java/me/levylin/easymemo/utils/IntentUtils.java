package me.levylin.easymemo.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

/**
 * Created by LinXin on 2017/1/19 10:46.
 */
public class IntentUtils {

    public static void launch(Context context, Class<? extends Activity> clazz) {
        Intent intent = new Intent(context, clazz);
        context.startActivity(intent);
    }
}
