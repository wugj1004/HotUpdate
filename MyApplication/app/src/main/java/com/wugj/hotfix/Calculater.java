package com.wugj.hotfix;

import android.content.Context;
import android.widget.Toast;

import com.wugj.hotfix.dex.ChangeClass;

/**
 * description:
 * </br>
 * author: wugj
 * </br>
 * date: 2019/7/26
 * </br>
 * version:
 */
@ChangeClass
public class Calculater {
    public void calculate(Context context){
        int a = 666;
        int b = 2;
        Toast.makeText(context, "aaa"+(a/b), Toast.LENGTH_SHORT).show();
    }
}
