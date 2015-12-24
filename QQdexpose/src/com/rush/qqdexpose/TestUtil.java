/**
 * Copyright (C) 2006-2015 Tuniu All rights reserved
 */
package com.rush.qqdexpose;

import android.content.Context;
import android.widget.Toast;

import cn.shuzilm.core.Main;

public class TestUtil {

    public static void showToast(Context context) {
        Toast.makeText(context, "origin showToast()", Toast.LENGTH_SHORT).show();
    }

    public static void loadSoLib(Context context) {
        Main.go(context, "test", "test");
        Toast.makeText(context, "origin loadSoLib()", Toast.LENGTH_SHORT).show();
    }
}