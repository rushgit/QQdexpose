/**
 * Copyright (C) 2006-2015 Tuniu All rights reserved
 */
package com.rush.qqdexpose;

import android.content.Context;
import android.widget.Toast;

public class TestUtil {

    public static void showToast(Context context) {
        Toast.makeText(context, "patch showToast()", Toast.LENGTH_SHORT).show();
    }

    public static void loadSoLib(Context context) {
        Toast.makeText(context, "patch loadSoLib()", Toast.LENGTH_SHORT).show();
    }
}
