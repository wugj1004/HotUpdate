package com.wugj.hotfix.fix;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import static com.wugj.hotfix.fix.Contacts.DEX_FILE_E;
import static com.wugj.hotfix.fix.Contacts.FIX_DEX_PATH;

/**
 * description:
 * </br>
 * author: wugj
 * </br>
 * date: 2019/7/24
 * </br>
 * version:
 */
public class FileUtil {

    private static String TAG = "FileUtil";


    public static void copyDexFileToApp(Context context, String dexFileName, boolean copyAndFix) throws FileNotFoundException {
        File pathFile = new File(Environment.getExternalStorageDirectory(), dexFileName);
        if (!pathFile.exists()) {
            Toast.makeText(context, "没有找到补丁文件", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!pathFile.getAbsolutePath().endsWith(DEX_FILE_E)){
            Toast.makeText(context, "补丁文件格式不正确", Toast.LENGTH_SHORT).show();
            return;
        }

        File dexFilePath = context.getDir(FIX_DEX_PATH, Context.MODE_PRIVATE);
        File fixDexFile = new File(dexFilePath, dexFileName);
        if (fixDexFile.exists()) {
            fixDexFile.delete();
        }


        boolean copyRight = copyFile(pathFile,fixDexFile);
        if (copyRight){
        Toast.makeText(context,"文件拷贝完成！！",Toast.LENGTH_SHORT).show();
        }
    }


    //拷贝文件
    private static boolean copyFile(File path, File dexFile) throws FileNotFoundException {

        boolean end = false;
        FileInputStream fis = new FileInputStream(path);
        FileOutputStream fos= new FileOutputStream(dexFile);

        try {
            int hasRead = 0;
            byte[] buf = new byte[1024];
            while((hasRead = fis.read(buf)) > 0) {
                fos.write(buf, 0, hasRead);
            }
            fos.flush();
            end = true;
        } catch (Exception e) {
            Log.d(TAG, "copyFile error " + e);

        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
                if(fis != null) {
                    fis.close();
                }
            } catch (Exception e2) {
                Log.d(TAG, "copyFile close error " + e2);
            }
        }
        return end;
    }
}
