package com.wugj.hot.res;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.text.TextUtils;

import com.wugj.hot.R;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import dalvik.system.DexClassLoader;

/**
 * description:
 * </br>
 * author: wugj
 * </br>
 * date: 2019/10/18
 * </br>
 * version:
 */
public class ResManager {

    //皮肤包存储目录
    public static String archivePath = "/mnt/sdcard/skin";

    private Resources mResources;


    /**
     * 获取APK资源
     * @param context 上下文
     * @param skinFilePath APK路径
     */
    public void loadSkinRes(Context context, String skinFilePath) {
        if (TextUtils.isEmpty(skinFilePath)) {
            return ;
        }
        try {
            AssetManager assetManager = createAssetManager(skinFilePath);
            mResources = createResources(context, assetManager);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private AssetManager createAssetManager(String skinFilePath) {
        try {
            AssetManager assetManager = AssetManager.class.newInstance();
                Method addAssetPath = assetManager.getClass().getMethod("addAssetPath", String.class);
                addAssetPath.invoke(assetManager, skinFilePath);
            return assetManager;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private Resources createResources(Context context, AssetManager assetManager) {
        Resources superRes = context.getResources();

        Resources resources = new Resources(assetManager, superRes.getDisplayMetrics(), superRes.getConfiguration());
        return resources;
    }


    /**
     * 这个方法需要拿到所有资源id比对资源名来获取新资源id，性能太低
     * @param resId
     * @param context
     * @param externalResource
     * @param apkPath
     * @param apkPackageName
     * @return
     */
    @Deprecated
    public int obtainTargetResourceId(String resId, Context context, String externalResource,String apkPath, String apkPackageName){

        int id = -1;
        DexClassLoader dexClassLoader = new DexClassLoader(apkPath,
                context.getDir(externalResource,Context.MODE_PRIVATE).getAbsolutePath(),
                null,
                context.getClassLoader());

        // 运用反射：在皮肤插件R文件的drawable类中寻找插件资源的id
        try {
            Class<?> forName = dexClassLoader.loadClass(apkPackageName+".R$drawable");

            // 获取成员变量的值
            for (Field field : forName.getDeclaredFields()) {
                // 获取资源id
                if (field.getName().contains(resId)) {
                    id = field.getInt(R.drawable.class);
                    return id;
                }

            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return id;
    }


    public int obtainExternalResourceId(Context context, String fileName, int oldResourceId){
        int externalResourceIdentifier = -1;
        //资源名：skin
        String resourceEntryName = context.getResources().getResourceEntryName(oldResourceId);
        //资源类型：drawable
        String resourceTypeName =  context.getResources().getResourceTypeName(oldResourceId);
        System.out.println("resourceEntryName:"+resourceEntryName+";resourceTypeName:"+resourceTypeName);

        PackageManager pm = context.getPackageManager();
        String archive = String.format("%s%s%s",archivePath, File.separator,fileName);
        PackageInfo info = pm.getPackageArchiveInfo(archive, PackageManager.GET_ACTIVITIES);

        String packageName = info.packageName;
        externalResourceIdentifier = mResources.getIdentifier(resourceEntryName,resourceTypeName,packageName);

        return externalResourceIdentifier;
    }


    public Resources getSkinResource() {
        return mResources;
    }
}
