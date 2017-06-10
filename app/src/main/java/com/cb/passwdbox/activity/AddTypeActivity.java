package com.cb.passwdbox.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;

import com.cb.passwdbox.R;

/**
 * Created by chenbin on 16-8-14.
 */
public class AddTypeActivity extends Activity {
    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.window_add_type);
    }
}
