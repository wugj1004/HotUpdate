package com.wugj.hot;

import android.app.Application;
import android.content.Context;

/**
 * description:
 * </br>
 * author: wugj
 * </br>
 * date: 2019/10/18
 * </br>
 * version:
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        getResources();
    }
}
