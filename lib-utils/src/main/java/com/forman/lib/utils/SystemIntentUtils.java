package com.forman.lib.utils;

import android.content.Intent;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.provider.MediaStore;

import java.io.File;

/**
 * 系统帮助类
 *
 * @author LinX
 * @date 2014年10月29日 下午2:14
 */
public class SystemIntentUtils {
    /**
     * 调用系统图片浏览程序
     */
    private static final String CONTENT_TYPE_IMAGE = "image/*";

    /**
     * 返回跳转到系统网络设置页的 intent
     *
     * @return
     */
    public static Intent createJump2SystemNetworkSettingIntent() {
        return new Intent(WifiManager.ACTION_PICK_WIFI_NETWORK);
    }

    /**
     * 返回跳转到系统相册页的 intent
     *
     * @return
     */
    public static Intent createJump2SystemAblumIntent() {
        Intent jumpIntent = new Intent(Intent.ACTION_PICK, null);
        jumpIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, CONTENT_TYPE_IMAGE);
        return jumpIntent;
    }

    /**
     * 返回跳转到系统相机页的 intent
     *
     * @param path
     * @return
     */
    public static Intent createJump2SystemCameraIntent(String path) {
        Intent jumpIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        jumpIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(path)));
        jumpIntent.putExtra(MediaStore.EXTRA_SCREEN_ORIENTATION, true);
        return jumpIntent;
    }

    /**
     * 返回跳转到系统相机页的 intent
     *
     * @param root
     * @param fileName
     * @return
     */
    public static Intent createJump2SystemCameraIntent(String root, String fileName) {
        Intent jumpIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File outputFile = new File(root, fileName);
        jumpIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(outputFile));
        jumpIntent.putExtra(MediaStore.EXTRA_SCREEN_ORIENTATION, true);
        return jumpIntent;
    }

    /**
     * 返回跳转到系统相机页的 intent
     *
     * @return
     */
    public static Intent createJump2SystemCameraIntent() {
        Intent jumpIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        return jumpIntent;
    }
}
