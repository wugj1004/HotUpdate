package com.wugj.hot;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * description:
 * </br>
 * author: wugj
 * </br>
 * date: 2019/10/18
 * </br>
 * version:
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Context mContext = this;
        this.getResources();
        requestPermission();
    }


    void requestPermission(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            //文件读写权限
            String[] perms = {Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE,};
            for (String perm : perms){
                if (checkCallingOrSelfPermission(perm) == PackageManager.PERMISSION_DENIED){
                    requestPermissions(perms,200);
                }
            }
        }
    }

    public void jumpRes(View view){
        startActivity(new Intent(this,ThirdActivity.class));
    }

    public void jumpCode(View view){
        startActivity(new Intent(this,SecondActivity.class));
    }
}
