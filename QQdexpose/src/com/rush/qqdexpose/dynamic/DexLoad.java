/**
 * Copyright (C) 2006-2015 Tuniu All rights reserved
 */
package com.rush.qqdexpose.dynamic;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.rush.qqdexpose.DexInstallHelper;
import com.rush.qqdexpose.QQdexposeApplication;
import com.rush.qqdexpose.Util;

import dalvik.system.BaseDexClassLoader;
import dalvik.system.PathClassLoader;

public class DexLoad {

    private static String patchApk = Environment.getExternalStorageDirectory() + File.separator + "patch" + File.separator + "patch.apk";
    private static String so = Environment.getExternalStorageDirectory() + File.separator + "patch" + File.separator + "so/arm64-v8a";

    private static BaseDexClassLoader mLoader;

    private DexLoad() {

    }

    private static void loadDex(Context context) throws IOException {
        FileUtils.copyFile(new File(so), new File(context.getFilesDir(), "so"));
        new File(context.getFilesDir(), "dex").mkdir();
//        mLoader = new DexClassLoader(patchApk, new File(context.getFilesDir(), "dex").getAbsolutePath(), new File(context.getFilesDir(), "so").getAbsolutePath(), context.getClassLoader());
        mLoader = new PathClassLoader(patchApk, new File(context.getFilesDir(), "so").getAbsolutePath(), context.getClassLoader());
        DexInstallHelper.printDexList(mLoader);
        File file = new File(context.getFilesDir(), "so");
        if (file == null) {
            return;
        }
        for (File f : file.listFiles()) {
            Log.i("TAG", "so: " + f.getAbsolutePath());
        }
    }

    public static Class<?> getClass(String name) throws ClassNotFoundException, IOException {
        if (mLoader == null) {
            loadDex(QQdexposeApplication.getInstance());
        }
        return mLoader.loadClass(name);
    }

    public static void invokeMethod() {
        try {
            Class<?> clazz = getClass("xx.xx.xx.xx");
            Log.i("TAG", "class: " + clazz);
            Method method = clazz.getDeclaredMethod("func", Context.class, String.class, String.class);
            method.invoke(clazz, QQdexposeApplication.getInstance(), "test", "test");
            Log.i("TAG", "dynamic load dex, invoke method success.");
            Util.toast("dynamic load dex, invoke method success.");
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("TAG", "dynamic load dex, invoke method failed. " + e);
            Util.toast("dynamic load dex, invoke method failed. ");
        }
    }
}
