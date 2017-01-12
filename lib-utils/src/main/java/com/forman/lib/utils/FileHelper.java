package com.forman.lib.utils;

import android.content.Context;
import android.os.Environment;

import java.io.File;

/**
 * 文件帮助类，主要用于初始化和获取文件目录
 * Created by LinXin on 2016/8/2 9:06.
 */
public class FileHelper {
    private static String sdDir;
    private static String rootDir;

    public static void init(Context context, String rootDir) {
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) && FileUtils.hasExternalStoragePermission(context)) {
            sdDir = Environment.getExternalStorageDirectory().getPath();
        } else {
            sdDir = context.getCacheDir().getPath();
        }
        FileHelper.rootDir = rootDir;
    }

    /**
     * 获取根目录
     *
     * @return 根目录
     */
    public static String getRootDir() {
        return sdDir + "/" + rootDir;
    }

    /**
     * 获取指定目录
     *
     * @param dirName 目录名称
     * @return 指定目录的位置
     */
    public static String getDir(String dirName) {
        File file = new File(sdDir, dirName);
        if (!file.exists()) {
            file.mkdirs();
        }
        return file.getPath();
    }
}
