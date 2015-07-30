package org.zreo.cnbetareader.Utils;

import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Created by Administrator on 2015/7/30.
 */
public class FileKit {

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

    public static boolean writeFile(String path, String fileName, String content) {
        File pathf = new File(path);
        if (!pathf.exists()) {
            if (!pathf.mkdirs()) {
                return false;
            }
        }
        return writeFile(pathf, fileName, content);
    }
    public static boolean writeFile(File path, String fileName, String content) {
        if (!path.exists()) {
            path.mkdirs();
        }
        return writeFile(new File(path, fileName), content);
    }
    public static boolean writeFile(File file, String content) {
        return writeFile(file, false, content);
    }

    public static boolean writeFile(File file, boolean append, String content) {
        FileOutputStream fos = null;
        FileChannel fc_out = null;
        try {
            fos = new FileOutputStream(file, append);
            fc_out = fos.getChannel();
            ByteBuffer buf = ByteBuffer.wrap(content.getBytes());
            buf.put(content.getBytes());
            buf.flip();
            fc_out.write(buf);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            if (null != fc_out) {
                try {
                    fc_out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != fos) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return true;
    }
    public static long copyFile(String srcFile, File destDir, String newFileName) {
        long copySizes = 0;
        if (!destDir.exists()) {
            if (!destDir.mkdirs()) {
                System.out.println("无法建立文件夹");
                return -1;
            }
        }
        if (newFileName == null) {
            System.out.println("文件名为null");
            return -1;
        }

        FileInputStream fsin = null;
        FileOutputStream fsout = null;
        try {
            fsin = new FileInputStream(srcFile);
            fsout = new FileOutputStream(new File(destDir, newFileName));
            FileChannel fcin = fsin.getChannel();
            FileChannel fcout = fsout.getChannel();
            long size = fcin.size();
            fcin.transferTo(0, fcin.size(), fcout);
            fcin.close();
            fcout.close();
            copySizes = size;
        } catch (Exception e) {
            e.printStackTrace();
            copySizes = -1;
        } finally {
            if (fsin != null)
                try {
                    fsin.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    copySizes = -1;
                }
            if (fsout != null)
                try {
                    fsout.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    copySizes = -1;
                }
        }
        return copySizes;
    }
    public static String buildFilePath(String path) {
        return Environment.getExternalStorageDirectory().getPath() + File.separator + path;
    }

    public static boolean checkExternalStorage() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
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
    public static void deleteDir(File dir) {
        File to = new File(dir.getAbsolutePath() + System.currentTimeMillis());
        dir.renameTo(to);
        if (to.isDirectory()) {
            String[] children = to.list();
            for (String aChildren : children) {
                File temp = new File(to, aChildren);
                if (temp.isDirectory()) {
                    deleteDir(temp);
                } else if (!temp.delete()) {
                    Log.d("DeleteSDFolder", "DELETE FAIL");
                }
            }
            to.delete();
        }
    }
}