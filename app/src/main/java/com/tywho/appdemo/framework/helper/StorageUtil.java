package com.tywho.appdemo.framework.helper;

import android.content.Context;
import android.os.Environment;

import java.io.File;

/**
 * Created by qbm on 2016/1/11.
 */
public class StorageUtil {

    private static boolean isSDCardMounted() {
        return (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) || !Environment
                .isExternalStorageRemovable());
    }

    public static File getDiskCacheDir(Context context, String name) {
        String cachePath = "";
        try {
            if (isSDCardMounted()) {
                cachePath = context.getExternalCacheDir().getPath();
            } else {
                cachePath = context.getCacheDir().getPath();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new File(cachePath + File.separator + name);
    }

    public static String getSDCardPath() {
        File dir = null;
        if (isSDCardMounted()) {
            dir = Environment.getExternalStorageDirectory();//获取跟目录
        }
        return dir == null ? null : dir.getAbsolutePath();
    }
}
