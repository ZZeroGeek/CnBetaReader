package org.zreo.cnbetareader.Utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Created by ywwxhz on 2014/10/17.
 */
public class FileKit {

    /**

     */
    /**
     * 获取缓存文件大小,这里不仅获取了sdCard缓存文件的目录，还获取了手机缓存的目录
     * @param context
     * @return
     */




    
    public static String getTotalCacheSize(Context context){
        //context.getCacheDir()获取的是内存缓存的目录/data/data/wj.com.universalimageloader/cache
        long cacheSize = getFolderSize(context.getCacheDir());
        //context.getExternalCacheDir()获取的是sdCard的缓存目录
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            cacheSize += getFolderSize(context.getExternalCacheDir());
        }
        return getFormatSize(cacheSize);
    }

    public static long getFolderSize(File file) {
        long size = 0;
        try {
            File[] fileList = file.listFiles();
            for (File aFileList : fileList) {
                if (aFileList.isDirectory()) {
                    size = size + getFolderSize(aFileList);
                } else {
                    size = size + aFileList.length();
                }
            }
        } catch (Exception ignored) {
        }
        return size;
    }

    public static long getFolderSize(String path) {
        File file = new File(path);
        long size = 0;
        try {
            File[] fileList = file.listFiles();
            for (File aFileList : fileList) {
                if (aFileList.isDirectory()) {
                    size = size + getFolderSize(aFileList);
                } else {
                    size = size + aFileList.length();
                }
            }
        } catch (Exception ignored) {
        }
        return size;
    }

    public static String getFormatSize(double size) {
        double kiloByte = size / 1024;
        if (kiloByte < 1) {
//            return size + "Byte";
            return "0K";
        }

        double megaByte = kiloByte / 1024;
        if (megaByte < 1) {
            BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));
            return result1.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "KB";
        }

        double gigaByte = megaByte / 1024;
        if (gigaByte < 1) {
            BigDecimal result2 = new BigDecimal(Double.toString(megaByte));
            return result2.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "MB";
        }

        double teraBytes = gigaByte / 1024;
        if (teraBytes < 1) {
            BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));
            return result3.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "GB";
        }
        BigDecimal result4 = new BigDecimal(teraBytes);
        return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString()
                + "TB";
    }


    public static void deleteDir(File dir) {
        File to = new File(dir.getAbsolutePath() + System.currentTimeMillis());
        dir.renameTo(to);// in order to fix android java.io.IOException: open failed: EBUSY (Device or resource busy)
        // detail http://stackoverflow.com/questions/11539657/open-failed-ebusy-device-or-resource-busy
        if (to.isDirectory()) {
            String[] children = to.list();
            for (String aChildren : children) {
                File temp = new File(to, aChildren);
                if (temp.isDirectory()) {
                    deleteDir(temp);
                } else if (!temp.delete()) {
                    Log.d("deleteSDCardFolder", "DELETE FAIL");
                }
            }
            to.delete();
        }
    }
}
