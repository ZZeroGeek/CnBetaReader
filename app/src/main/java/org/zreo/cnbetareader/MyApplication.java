package org.zreo.cnbetareader;

import android.app.Application;
import android.content.Context;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

/**
 * Created by Administrator on 2015/7/28.
 */
public class MyApplication extends Application {
    private static MyApplication mApplicationInstance;
   // private static LoadImage instance;
    public static DisplayImageOptions options = new DisplayImageOptions.Builder().cacheOnDisk(true).build();
    public void onCreate() {

        super.onCreate();
        initImageLoader(getApplicationContext());

    }
    public void initImageLoader(Context context) {

        ImageLoaderConfiguration configuration = ImageLoaderConfiguration
                .createDefault(this);

        ImageLoader.getInstance().init(configuration);

    }
    public static DisplayImageOptions getDefaultDisplayOption() {
        return options;
    }

    public static Application getMyApplicationInstance() {
        return mApplicationInstance;
    }
}
