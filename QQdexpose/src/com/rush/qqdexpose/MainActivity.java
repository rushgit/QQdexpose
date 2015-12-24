package com.rush.qqdexpose;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.rush.qqdexpose.dynamic.DexLoad;

public class MainActivity extends Activity implements View.OnClickListener {

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        findViewById(R.id.btn1).setOnClickListener(this);
        findViewById(R.id.btn2).setOnClickListener(this);
        findViewById(R.id.btn3).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn1:
                TestUtil.showToast(this);
                break;
            case R.id.btn2:
                TestUtil.loadSoLib(this);
                break;
            case R.id.btn3:
                DexLoad.invokeMethod();
                break;
        }
    }
}
