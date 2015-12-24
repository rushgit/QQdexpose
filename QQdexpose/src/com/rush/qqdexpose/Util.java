/**
 * Copyright (C) 2006-2015 Tuniu All rights reserved
 */
package com.rush.qqdexpose;

import android.widget.Toast;

public class Util {

    public static void toast(String str) {
        Toast.makeText(QQdexposeApplication.getInstance(), str, Toast.LENGTH_SHORT).show();
    }
}
