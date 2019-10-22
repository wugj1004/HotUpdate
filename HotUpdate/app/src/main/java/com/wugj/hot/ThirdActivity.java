package com.wugj.hot;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.Toast;

import com.wugj.hot.res.ResManager;

import java.io.File;

/**
 * description:
 * </br>
 * author: wugj
 * </br>
 * date: 2019/10/18
 * </br>
 * version:
 */
public class ThirdActivity extends AppCompatActivity {

    private Context mContext;
    private ImageView icon;
    private ResManager mSkinManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);



        icon =  findViewById(R.id.icon);
        mContext = this;
        mSkinManager = new ResManager();




        this.getResources();

        mContext.getResources();

    }


    @Override
    protected void onStart() {
        super.onStart();


    }


    @Override
    protected void onResume() {
        super.onResume();

        new Thread(){
            @Override
            public void run() {
                super.run();

                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        checkNewSkin();
                    }
                });

            }
        }.start();


    }

    private void checkNewSkin() {

        //File packageFile =  mContext.getFilesDir();//系统分配目录

        File file = new File(ResManager.archivePath);
        if (!file.exists()){
            file.mkdir();
        }
        File[] skinFiles = file.listFiles();
        if (skinFiles == null || skinFiles.length == 0) {
            Toast.makeText(mContext, "暂无热修复资源", Toast.LENGTH_SHORT).show();
            return ;
        }
        //获取皮肤包文件
        File archiveFile = skinFiles[0];
        String archiveFilePath = archiveFile.getAbsolutePath();

        //加载外部皮肤包
        mSkinManager.loadSkinRes(mContext, archiveFilePath);
        if (mSkinManager.getSkinResource() != null) {
            //获取新资源id
            int drawSkinId = mSkinManager.obtainExternalResourceId(this,archiveFilePath, R.mipmap.skin);
            //加载新皮肤
            icon.setImageDrawable(mSkinManager.getSkinResource().getDrawable(drawSkinId));
        }
    }


}
