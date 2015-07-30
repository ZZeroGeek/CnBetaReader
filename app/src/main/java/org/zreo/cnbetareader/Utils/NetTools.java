package org.zreo.cnbetareader.Utils;

import com.google.gson.Gson;

/**
 * Created by zqh on 2015/7/30  09:20.
 * Email:zqhkey@163.com
 */
public class NetTools {
    private static Gson gson;

    public static Gson getGson() {
        if (gson == null) {
           gson=new Gson();
        }
        return gson;
    }
}
