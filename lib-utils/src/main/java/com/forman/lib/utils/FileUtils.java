package com.forman.lib.utils;

import android.content.Context;
import android.content.pm.PackageManager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 文件工具类
 * Created by LinXin on 2016/8/2 9:11.
 */
public class FileUtils {
    private static final String EXTERNAL_STORAGE_PERMISSION = "android.permission.WRITE_EXTERNAL_STORAGE";

    /**
     * 判断是否有读取文件的权限
     *
     * @param context
     * @return
     */
    public static boolean hasExternalStoragePermission(Context context) {
        int perm = context.checkCallingOrSelfPermission(EXTERNAL_STORAGE_PERMISSION);
        return perm == PackageManager.PERMISSION_GRANTED;
    }

    /**
     * 删除文件夹
     *
     * @param oldPath 文件夹
     */
    public static void deleteFile(File oldPath) {
        if (oldPath.isDirectory()) {
            File[] files = oldPath.listFiles();
            for (File file : files) {
                deleteFile(file);
                file.delete();
            }
        } else {
            oldPath.delete();
        }
    }

    /**
     * 保存文件
     *
     * @param filePath
     * @param fileName
     * @param bytes
     * @return
     */
    public static String saveFile(String filePath, String fileName, byte[] bytes) {
        String fileFullName = "";
        FileOutputStream fos = null;
        try {
            String suffix = "";
            File file = new File(filePath);
            if (!file.exists()) {
                file.mkdirs();
            }
            File fullFile = new File(filePath, fileName + suffix);
            fileFullName = fullFile.getPath();
            fos = new FileOutputStream(new File(filePath, fileName + suffix));
            fos.write(bytes);
        } catch (Exception e) {
            fileFullName = "";
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    fileFullName = "";
                }
            }
        }
        return fileFullName;
    }
}
