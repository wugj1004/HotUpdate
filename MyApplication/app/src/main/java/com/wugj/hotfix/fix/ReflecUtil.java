package com.wugj.hotfix.fix;

import java.lang.reflect.Field;

/**
 * description:
 * </br>
 * author: wugj
 * </br>
 * date: 2019/7/25
 * </br>
 * version:
 */
public class ReflecUtil {


    //通过反射获取BaseDexClassLoader的pathList属性
    public static Object getPathList(Object baseDexClassLoader)
            throws IllegalArgumentException, NoSuchFieldException, IllegalAccessException, ClassNotFoundException {
        return getField(baseDexClassLoader, Class.forName(Contacts.DEX_BASECLASSLOADER_CLASS_NAME),
                Contacts.DEX_PATHLIST_FIELD);
    }

    //获取DexPathList的dexElements属性，dexElements用于存放已经加载的dex文件
    public static Object getDexElements(Object paramObject)
            throws IllegalArgumentException, NoSuchFieldException, IllegalAccessException {
        return getField(paramObject, paramObject.getClass(), Contacts.DEX_ELEMENTS_FIELD);
    }

    public static Object getField(Object obj, Class<?> cl, String field)
            throws NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
        Field localField = cl.getDeclaredField(field);
        localField.setAccessible(true);
        return localField.get(obj);
    }

    public static void setField(Object obj, Class<?> cl, Object value)
            throws NoSuchFieldException, IllegalArgumentException, IllegalAccessException {

        Field localField = cl.getDeclaredField(Contacts.DEX_ELEMENTS_FIELD);
        localField.setAccessible(true);
        localField.set(obj, value);
    }

}
