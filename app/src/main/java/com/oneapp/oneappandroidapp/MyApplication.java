package com.oneapp.oneappandroidapp;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.alipay.mobile.framework.quinoxless.IInitCallback;
import com.alipay.mobile.framework.quinoxless.QuinoxlessFramework;
import com.alipay.mobile.nebula.provider.H5AppCenterPresetProvider;
import com.alipay.mobile.nebula.util.H5Utils;
import com.mpaas.mas.adapter.api.MPLogger;
import com.mpaas.nebula.adapter.api.MPTinyHelper;
import com.mpaas.tinyappcommonres.TinyAppCenterPresetProvider;

public class MyApplication extends Application {
    private static final String TAG = "MyApplication";

    @Override
    protected void attachBaseContext(Context base) {
        Log.i(TAG, "attachBaseContext: going");
        super.attachBaseContext(base);
        QuinoxlessFramework.setup(this, new IInitCallback() {
            @Override
            public void onPostInit() {
                H5Utils.setProvider(H5AppCenterPresetProvider.class.getName(),
                        new TinyAppCenterPresetProvider());
                MPTinyHelper tinyHelper = MPTinyHelper.getInstance();
                tinyHelper.setTinyAppVHost("voneapp.com");
                MPLogger.setUserId("jc");
            }
        });
    }

    @Override
    public void onCreate() {
        super.onCreate();
        QuinoxlessFramework.init();
    }
}
