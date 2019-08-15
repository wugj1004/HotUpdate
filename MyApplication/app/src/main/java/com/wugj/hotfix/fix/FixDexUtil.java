package com.wugj.hotfix.fix;

import android.content.Context;

import java.io.File;
import java.util.HashSet;

import dalvik.system.DexClassLoader;
import dalvik.system.PathClassLoader;

/**
 * description:
 * </br>
 * author: wugj
 * </br>
 * date: 2019/7/25
 * </br>
 * version:
 */
public class FixDexUtil {

    private static HashSet<File> loadedDex = new HashSet<>();

    static {
        loadedDex.clear();
    }

    public static void loadFixDex(Context context) {

        if (context == null) return;
        //这里重要否则无法获取安装包下dex
        File fixDexFile = context.getDir(Contacts.FIX_DEX_PATH, Context.MODE_PRIVATE);
        File[] files = fixDexFile.listFiles();

        for (File file : files) {
            if (file.getName().endsWith(Contacts.DEX_FILE_E) && !"classes.dex".equals(file.getName())) {
                loadedDex.add(file);
            }
        }

        createDexClassLoader(context, fixDexFile);
    }

    private static void createDexClassLoader(Context context, File fixDexFile) {

        File fileOpt = new File(fixDexFile.getAbsolutePath(), Contacts.DEX_OPT_DIR);
        if (!fileOpt.exists()) {
            fileOpt.mkdirs();
        }
        String optimizedDirectory = fileOpt.getAbsolutePath();

        for (File dex : loadedDex) {
            //1、获取DexClassLoader类加载器
            DexClassLoader dexClassLoader = new DexClassLoader(dex.getAbsolutePath(), optimizedDirectory, null,
                    context.getClassLoader());

            hotFix(dexClassLoader, context);
        }

    }

    private static void hotFix(DexClassLoader dexClassLoader, Context context) {

        //2、获取PathClassLoader类加载器
        PathClassLoader pathClassLoader = (PathClassLoader) context.getClassLoader();

        try {
            //3、获取补丁DexClassLoader中PathList中的Elements
            Object fixDexElements = ReflecUtil.getDexElements(ReflecUtil.getPathList(dexClassLoader));

            //4、获取系统PathClassLoader中PathList中的Elements
            Object sysDexElements = ReflecUtil.getDexElements(ReflecUtil.getPathList(pathClassLoader));

            //5、合并Elements且Dex对对应Elements前置
            Object finalElements = ArrayUtil.combineArray(fixDexElements, sysDexElements);

            //6、反射更新PathClassLoader中PathList中Elements
            Object sysPathList = ReflecUtil.getPathList(pathClassLoader);
            ReflecUtil.setField(sysPathList, sysPathList.getClass(), finalElements);


        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
