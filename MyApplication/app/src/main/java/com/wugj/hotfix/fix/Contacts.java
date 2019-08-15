package com.wugj.hotfix.fix;

/**
 * description:
 * </br>
 * author: wugj
 * </br>
 * date: 2019/7/26
 * </br>
 * version:
 */
public class Contacts {
    public static final String FIX_DEX_PATH = "odex";//fixDex存储的路径
    public static final String DEX_OPT_DIR = "opt_dex";//dex的优化路径
    public static final String DEX_BASECLASSLOADER_CLASS_NAME = "dalvik.system.BaseDexClassLoader";
    public static final String DEX_PATHLIST_FIELD = "pathList";//BaseClassLoader中的pathList字段
    public static final String DEX_ELEMENTS_FIELD = "dexElements";//pathList中的dexElements字段
    public static final String DEX_FILE_E = ".dex";//扩展名
}
