package com.wugj.hotfix;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.wugj.hotfix.fix.FileUtil;
import com.wugj.hotfix.fix.FixDexUtil;

import java.io.FileNotFoundException;

/**
 * description:
 * </br>
 * author: wugj
 * </br>
 * date: 2019/7/24
 * </br>
 * version:
 */
public class SecondActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

    }


    public void hotFix(View view) {
        try {
            FileUtil.copyDexFileToApp(this,"classes2.dex",true);

            FixDexUtil.loadFixDex(this);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


    public void showCalculater(View view){

        new Calculater().calculate(this);
    }
}
