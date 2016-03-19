package com.rush.qqdexpose;

import java.io.File;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import dalvik.system.DexClassLoader;

public class DexInstallHelper {

    private static final String TAG = "TAG";

    private static String patchApk = Environment.getExternalStorageDirectory() + File.separator + "patch" + File.separator + "patch.apk";
    private static String so = Environment.getExternalStorageDirectory() + File.separator + "patch" + File.separator + "so/arm64-v8a";

    public static void installDex(Context context) throws IllegalArgumentException, IllegalAccessException,
            NoSuchFieldException, InvocationTargetException, NoSuchMethodException, ClassNotFoundException {
        ClassLoader loader = context.getClassLoader();
        Field pathListField = findField(loader, "pathList");
        Object dexPathList = pathListField.get(loader);
        File dexOptDir = new File(context.getFilesDir(), "dex");
        dexOptDir.mkdir();
        DexClassLoader dexClassLoader = new DexClassLoader(patchApk, dexOptDir.getAbsolutePath(), /*patchApk*/null, context.getClassLoader());
        expandFieldArrayHead(dexPathList, "dexElements", getDexElements(getPathList(dexClassLoader)));

        printDexList(loader);
    }

    public static void injectSoLib(Context context) throws NoSuchFieldException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Log.i("TAG", "injectSoLib()");
        ClassLoader loader = context.getClassLoader();
        Field pathListField = findField(loader, "pathList");
        Object dexPathList = pathListField.get(loader);
        File[] soFiles = new File[]{new File(so)};
        expandFieldArrayHead(dexPathList, "nativeLibraryDirectories", soFiles);

        printDexList(loader);
    }

    public static void printDexList(ClassLoader loader) {
        try {
            Field pathListField = findField(loader, "pathList");
            Object dexPathList = pathListField.get(loader);
            Method method = findMethod(dexPathList, "toString");
            String dexList = (String) method.invoke(dexPathList);
            Log.i(TAG, "dexList: " + dexList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void expandFieldArrayHead(Object instance, String fieldName,
                                             Object[] extraElements) throws NoSuchFieldException, IllegalArgumentException,
            IllegalAccessException {
        Field jlrField = findField(instance, fieldName);
        Object[] original = (Object[]) jlrField.get(instance);
        Object[] combined = (Object[]) Array.newInstance(
                original.getClass().getComponentType(), original.length + extraElements.length);
        System.arraycopy(extraElements, 0, combined, 0, extraElements.length);
        System.arraycopy(original, 0, combined, extraElements.length, original.length);
        jlrField.set(instance, combined);
    }

    private static Object getPathList(Object obj) throws ClassNotFoundException, NoSuchFieldException,
            IllegalAccessException {
        return findField(obj, "pathList").get(obj);
    }

    private static Object[] getDexElements(Object obj) throws NoSuchFieldException, IllegalAccessException {
        Log.i(TAG, "dexElements type: " + findField(obj, "dexElements").get(obj).getClass().toString());
        return (Object[]) (findField(obj, "dexElements").get(obj));
    }

    private static Field findField(Object instance, String name) throws NoSuchFieldException {
        for (Class<?> clazz = instance.getClass(); clazz != null; clazz = clazz.getSuperclass()) {
            try {
                Field field = clazz.getDeclaredField(name);


                if (!field.isAccessible()) {
                    field.setAccessible(true);
                }

                return field;
            } catch (NoSuchFieldException e) {
                // ignore and search next
            }
        }

        throw new NoSuchFieldException("Field " + name + " not found in " + instance.getClass());
    }

    private static Method findMethod(Object instance, String name, Class<?>... parameterTypes)
            throws NoSuchMethodException {
        for (Class<?> clazz = instance.getClass(); clazz != null; clazz = clazz.getSuperclass()) {
            try {
                Method method = clazz.getDeclaredMethod(name, parameterTypes);


                if (!method.isAccessible()) {
                    method.setAccessible(true);
                }

                return method;
            } catch (NoSuchMethodException e) {
                // ignore and search next
            }
        }

        throw new NoSuchMethodException("Method " + name + " with parameters " +
                Arrays.asList(parameterTypes) + " not found in " + instance.getClass());
    }
}
