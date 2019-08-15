package com.wugj.hotfix;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.wugj.hotfix.dex.FindClass;
import com.wugj.hotfix.fix.FixDexUtil;

/**
 * description:
 * </br>
 * author: wugj
 * </br>
 * date: 2019/7/24
 * </br>
 * version:
 */
public class MyApplication extends Application {
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
        FixDexUtil.loadFixDex(this);

        FindClass.getChangeClass(this);
    }
}
