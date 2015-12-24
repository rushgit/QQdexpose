/**
 * Copyright (C) 2006-2015 Tuniu All rights reserved
 */
package com.rush.qqdexpose;

import android.app.Application;
import android.content.Context;
import android.util.Log;

public class QQdexposeApplication extends Application {

    private static QQdexposeApplication sInstance;

    public static QQdexposeApplication getInstance() {
        return sInstance;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        sInstance = this;
        try {
            DexInstallHelper.installDex(this);
            DexInstallHelper.injectSoLib(this);
            Log.i("TAG", "loadPatch success");
        } catch (Exception e) {
            Log.e("TAG", "installDex error: " + e);
            e.printStackTrace();
        }
    }
}
