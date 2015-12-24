package com.rush.qqdexpose.dynamic;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;
import android.content.res.AssetManager;

public class FileUtils {

    public static String copyFile(File srcFile, File dir) throws IOException {

        dir.mkdirs();
        File outFile = null;
        if (srcFile.listFiles() == null) {
            return "";
        }
        for (File inputFile : srcFile.listFiles()) {
            outFile = new File(dir, inputFile.getName());
            if (!outFile.exists()) {
                InputStream in = new FileInputStream(inputFile);
                OutputStream out = new FileOutputStream(outFile);
                copyFile(in, out);
                in.close();
                out.close();
            }
        }
        return outFile.getAbsolutePath();
    }

    private static void copyFile(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int read;
        while ((read = in.read(buffer)) != -1) {
            out.write(buffer, 0, read);
        }
    }
}
