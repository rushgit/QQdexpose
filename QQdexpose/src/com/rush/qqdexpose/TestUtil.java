package com.rush.qqdexpose;

import android.content.Context;
import android.widget.Toast;

public class TestUtil {

    public static void showToast(Context context) {
        Toast.makeText(context, "origin showToast()", Toast.LENGTH_SHORT).show();
    }

    public static void loadSoLib(Context context) {
        Toast.makeText(context, "origin loadSoLib()", Toast.LENGTH_SHORT).show();
    }
}
