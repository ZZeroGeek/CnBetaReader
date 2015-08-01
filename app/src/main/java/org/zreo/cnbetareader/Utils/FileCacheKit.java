package org.zreo.cnbetareader.Utils;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import java.io.File;
import java.lang.ref.WeakReference;

/**
 * Created by Administrator on 2015/7/30.
 */
public class FileCacheKit {

    private File cacheDir;
    private static final int MESSAGE_FINISH = 0x01;
    public void cleanCache() {
        File[] files = cacheDir.listFiles();
        if (files != null) {
            for (File f : files) {
                f.delete();
            }
        }
    }

    public long getCacheSize() {
        try {
            return FileKit.getFolderSize(cacheDir);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
    public File getCacheDir() {
        return cacheDir;
    }
    public interface FileCacheListener {
        public void onFinish(String obj);
    }

    private class FileCacheSaveThread extends Thread {
        private String key;
        private String value;
        private String type;
        private FileCacheHandler handler;

        protected FileCacheSaveThread(String key, String value, String type, FileCacheHandler handler) {
            this.key = key;
            this.type = type;
            this.value = value;
            this.handler = handler;
        }
        public void put(String filename, String value, String type) {
            FileKit.writeFile(cacheDir, filename + "." + type, value);}
        @Override
        public void run() {
            put(key, value, type);
            if (handler != null) {
                Message msg = new Message();
                msg.what = MESSAGE_FINISH;
                msg.obj = "";
                handler.sendMessage(msg);
            }
        }
    }

    private class FileCacheGetThread extends Thread {
        private String key;
        private FileCacheHandler handler;

        protected FileCacheGetThread(String key, FileCacheHandler handler) {
            this.key = key;
            this.handler = handler;
        }

        @Override
        public void run() {
            if (handler != null) {
                Message msg = new Message();
                msg.what = MESSAGE_FINISH;
                handler.sendMessage(msg);
            }
        }
    }

    private static class FileCacheHandler extends Handler {
        private WeakReference<FileCacheListener> listener;

        private FileCacheHandler(FileCacheListener listener) {
            super(Looper.getMainLooper());
            this.listener =  new WeakReference<>(listener);
        }

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MESSAGE_FINISH:
                    if (listener != null) {
                        listener.get().onFinish((String) msg.obj);
                    }
                    break;
            }
            super.handleMessage(msg);
        }
    }

}
